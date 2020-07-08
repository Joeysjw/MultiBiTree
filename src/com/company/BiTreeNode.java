package com.company;

/**
 * Created by shijiawei
 */
public class BiTreeNode<T> {
    private T val;
    BiTreeNode left;
    BiTreeNode right;

    BiTreeNode() {

    }
    BiTreeNode(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    public BiTreeNode getLeft() {
        return left;
    }

    public void setLeft(BiTreeNode left) {
        this.left = left;
    }

    public BiTreeNode getRight() {
        return right;
    }

    public void setRight(BiTreeNode right) {
        this.right = right;
    }


}
