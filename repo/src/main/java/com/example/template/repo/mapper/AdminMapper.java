package com.example.template.repo.mapper;

import com.example.template.repo.BaseMapper;
import com.example.template.repo.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    List<Admin> findByUserName(@Param("userName") String userName);
}