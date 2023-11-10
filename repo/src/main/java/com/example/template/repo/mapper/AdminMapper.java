package com.example.template.repo.mapper;

import com.example.template.repo.BaseMapper;
import com.example.template.repo.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> findByUserName(@Param("userName") String userName);

    @Select("SELECT * FROM t_admin WHERE is_del = 0 AND JSON_CONTAINS(role_codes,'\"${roleCode}\"')")
    List<Admin> findByRoleCode(@Param("roleCode") String roleCode);

    int delAdminOneRole(@Param("roleCode") String roleCode);

    List<Admin> searchUser(@Param("keyword") String keyword);
}