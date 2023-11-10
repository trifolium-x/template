package com.example.template.repo.mapper;


import com.example.template.repo.BaseMapper;
import com.example.template.repo.entity.AdminRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    List<AdminRole> search(@Param("keyword") String keyword);

    @Select("SELECT * FROM t_admin_role WHERE (name=#{name} OR code = #{code}) AND is_del = 0")
    List<AdminRole> findByNameOrCode(@Param("name") String name,@Param("code") String code);

    @Select("SELECT * FROM t_admin_role WHERE code=#{code} AND is_del = 0")
    AdminRole findByCode(@Param("code") String code);
}