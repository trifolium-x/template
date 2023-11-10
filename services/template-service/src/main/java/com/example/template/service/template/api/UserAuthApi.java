package com.example.template.service.template.api;

import com.example.template.common.response.ResponseResult;
import com.example.template.service.template.model.ro.AdminLoginRo;
import com.example.template.service.template.model.ro.EditPwdRo;
import com.example.template.service.template.model.vo.admin.AdminLoginVo;
import com.example.template.service.template.service.UserAuthService;
import com.example.template.services.common.annotion.TokenValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @title: UserAuthApi
 * @author: trifolium
 * @date: 2023/1/9
 * @modified :
 */
@Tag(name="授权相关接口")
@RestController
@RequestMapping("/auth")
public class UserAuthApi {

    @Inject
    private UserAuthService authService;

    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public ResponseResult<AdminLoginVo> login(@Valid @RequestBody AdminLoginRo adminLoginRo) {

        return ResponseResult.success(authService.login(adminLoginRo));
    }

    @Operation(summary =  "修改密码")
    @TokenValidator
    @PostMapping("/edit_pwd")
    public ResponseResult<Void> editPwd(@Valid @RequestBody EditPwdRo editPwdRo) {

        authService.editPwd(editPwdRo);
        return ResponseResult.success();
    }

    @Operation(summary = "管理员登出")
    @TokenValidator
    @GetMapping("/logout")
    public ResponseResult<Object> logout() {
        authService.logout();
        return ResponseResult.success();
    }

    @Operation(summary =  "获取验证码")
    @GetMapping("/captcha_image")
    public void getCaptcha(HttpServletResponse response) throws IOException {
        authService.captchaImage(response);
    }

}
