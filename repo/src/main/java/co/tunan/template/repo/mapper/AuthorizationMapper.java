package co.tunan.template.repo.mapper;

import co.tunan.template.repo.BaseMapper;
import co.tunan.template.repo.entity.Authorization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthorizationMapper extends BaseMapper<Authorization> {

    List<Authorization> findAuthorizationByRoleCode(@Param("roleCode") String roleCode);
}