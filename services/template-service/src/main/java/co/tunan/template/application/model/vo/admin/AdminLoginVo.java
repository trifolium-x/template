package co.tunan.template.application.model.vo.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @title: AdminLoginVo
 * @author: trifolium
 * @date: 2023/1/9
 * @modified :
 */
@Data
@Schema(name = "AdminLoginVo", description = "登录信息")
public class AdminLoginVo {

    private String token;

    private String tokenType;

    @Schema(description = "用户信息详情")
    private AdminLoginDetailVo adminVo;

    @Schema(description = "权限列表")
    private List<AdminAuthorityVo> authorities;

    @Schema(description = "权限标记树(路由)")
    private List<Map<String, ?>> pageAuthorities;

    @Schema(description = "过期时间")
    private Long expires;

}
