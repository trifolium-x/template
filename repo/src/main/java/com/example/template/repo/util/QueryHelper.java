package com.example.template.repo.util;

import com.example.template.common.enumeration.OrderType;
import com.example.template.common.helper.BeanFiller;
import com.example.template.common.helper.exception.InvokeException;
import com.example.template.common.request.PageCondition;
import com.example.template.common.response.Paging;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by trifolium on 2021/8/23.
 */
public class QueryHelper {

    public static String createFullLikeFilterText(String filterText) {
        String validFilter = null;

        if (filterText != null) {
            validFilter = "%" + StringUtils.trim(filterText) + "%";
        }

        return validFilter;
    }

    public static void setupPageCondition(Map<String, String> mapping, PageCondition pageCondition) {
        PageHelper.startPage(pageCondition.getPageIndex(), pageCondition.getPageSize());

        String sortString = getSortString(mapping, pageCondition);
        if (StringUtils.isNotBlank(sortString)) {
            PageHelper.orderBy(sortString);
        }
    }

    public static void setupPageCondition(PageCondition pageCondition, String sort, OrderType orderType) {
        if (StringUtils.isBlank(sort) || orderType == null) {

            throw new InvokeException("order error");
        }
        PageHelper.startPage(pageCondition.getPageIndex(), pageCondition.getPageSize());
        String sortString = String.format("%s %s", sort, orderType.getValue());
        PageHelper.orderBy(sortString);
    }

    public static void setupPageCondition(PageCondition pageCondition) {
        PageHelper.startPage(pageCondition.getPageIndex(), pageCondition.getPageSize());
    }

    public static void setupOrder(String sort, OrderType orderType) {
        String sortString = String.format("%s %s", sort, orderType.getValue());
        PageHelper.orderBy(sortString);
    }

    /**
     * 获取分页对象，完全自动映射
     */
    public static <I, R> Paging<R> getPaging(List<I> data, Class<R> clazz) {
        com.github.pagehelper.PageInfo<I> pageInfo
                = new com.github.pagehelper.PageInfo<>(data);
        Paging<R> paging = new Paging<>();
        paging.setData(BeanFiller.target(clazz).acceptList(data));
        paging.setTotalCount(pageInfo.getTotal());
        paging.setPageSize(pageInfo.getPageSize());
        paging.setPageIndex(pageInfo.getPageNum());
        paging.setPageCount(pageInfo.getPages());

        return paging;
    }

    /**
     * 获取分页对象，会先自动映射，consumer 是对自动映射后的属性继续补充
     */
    public static <I, R> Paging<R> getPaging(List<I> data, Class<R> clazz, BiConsumer<R, I> consumer) {
        com.github.pagehelper.PageInfo<I> pageInfo
                = new com.github.pagehelper.PageInfo<>(data);
        Paging<R> paging = new Paging<>();
        paging.setData(BeanFiller.target(clazz).acceptListDefault(data, consumer));
        paging.setTotalCount(pageInfo.getTotal());
        paging.setPageSize(pageInfo.getPageSize());
        paging.setPageIndex(pageInfo.getPageNum());
        paging.setPageCount(pageInfo.getPages());

        return paging;
    }

    /**
     * 不自动映射，完全在consumer手动映射
     */
    public static <I, R> Paging<R> getCustomMappingPaging(List<I> data, Class<R> clazz, BiConsumer<R, I> consumer) {
        com.github.pagehelper.PageInfo<I> pageInfo
                = new com.github.pagehelper.PageInfo<>(data);
        Paging<R> paging = new Paging<>();
        paging.setData(BeanFiller.target(clazz).acceptList(data, consumer));
        paging.setTotalCount(pageInfo.getTotal());
        paging.setPageSize(pageInfo.getPageSize());
        paging.setPageIndex(pageInfo.getPageNum());
        paging.setPageCount(pageInfo.getPages());

        return paging;
    }

    /**
     * 获取分页对象
     */
    public static <I> Paging<I> getPaging(List<I> data) {
        com.github.pagehelper.PageInfo<I> pageInfo = new com.github.pagehelper.PageInfo<>(data);

        return new Paging<>(pageInfo);
    }

    private static String getSortString(Map<String, String> mapping, PageCondition pageCondition) {
        if (pageCondition.getSort() != null) {
            String orderBy = mapping.get(pageCondition.getSort());
            if (orderBy == null) {

            } else {
                return String.format("%s %s", orderBy, pageCondition.getOrder().getValue());
            }
        }

        return "";
    }

}
