package co.tunan.template.repo.mapper;

import co.tunan.template.repo.BaseMapper;
import co.tunan.template.repo.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    List<Admin> findByUserName(@Param("userName") String userName);
}