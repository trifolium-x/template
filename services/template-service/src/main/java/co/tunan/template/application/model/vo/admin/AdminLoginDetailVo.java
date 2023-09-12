package co.tunan.template.application.model.vo.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @title: AdminLoginDetailVo
 * @author: trifolium
 * @date: 2022/10/9
 * @modified :
 */
@Schema(name = "AdminLoginDetailVo", description = "管理员登录信息详情")
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminLoginDetailVo extends AdminBaseVo {

    @Schema(description = "是否超级管理员")
    private Boolean isSuper;

    @Schema(description = "角色code列表")
    private List<String> roleCodes;

}
