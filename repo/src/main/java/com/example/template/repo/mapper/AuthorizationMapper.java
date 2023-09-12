package com.example.template.repo.mapper;

import com.example.template.repo.BaseMapper;
import com.example.template.repo.entity.Authorization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthorizationMapper extends BaseMapper<Authorization> {

    List<Authorization> findAuthorizationByRoleCode(@Param("roleCode") String roleCode);
}