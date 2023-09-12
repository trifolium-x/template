package com.example.template.service.template.model.vo.admin;

import com.example.template.services.common.model.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @title: AdminVo
 * @author: trifolium
 * @date: 2023/1/10
 * @modified :
 */
@Data
@Schema(description = "用户信息Vo")
public class AdminVo {

    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "登录账号")
    private String userName;

    @Schema(description = "角色名称")
    private List<BaseVo> roles;

    @Schema(description = "是否禁用")
    private Boolean isBanned;

    @Schema(description = "是否超级管理员")
    private Boolean isSuper;

    @Schema(description = "备注")
    private String remark;

}
