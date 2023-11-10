package com.example.template.service.template.model.vo.account;


import com.example.template.common.helper.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @title: AuthBaseVo
 * @author: trifolium.wang
 * @date: 2023/11/6
 * @modified :
 */
@Data
@Schema(description = "权限树")
public class AuthBaseVo implements TreeNode<AuthBaseVo, String> {

    @Schema(description = "权限code")
    private String id;

    private String name;

    @Schema(description = "授权字符串")
    private String authority;

    @Schema(description = "权限类型,0->接口权限,1->页面权限,3->统一权限")
    private Integer type;

    @Schema(description = "父权限id")
    private String parentId;

    @Schema(description = "子权限们")
    private List<AuthBaseVo> children;

    @Override
    public int compareTo(AuthBaseVo o) {
        return this.id.compareTo(o.getId());
    }
}
