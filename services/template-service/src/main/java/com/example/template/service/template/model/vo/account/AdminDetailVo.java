package com.example.template.service.template.model.vo.account;

import com.example.template.service.template.model.vo.AdminBaseVo;
import com.example.template.services.common.model.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @title: AdminDetailVo
 * @author: trifolium.wang
 * @date: 2024/1/19
 * @modified:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "管理员详情vo")
public class AdminDetailVo extends AdminBaseVo {

    @Schema(description = "是否是超级管理员")
    private Boolean isSuper;

    @Schema(description = "是否被禁用")
    private Boolean isBanned;

    @Schema(description = "角色列表")
    private List<BaseVo> roles;
}
