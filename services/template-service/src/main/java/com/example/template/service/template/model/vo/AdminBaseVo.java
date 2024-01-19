package com.example.template.service.template.model.vo;

import com.example.template.repo.entity.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @title: AdminBaseVo
 * @author: trifolium
 * @date: 2022/2/24
 * @modified :
 */
@Data
@Schema(name = "AdminBaseVo", description = "管理员基础vo-AdminBaseVo")
public class AdminBaseVo {

    public AdminBaseVo(){

    }

    public AdminBaseVo(Admin admin){

        this.id = admin.getId();
        this.name = admin.getName();
        this.userName = admin.getUserName();
    }

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

}
