package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

/**
 * Created by shijiawei
 */
public class BiTreeFamily<T>{
    private List<BiTreeNode> trees = new ArrayList<>(); //二叉树集合
    private List<TreeRelation> relations = new ArrayList<>(); //树之间的关联信息
    private static int index = 0;

    public BiTreeNode createBiTree(T[] arr){
        index = 0;
        return createBiTreeInternal(new BiTreeNode(), arr, index);
    }

    public List<BiTreeNode> createBiTree(T[][] arr){
        for(int i = 0; i < arr.length; i++){
            BiTreeNode tree = createBiTree(arr[i]);
            addBiTree(tree);
        }
        return trees;
    }


    public void addBiTree(BiTreeNode tree){
        if(tree != null){
            findTreeRelation(tree);
            trees.add(tree);
        }
    }

    /**
     * 判断两个节点是否有亲属关系
     * @param val1
     * @param val2
     * @return true 代表存在亲属关系
     */
    public RelationEnum getRelationType(T val1, T val2){
        for(BiTreeNode tree : trees){
            if(isNodeInTree(tree, val1) && isNodeInTree(tree, val2)){
                return RelationEnum.ZHIXI;
            }
        }


        if(findNodesInRelations(val1, val2)){
            return RelationEnum.PANGXI;
        }

        return RelationEnum.NONE;
    }

    /**
     * 查找节点的所有亲属节点
     * @param val
     * @return
     */
    public HashSet<T> findAllRelatedVals(T val){
        HashSet<T> set = new HashSet<>();
        BiTreeNode tree = null;
        for(BiTreeNode treeInlist : trees){
            if(isNodeInTree(treeInlist, val)){
                tree = treeInlist;
                collectTreeNode(tree,set);
                break;

            }

        }
        for(TreeRelation relation : relations){
            if(isTreeInRelation(relation, tree)){
                TreeRelation prev = relation.ralation1;
                if(prev != null){
                    while (prev != null){
                        prev = prev.ralation1;
                    }
                }else{
                    prev = relation;
                }

                TreeRelation next = prev;
                while (next != null){
                    collectTreeNode(next.current, set);

                    next = next.ralation2;
                }
            }
        }
        return set;
    }

    /**
     * 旁系亲属查询
     * @param val
     * @return
     */
    public HashSet<T> findRelatedValsInRelations(T val){
        HashSet<T> set = new HashSet<>();
        BiTreeNode tree = null;
        for(BiTreeNode treeInList : trees){
            if(isNodeInTree(treeInList, val)){
                tree = treeInList;
                break;
            }
        }
        for(TreeRelation relation : relations){
            if(isTreeInRelation(relation, tree)){
                TreeRelation prev = relation.ralation1;
                if(prev != null){
                    while (prev != null){
                        prev = prev.ralation1;
                    }
                }else{
                    prev = relation;
                }

                TreeRelation next = prev;

                while (next != null){
                    if(next.current != tree){
                        collectTreeNode(next.current, set);
                    }

                    next = next.ralation2;
                }
            }

        }
        return set;
    }

    /**
     * 直系亲属查询
     * @param val
     * @return
     */
    public HashSet<T> findRelatedValsInTree(T val){
        HashSet<T> set = new HashSet<>();
        for(BiTreeNode tree : trees){
            if(isNodeInTree(tree, val)){
                collectTreeNode(tree, set);
            }
        }
        return set;
    }


    private BiTreeNode createBiTreeInternal(BiTreeNode node, T[] arr, int i) {
        if (null == arr[i]) {
            return null;
        } else {
            node.setVal(arr[i]);
        }

        BiTreeNode leftChild = new BiTreeNode();
        node.left = createBiTreeInternal(leftChild, arr, ++index);
        BiTreeNode rightChild = new BiTreeNode();
        node.right = createBiTreeInternal(rightChild,arr, ++index);
        return node;
    }

    private boolean findNodesInRelations(T val1, T val2){
        boolean findVla1 = false;
        boolean findVla2 = false;
        for(TreeRelation relation : relations){
            TreeRelation prev = relation.ralation1;
            if(prev != null){
                while (prev != null){
                    prev = prev.ralation1;
                }
            }else{
                prev = relation;
            }


            TreeRelation next = prev;
            while (next != null){
                if(!findVla1){
                    findVla1 = isNodeInTree(next.current, val1);
                }
                if(!findVla2){
                    findVla2 = isNodeInTree(next.current, val2);
                }

                next = next.ralation2;
            }

        }

        return findVla1 && findVla2;
    }

    private void findTreeRelation(BiTreeNode tree){
        if(!trees.isEmpty()){
            for(BiTreeNode treeInList : trees){
                if(isRelatedTree(tree, treeInList)){
                    if(relations.isEmpty()){
                        TreeRelation treeRelation = new TreeRelation();
                        treeRelation.current = treeInList;
                        TreeRelation nextRelation = new TreeRelation();
                        nextRelation.current = tree;
                        treeRelation.ralation2 = nextRelation;
                        relations.add(treeRelation);
                    }else{
                        boolean isNewRelation = true;
                        for(TreeRelation relation : relations){
                            if(isTreeInRelation(relation,treeInList) && !isTreeInRelation(relation,tree)){
                                addTreeInRelation(relation, treeInList, tree);
                                isNewRelation = false;
                            }

                        }

                        if(isNewRelation){
                            TreeRelation treeRelation = new TreeRelation();
                            treeRelation.current = treeInList;
                            TreeRelation nextRelation = new TreeRelation();
                            nextRelation.current = tree;
                            treeRelation.ralation2 = nextRelation;
                            relations.add(treeRelation);
                        }
                    }

                }

            }

        }

    }

    private void addTreeInRelation(TreeRelation relation, BiTreeNode treeAlreadyInRealtion, BiTreeNode tree){
        if(relation == null){
            return ;
        }
        if(relation.current == treeAlreadyInRealtion){
            if(relation.ralation2 == null){
                TreeRelation next = new TreeRelation();
                next.current = tree;
                relation.ralation2 = next;
            }
            if(relation.ralation1 == null){
                TreeRelation pre = new TreeRelation();
                pre.current = tree;
                relation.ralation1 = pre;
            }
            return;
        }

        TreeRelation prev = relation.ralation1;
        while (prev != null){
            if(prev.current == treeAlreadyInRealtion){
                if(relation.ralation1 == null){
                    TreeRelation pre = new TreeRelation();
                    pre.current = tree;
                    relation.ralation1 = pre;
                }
                return;
            }else{
                prev = prev.ralation1;
            }

        }

        TreeRelation next = relation.ralation2;
        while (next != null){
            if(next.current == treeAlreadyInRealtion){
                TreeRelation nex = new TreeRelation();
                nex.current = tree;
                relation.ralation2 = nex;
                return;
            }else{
                next = next.ralation2;
            }
        }

    }


    private boolean isTreeInRelation(TreeRelation relation, BiTreeNode tree){
        if(relation == null){
            return false;
        }
        if(relation.current == tree){
            return true;
        }
        TreeRelation prev = relation.ralation1;
        while (prev != null){
            if(prev.current == tree){
                return true;
            }else{
                prev = prev.ralation1;
            }

        }

        TreeRelation next = relation.ralation2;
        while (next != null){
            if(next.current == tree){
                return true;
            }else{
                next = next.ralation2;
            }

        }
        return false;
    }

    private void collectTreeNode(BiTreeNode tree, HashSet<T> set) {
        if (tree == null || set == null) return;
        Stack<BiTreeNode> stack = new Stack<BiTreeNode>();
        stack.push(tree);
        while (!stack.empty()) {
            tree = stack.pop();
            set.add((T) tree.getVal());
            if (tree.right != null) stack.push(tree.right);
            if (tree.left != null) stack.push(tree.left);
        }
    }


    private boolean isRelatedTree(BiTreeNode tree, BiTreeNode treeInList) {
        if (tree == null || treeInList == null) return false;
        Stack<BiTreeNode> stack = new Stack<BiTreeNode>();
        stack.push(tree);
        while (!stack.empty()) {
            tree = stack.pop();
            if(visitNode(tree, treeInList)){
                return true;
            }
            if (tree.right != null) stack.push(tree.right);
            if (tree.left != null) stack.push(tree.left);
        }
        return false;
    }

    private boolean isNodeInTree(BiTreeNode tree, T val) {
        if (tree == null) return false;
        Stack<BiTreeNode> stack = new Stack<BiTreeNode>();
        stack.push(tree);
        while (!stack.empty()) {
            tree = stack.pop();
            if(isSameNode(tree, val)){
                return true;
            }
            if (tree.right != null) stack.push(tree.right);
            if (tree.left != null) stack.push(tree.left);
        }

        return false;
    }


    private boolean visitNode(BiTreeNode node, BiTreeNode treeInList){
        return isNodeInTree(treeInList, (T) node.getVal());
    }

    private boolean isSameNode(BiTreeNode node, T val){
        if(node.getVal().equals(val)){
            return true;
        }
        return false;
    }


}
