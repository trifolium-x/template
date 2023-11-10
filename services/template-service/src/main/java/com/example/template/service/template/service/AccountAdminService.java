package com.example.template.service.template.service;

import cn.hutool.core.collection.CollUtil;
import com.example.template.common.helper.BeanFiller;
import com.example.template.common.helper.exception.InvokeException;
import com.example.template.common.helper.exception.NotFondException;
import com.example.template.common.helper.exception.ValidateException;
import com.example.template.common.helper.tree.TreeBuilder;
import com.example.template.common.response.Paging;
import com.example.template.common.util.JsonUtil;
import com.example.template.repo.entity.AdminRole;
import com.example.template.repo.entity.AuthRoleIdx;
import com.example.template.repo.entity.Authorization;
import com.example.template.repo.mapper.AdminMapper;
import com.example.template.repo.mapper.AdminRoleMapper;
import com.example.template.repo.mapper.AuthRoleIdxMapper;
import com.example.template.repo.mapper.AuthorizationMapper;
import com.example.template.repo.util.QueryHelper;
import com.example.template.service.template.model.ro.account.SaveRoleRo;
import com.example.template.service.template.model.vo.account.AdminListVo;
import com.example.template.service.template.model.vo.account.AuthBaseVo;
import com.example.template.service.template.model.vo.account.RoleListVo;
import com.example.template.service.template.model.vo.admin.AdminBaseVo;
import com.example.template.services.common.context.AdminContext;
import com.example.template.services.common.model.ro.SearchRo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @title: AccountAdminService
 * @author: trifolium.wang
 * @date: 2023/11/10
 * @modified :
 */
@Slf4j
@Service
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
        return QueryHelper.getPaging(adminRoleMapper.search(ro.getKeyword()), RoleListVo.class, (vo, db) -> {
            vo.setCreateUser(new AdminBaseVo(adminContext.getAdmin(db.getCreateBy())));
        });
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

    public List<AuthBaseVo> getAuthTree() {
        List<Authorization> auths = authorizationMapper.selectAll();
        return TreeBuilder.build(auths.stream().map(a -> {
            AuthBaseVo vo = new AuthBaseVo();
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
                AdminListVo.class, (vo, admin) -> vo.setRole(roles.stream().filter(
                        r -> CollUtil.contains(JsonUtil.jsonToList(admin.getRoleCodes(), String.class),
                                r.getCode())).map(AdminRole::getName).collect(Collectors.toList())

                ));
    }
}
