package co.tunan.template.application.model.vo.admin;

import co.tunan.template.services.ms.common.model.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @title: Created by trifolium.
 * @author: trifolium
 * @date: 2021/9/1
 * @modified :
 */
@Getter
@Setter
@Schema(description = "管理员权限Vo")
public class AdminAuthorityVo extends BaseVo {

    @Schema(description = "权限标记")
    private String authority;

    @Schema(description = "类型")
    private String type;
}
