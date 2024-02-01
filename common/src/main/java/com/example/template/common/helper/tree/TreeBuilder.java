package com.example.template.common.helper.tree;

import cn.hutool.core.collection.CollUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @title: 树构造器
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
public class TreeBuilder {
    private TreeBuilder() {

    }

    /**
     * 根据列表构造树，这里的树可以是多个根的。
     *
     * @param treeNodeList 列表
     * @param sort         是否排序
     * @return 构造后的树
     */
    public static <E, T extends TreeNode<T, E>> List<T> build(List<T> treeNodeList, boolean sort) {
        List<T> trees = new ArrayList<>();
        Map<E, T> treeNodeMap = new HashMap<>(treeNodeList.size() * 2);
        for (T treeNode : treeNodeList) {
            treeNodeMap.put(treeNode.getId(), treeNode);
        }
        for (Map.Entry<E, T> entry : treeNodeMap.entrySet()) {
            T treeNode = entry.getValue();
            T parentNode = treeNodeMap.get(treeNode.getParentId());
            if (parentNode == null) {
                trees.add(treeNode);
            } else {
                if (parentNode.getChildren() == null) {
                    parentNode.setChildren(new ArrayList<>());
                }
                parentNode.getChildren().add(treeNode);
            }
        }

        if (sort) {
            treeNodeList.stream().filter(treeNode -> !CollUtil.isEmpty(treeNode.getChildren()))
                    .forEach(treeNode -> Collections.sort(treeNode.getChildren()));
            Collections.sort(trees);
        }
        return trees;
    }

    /**
     * 在树节点列表中查找子树
     *
     * @param treeNodeList 树节点列表
     * @param id           待查找节点id
     * @return 子树
     */
    public static <E, T extends TreeNode<T, E>> T getSubTreeFromList(List<T> treeNodeList, String id) {
        AtomicReference<T> targetNode = new AtomicReference<>();
        Map<E, T> treeNodeMap = new HashMap<>(treeNodeList.size() * 2);
        for (T treeNode : treeNodeList) {
            treeNodeMap.put(treeNode.getId(), treeNode);
        }

        treeNodeMap.values().forEach(treeNode -> {
            if (treeNode.getId().equals(id)) {
                targetNode.set(treeNode);
            }
            T parentNode = treeNodeMap.get(treeNode.getParentId());
            if (parentNode != null) {
                if (parentNode.getChildren() == null) {
                    parentNode.setChildren(new ArrayList<>());
                }
                parentNode.getChildren().add(treeNode);
            }
        });

        return targetNode.get();
    }

    /**
     * 将树形转成列表
     *
     * @param root 树节点
     * @return 树节点列表
     */
    public static <E, T extends TreeNode<T, E>> List<T> treeToList(T root) {
        if (root == null) {
            return null;
        }
        List<T> treeNodeList = new ArrayList<>();
        treeNodeList.add(root);
        List<T> children = root.getChildren();
        if (!CollUtil.isEmpty(children)) {
            for (T child : children) {
                treeNodeList.addAll(treeToList(child));
            }
        }
        return treeNodeList;
    }

    public static <E, T extends TreeNode<T, E>> List<T> treeToList(List<T> trees) {
        if (trees == null) {

            return null;
        }
        List<T> treeNodeList = new ArrayList<>();
        for (T node : trees) {
            treeNodeList.add(node);
            if (node.getChildren() != null) {
                treeNodeList.addAll(treeToList(node.getChildren()));
                node.setChildren(null);
            }
        }

        return treeNodeList;
    }

    /**
     * 获取所有的祖先
     *
     */
    public static <E, T extends TreeNode<T, E>> List<T> getAllAncestors(List<T> treeNodeList, List<E> ids) {
        Map<E, T> treeNodeMap = new HashMap<>(treeNodeList.size() * 2);
        treeNodeList.forEach(treeNode -> treeNodeMap.put(treeNode.getId(), treeNode));
        List<T> ancestors = new ArrayList<>();
        ids.forEach(id -> {
            T node = treeNodeMap.get(id);
            if (node == null) {
                throw new RuntimeException("cannot find this node");
            }
            for (; ; ) {
                node = treeNodeMap.get(node.getParentId());
                if (node == null) {
                    break;
                } else {
                    ancestors.add(node);
                }
            }
        });

        return ancestors.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 获取所有的后代
     *
     */
    public static <E, T extends TreeNode<T, E>> List<T> getAllDescendants(List<T> treeNodeList, List<E> ids) {
        Map<E, List<T>> descendantsMap = new HashMap<>(treeNodeList.size());
        Map<E, T> treeNodeMap = new HashMap<>(treeNodeList.size());
        treeNodeList.forEach(treeNode -> {
            List<T> descendants = descendantsMap.computeIfAbsent(treeNode.getParentId(), k -> new ArrayList<>());
            descendants.add(treeNode);
            treeNodeMap.put(treeNode.getId(), treeNode);
        });

        List<T> allDescendants = new ArrayList<>();
        List<T> currentDescendants = ids.stream().map(treeNodeMap::get).collect(Collectors.toList());
        List<T> toFindDescendants = new ArrayList<>();
        while (!currentDescendants.isEmpty()) {
            for (T currentDescendant : currentDescendants) {
                List<T> oneToFind = descendantsMap.get(currentDescendant.getId());
                if (!CollUtil.isEmpty(oneToFind)) {
                    toFindDescendants.addAll(oneToFind);
                    allDescendants.addAll(oneToFind);
                }
            }
            currentDescendants = toFindDescendants;
        }
        return allDescendants;
    }

}
