package com.example.template.service.template.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.example.template.common.helper.BeanFiller;
import com.example.template.common.helper.exception.InvokeException;
import com.example.template.common.helper.exception.NotFondException;
import com.example.template.common.helper.exception.ValidateException;
import com.example.template.common.helper.tree.TreeBuilder;
import com.example.template.common.response.Paging;
import com.example.template.common.util.JsonUtil;
import com.example.template.common.util.MD5Util;
import com.example.template.repo.entity.Admin;
import com.example.template.repo.entity.AdminRole;
import com.example.template.repo.entity.AuthRoleIdx;
import com.example.template.repo.entity.Authorization;
import com.example.template.repo.mapper.AdminMapper;
import com.example.template.repo.mapper.AdminRoleMapper;
import com.example.template.repo.mapper.AuthRoleIdxMapper;
import com.example.template.repo.mapper.AuthorizationMapper;
import com.example.template.repo.util.QueryHelper;
import com.example.template.service.template.model.ro.account.SaveAdminRo;
import com.example.template.service.template.model.ro.account.SaveRoleRo;
import com.example.template.service.template.model.vo.AdminBaseVo;
import com.example.template.service.template.model.vo.account.AdminDetailVo;
import com.example.template.service.template.model.vo.account.AdminListVo;
import com.example.template.service.template.model.vo.account.AuthTreeVo;
import com.example.template.service.template.model.vo.account.RoleListVo;
import com.example.template.services.common.context.AdminContext;
import com.example.template.services.common.model.ro.SearchRo;
import com.example.template.services.common.model.vo.BaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @title: AccountAdminService
 * @author: trifolium.wang
 * @date: 2023/11/10
 * @modified :
 */
@Slf4j
@Service
@Transactional
public class AccountAdminService {

    @Inject
    private AdminRoleMapper adminRoleMapper;

    @Inject
    private AdminContext adminContext;

    @Inject
    private AuthRoleIdxMapper authRoleIdxMapper;

    @Inject
    private AdminMapper adminMapper;

    @Inject
    private AuthorizationMapper authorizationMapper;

    public Paging<RoleListVo> roles(SearchRo ro) {

        QueryHelper.setupPageCondition(ro);
        return QueryHelper.getPaging(adminRoleMapper.search(ro.getKeyword()),
                RoleListVo.class, (vo, db) -> vo.setCreateUser(new AdminBaseVo(adminContext.getAdmin(db.getCreateBy()))));
    }

    public void saveRole(SaveRoleRo ro) {
        Long adminId = adminContext.getAdmin().getId();
        String roleCode;
        AdminRole role;
        if (ro.getId() == null) {
            roleCode = ro.getCode();
            role = BeanFiller.target(AdminRole.class).accept(ro);
            role.setCreateBy(adminId);
            role.setCreateTime(new Date());
            role.setIsBanned(Boolean.FALSE);
            role.setIsDel(Boolean.FALSE);
            role.setIsSysRole(Boolean.FALSE);

            if (CollUtil.isNotEmpty(adminRoleMapper.findByNameOrCode(ro.getName(), ro.getCode()))) {
                throw new ValidateException("名称或code已经存在!");
            }

            adminRoleMapper.insert(role);
        } else {
            role = adminRoleMapper.selectByPrimaryKey(ro.getId());
            if (role == null) {
                throw new NotFondException("角色不存在");
            }
            role.setName(ro.getName());
            role.setDescription(ro.getDescription());
            List<AdminRole> byName = adminRoleMapper.findByNameOrCode(ro.getName(), role.getCode());
            if (CollUtil.isNotEmpty(byName) && byName.stream().anyMatch(n -> !n.getId().equals(ro.getId()))) {
                throw new ValidateException("名称已经存在!");
            }

            adminRoleMapper.updateByPrimaryKey(role);
            roleCode = role.getCode();
        }
        authRoleIdxMapper.delByRoleCode(role.getCode());
        if (CollUtil.isNotEmpty(ro.getAuths())) {
            authRoleIdxMapper.insertList(ro.getAuths().stream().map(auth -> {
                AuthRoleIdx idx = new AuthRoleIdx();
                idx.setCreateBy(adminId);
                idx.setCreateTime(new Date());
                idx.setRoleCode(roleCode);
                idx.setAuthCode(auth);
                return idx;
            }).collect(Collectors.toList()));
        }
    }

    public void disEnAbleRole(String code) {

        AdminRole role = adminRoleMapper.findByCode(code);
        if (role == null) {
            throw new NotFondException("角色不存在!");
        }
        if (role.getIsSysRole()) {
            throw new InvokeException("系统角色无法禁用!");
        }

        role.setIsBanned(!Boolean.TRUE.equals(role.getIsBanned()));
        adminRoleMapper.updateByPrimaryKey(role);
    }

    public void deleteRole(String code) {

        AdminRole role = adminRoleMapper.findByCode(code);
        if (role == null) {
            throw new NotFondException("角色不存在!");
        }
        if (role.getIsSysRole()) {
            throw new InvokeException("系统角色无法删除!");
        }

        adminMapper.delAdminOneRole(code);
        adminRoleMapper.deleteByPrimaryKey(role.getId());
        authRoleIdxMapper.delByRoleCode(code);
    }

    public List<AuthTreeVo> getAuthTree() {
        List<Authorization> auths = authorizationMapper.selectAll();
        return TreeBuilder.build(auths.stream().map(a -> {
            AuthTreeVo vo = new AuthTreeVo();
            vo.setId(a.getCode());
            vo.setName(a.getName());
            vo.setType(a.getType());
            vo.setParentId(a.getParentCode());
            vo.setAuthority(a.getAuthority());
            return vo;
        }).collect(Collectors.toList()), true);
    }

    public Paging<AdminListVo> adminList(SearchRo ro) {

        List<AdminRole> roles = adminRoleMapper.selectAll();
        QueryHelper.setupPageCondition(ro);
        return QueryHelper.getPaging(adminMapper.searchUser(ro.getKeyword()),
                AdminListVo.class, (vo, admin) -> vo.setRoleNames(roles.stream().filter(
                        r -> CollUtil.contains(JsonUtil.jsonToList(admin.getRoleCodes(), String.class),
                                r.getCode())).map(AdminRole::getName).collect(Collectors.toList())

                ));
    }

    public AdminDetailVo adminDetail(Long id) {

        var admin = adminMapper.selectByPrimaryKey(id);
        if (admin == null) {
            throw new NotFondException("用户不存在!");
        }

        var vo = BeanFiller.target(AdminDetailVo.class).accept(admin);
        List<String> roleCodes = JsonUtil.jsonToList(admin.getRoleCodes(), String.class);
        if (roleCodes != null) {
            List<AdminRole> allRoles = adminRoleMapper.selectAll();
            vo.setRoles(allRoles.stream().filter(
                    r -> CollUtil.contains(JsonUtil.jsonToList(admin.getRoleCodes(), String.class),
                            r.getCode())).map(r -> new BaseVo(r.getCode(), r.getName())).collect(Collectors.toList())

            );
        }

        return vo;
    }

    public void saveAdmin(SaveAdminRo ro) {

        List<Admin> byUserName = adminMapper.findByUserName(ro.getUserName());

        if (ro.getId() == null) {
            if (!byUserName.isEmpty()) {
                throw new InvokeException("账号已存在!");
            }
            Admin newAdmin = new Admin();
            newAdmin.setName(ro.getName());
            newAdmin.setUserName(ro.getUserName());
            newAdmin.setRoleCodes(JsonUtil.toJson(ro.getRoles()));
            newAdmin.setCreateBy(adminContext.getAdminIdIfDefault(null));
            newAdmin.setCreateTime(new Date());
            newAdmin.setIsBanned(Boolean.FALSE);
            newAdmin.setIsDel(Boolean.FALSE);
            newAdmin.setIsSuper(Boolean.FALSE);
            if (StrUtil.isNotBlank(ro.getPassword())) {
                newAdmin.setPassword(MD5Util.getMD5String(ro.getPassword()));
            } else {
                newAdmin.setPassword(RandomUtil.randomString(32));
            }
            adminMapper.insert(newAdmin);

        } else {
            var oldAdmin = adminMapper.selectByPrimaryKey(ro.getId());
            if (oldAdmin == null) {
                throw new NotFondException("用户不存在!");
            }
            if (oldAdmin.getIsSuper()) {
                throw new InvokeException("超级管理员无法修改!");
            }
            if (byUserName.stream().anyMatch(a -> !Objects.equals(a.getId(), ro.getId()))) {
                throw new InvokeException("账号已存在!");
            }
            Admin newAdmin = new Admin();
            newAdmin.setId(ro.getId());
            newAdmin.setName(ro.getName());
            newAdmin.setUserName(ro.getUserName());
            newAdmin.setRoleCodes(JsonUtil.toJson(ro.getRoles()));
            newAdmin.setUpdateBy(adminContext.getAdminIdIfDefault(null));
            newAdmin.setUpdateTime(new Date());
            if (StrUtil.isNotBlank(ro.getPassword())) {
                newAdmin.setPassword(MD5Util.getMD5String(ro.getPassword()));
            }
            adminMapper.updateByPrimaryKeySelective(newAdmin);
        }
    }

    public void deleteAdmin(Long id){
        var admin = adminMapper.selectByPrimaryKey(id);
        if (admin == null) {
            throw new NotFondException("用户不存在!");
        }
        if (admin.getIsSuper()) {
            throw new InvokeException("超级管理员无法删除!");
        }

        adminMapper.deleteByPrimaryKey(id);
    }

    public void disEnAbleAdmin(Long id) {

        var admin = adminMapper.selectByPrimaryKey(id);
        if (admin == null) {
            throw new NotFondException("用户不存在!");
        }
        if (admin.getIsSuper()) {
            throw new InvokeException("超级管理员无法禁用!");
        }
        admin.setIsBanned(!Boolean.TRUE.equals(admin.getIsBanned()));
        adminMapper.updateByPrimaryKey(admin);
    }

}
