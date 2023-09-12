package co.tunan.template.application.model.ro;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @title: AdminLoginRo
 * @author: trifolium
 * @date: 2023/1/9
 * @modified :
 */
@Data
@Schema(description = "用户登录")
public class AdminLoginRo {

    @Schema(description = "用户名", example = "admin")
    @NotBlank(message = "账号必须填写")
    @Size(max = 100, message = "账号长度最大100")
    private String userName;

    @Schema(description = "密码", example = "111111")
    @NotBlank(message = "密码必须填写")
    @Size(max = 18, min = 6, message = "密码长度必须为6-18位")
    private String password;

    @Schema(description = "验证码", example = "1234")
    @NotBlank(message = "验证码错误")
    @Size(min = 4, max = 4, message = "验证码错误")
    private String captcha;
}
