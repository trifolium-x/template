package com.example.template.service.template.model.ro.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @title: SaveAdminRo
 * @author: trifolium.wang
 * @date: 2024/1/19
 * @modified:
 */
@Data
@Schema(description = "保存或者更新管理员")
public class SaveAdminRo {

    @Schema(description = "如果存在为修改，不存在为添加")
    private Long id;

    @NotNull(message = "昵称必须填写")
    @NotEmpty(message = "昵称必须填写")
    private String name;

    @NotNull(message = "账号必须填写")
    @NotEmpty(message = "账号必须填写")
    private String userName;

    private String password;

    private List<String> roles;
}
