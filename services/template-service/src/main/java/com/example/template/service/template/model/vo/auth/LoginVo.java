package com.example.template.service.template.model.vo.auth;

import com.example.template.service.template.model.vo.AdminBaseVo;
import com.example.template.services.common.model.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @title: LoginVo
 * @author: trifolium
 * @date: 2023/1/9
 * @modified :
 */
@Data
@Schema(name = "LoginVo", description = "登录信息")
public class LoginVo {

    private String token;

    private String tokenType;

    @Schema(description = "用户信息详情")
    private LoginAdminVo adminVo;

    @Schema(description = "权限列表")
    private List<LoginAuthorityVo> authorities;

    @Schema(description = "权限标记树(路由)")
    private List<Map<String, ?>> pageAuthorities;

    @Schema(description = "过期时间")
    private Long expires;

    @Getter
    @Setter
    @Schema(description = "管理员权限Vo")
    public static class LoginAuthorityVo extends BaseVo {

        @Schema(description = "权限标记")
        private String authority;

        @Schema(description = "类型")
        private String type;
    }

    @Getter
    @Setter
    @Schema(name = "管理员信息详情Vo")
    public static class LoginAdminVo extends AdminBaseVo {

        @Schema(description = "是否超级管理员")
        private Boolean isSuper;

        @Schema(description = "角色code列表")
        private List<String> roleCodes;

    }

}
