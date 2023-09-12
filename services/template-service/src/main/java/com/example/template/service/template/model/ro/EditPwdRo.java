package com.example.template.service.template.model.ro;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @title: EditPwdRo
 * @author: trifolium
 * @date: 2023/1/9
 * @modified :
 */
@Data
@Schema(description = "修改密码Ro")
public class EditPwdRo {

    @NotBlank(message = "旧密码必须填写")
    private String oldPwd;

    @NotBlank(message = "新密码必须填写")
    private String newPwd;
}
