package com.example.template.repo.mapper;

import com.example.template.repo.BaseMapper;
import com.example.template.repo.entity.AuthRoleIdx;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface AuthRoleIdxMapper extends BaseMapper<AuthRoleIdx> {

    @Delete("DELETE FROM  t_auth_role_idx WHERE role_code = #{roleCode} ")
    int delByRoleCode(@Param("roleCode") String roleCode);
}