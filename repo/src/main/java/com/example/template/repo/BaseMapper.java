package com.example.template.repo;


import tk.mybatis.mapper.additional.select.SelectByPropertyMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by trifolium on 2021/8/23.
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T>, SelectByPropertyMapper<T> {
}
