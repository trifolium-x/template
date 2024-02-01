package com.example.template.common.helper.tree;

import java.util.List;

/**
 * @title: 树节点
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
public interface TreeNode<T extends TreeNode<?, ?>, E> extends Comparable<T> {

    List<T> getChildren();

    void setChildren(List<T> children);

    E getId();

    E getParentId();

}
