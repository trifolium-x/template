package com.example.template.service.template.controller;

import com.example.template.common.response.Paging;
import com.example.template.common.response.ResponseResult;
import com.example.template.service.template.model.vo.admin.AdminBaseVo;
import com.example.template.service.template.service.AdminService;
import com.example.template.services.common.annotion.TokenValidator;
import com.example.template.services.common.model.ro.SearchRo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * @title: AdminController
 * @author: trifolium
 * @date: 2023/9/8
 * @modified :
 */
@Tag(name = "用户管理相关接口")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Inject
    private AdminService adminService;

    @Operation(summary = "用户列表", description = "查询管理员的列表")
    @TokenValidator(authorities = "admin_list")
    @PostMapping("/list")
    public ResponseResult<Paging<AdminBaseVo>> list(@Valid @RequestBody SearchRo searchRo) {

        return ResponseResult.success(adminService.adminList(searchRo));
    }

    @Operation(summary = "用户列表有缓存", description = "查询管理员的列表但又缓存")
    @TokenValidator(authorities = "admin_list_2")
    @PostMapping("/list2")
    public ResponseResult<Paging<AdminBaseVo>> list2(@Valid @RequestBody SearchRo searchRo) {

        return ResponseResult.success(adminService.adminListHasCache(searchRo));
    }

    @Operation(summary = "一个用户")
    @TokenValidator
    @GetMapping("/one")
    public ResponseResult<AdminBaseVo> one(@RequestParam(value = "id") Long id) {

        return ResponseResult.success(adminService.adminById(id));
    }

    @Operation(summary = "清理所有缓存")
    @TokenValidator
    @GetMapping("/clear_all_cache")
    public ResponseResult<Void> clearAllCache() {

        adminService.clearAllCache();
        return ResponseResult.success();
    }
}
