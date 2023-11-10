package com.example.template.service.template.model.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @title: AdminListVo
 * @author: trifolium.wang
 * @date: 2023/11/6
 * @modified :
 */
@Data
@Schema(description = "管理员列表vo")
public class AdminListVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String userName;

    private List<String> role;

    private String remark;
}
