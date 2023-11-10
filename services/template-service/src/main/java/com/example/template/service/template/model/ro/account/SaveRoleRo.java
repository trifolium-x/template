package com.example.template.service.template.model.ro.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @title: SaveRoleRo
 * @author: trifolium.wang
 * @date: 2023/11/6
 * @modified :
 */
@Data
@Schema(description = "更新或者保存角色")
public class SaveRoleRo {

    @Schema(description = "如果存在为修改，不存在为添加")
    private Long id;

    @NotEmpty(message = "code不能为空")
    @Schema(description = "角色code, code不能修改")
    private String code;

    @NotEmpty(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @Schema(description = "权限codes")
    private List<String> auths;

    @Schema(description = "描述")
    private String description;
}
