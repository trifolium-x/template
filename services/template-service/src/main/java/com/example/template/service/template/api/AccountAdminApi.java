package com.example.template.service.template.api;

import com.example.template.common.response.Paging;
import com.example.template.common.response.ResponseResult;
import com.example.template.service.template.model.ro.account.SaveRoleRo;
import com.example.template.service.template.model.vo.account.AdminListVo;
import com.example.template.service.template.model.vo.account.AuthBaseVo;
import com.example.template.service.template.model.vo.account.RoleListVo;
import com.example.template.service.template.service.AccountAdminService;
import com.example.template.services.common.annotion.TokenValidator;
import com.example.template.services.common.model.ro.SearchRo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * @title: AccountAdminApi
 * @author: trifolium.wang
 * @date: 2023/11/10
 * @modified :
 */
@Tag(name = "账户管理")
@RestController
@TokenValidator
@RequestMapping("/user_admin")
public class AccountAdminApi {

    @Inject
    private AccountAdminService accountAdminService;

    @Operation(summary = "角色列表")
    @PostMapping("/roles")
    public ResponseResult<Paging<RoleListVo>> roles(@Valid @RequestBody SearchRo ro) {

        return ResponseResult.success(accountAdminService.roles(ro));
    }

    @Operation(summary = "权限树")
    @GetMapping("/auth_tree")
    public ResponseResult<List<AuthBaseVo>> authTree() {

        return ResponseResult.success(accountAdminService.getAuthTree());
    }

    @Operation(summary = "保存角色")
    @PostMapping("/save_role")
    public ResponseResult<Void> roles(@Valid @RequestBody SaveRoleRo ro) {

        accountAdminService.saveRole(ro);
        return ResponseResult.success();
    }

    @Operation(summary = "禁用或者启用角色")
    @GetMapping("/dis_en_able_role")
    public ResponseResult<Void> disEnAbleRole(@RequestParam String code) {

        accountAdminService.disEnAbleRole(code);
        return ResponseResult.success();
    }

    @Operation(summary = "删除角色")
    @GetMapping("/del_role")
    public ResponseResult<Void> allHosCategory(@RequestParam String code) {

        accountAdminService.deleteRole(code);
        return ResponseResult.success();
    }

    @Operation(summary = "用户列表")
    @PostMapping("/user_list")
    public ResponseResult<Paging<AdminListVo>> adminList(@Valid @RequestBody SearchRo ro) {

        return ResponseResult.success(accountAdminService.adminList(ro));
    }

    // TODO 保存用户，删除用户，禁用启用用户
}
