package com.company;

import java.util.Scanner;

/**
 * Created by shijiawei
 */
public class Main {
    /**
     * 输入前序遍历二叉树的值,其中空节点以null代替，
     *              A
     *             / \
     *            B   C
     *            \  / \
     *            E F  G
     *
     * 如上二叉树前序遍历输入为String[] arr = {"a","b",null,"e",null,null,"c","f",null, null,"g", null, null};
     */
    public static void main(String[] args) {
        /**
         * 测试数据1
         * String[][] arr = {{"a","b",null,null,"c","d","f",null,null,null,"e",null,null}};
         *          a
         *         / \
         *        b   c
         *           / \
         *          d   e
         *         /
         *        f
         *
         */


        /**
         * 测试数据2
         * String[][] arr = {{{"a","b",null,null,"c",null,null},
         *                 {"d","c",null,null,"e","f","h",null,null,null,"g",null,null},
         *                 {"j","k",null, null,"l", "n", null,null,null},
         *                 {"m","l","n",null,null,null,null}};
         *
         *                 a    d                   j   m
         *                / \  /  \                / \ /
         *               b   c     e              k   l
         *                       /   \               /
         *                      f     g             n
         *                     /
         *                    h
         *
         */


        /**
         * 测试数据3为题目给出的用例
         *
         */
        String[][] arr = {{"a","b","d",null, null, null,"c","e",null, null,"f",null,null},
                            {"x","y","f",null, null, null,"z",null,null},
                            {"g","w",null, null,"v", null, null}};
        BiTreeFamily<String> biTreeFamily = new BiTreeFamily<>();
        biTreeFamily.createBiTree(arr);



        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("请选择功能：\n1.亲属关系查询\n2.指定亲属关系查询\n3.指定亲属人群查询");
            int func = scanner.nextInt();
            switch (func){
                case 1:
                    doFunc1(scanner, biTreeFamily);
                    break;
                case 2:
                    //我个人理解这个功能是不是和第一个重复了？

                    break;
                case 3:
                    doFunc3(scanner, biTreeFamily);
                    break;
                default:
                    System.out.println("input wrong!!!");
            }

            System.out.println("\n");

        }

    }

    public static void doFunc1(Scanner scanner, BiTreeFamily biTreeFamily){
        System.out.println("请输入两个名称，以,分开");
        String vals = scanner.next();
        String[] names = vals.split(",");
        if(names.length > 1){
            RelationEnum type = biTreeFamily.getRelationType(names[0], names[1]);
            if(type == RelationEnum.ZHIXI){
                System.out.println(vals+" 为直系亲属");
            }else if(type == RelationEnum.PANGXI){
                System.out.println(vals+" 为旁系亲属");
            }else{
                System.out.println(vals+" 没有血缘关系");
            }
        }else{
            System.out.println("输入错误！！！");
        }

    }

    public static void doFunc3(Scanner scanner, BiTreeFamily biTreeFamily){
        System.out.println("请选择功能：\n1.直系亲属查询\n2.旁系亲属查询\n3.所有亲属查询");
        int func = scanner.nextInt();
        System.out.println("请输入要查询的名称");
        String val = scanner.next();

        switch (func){
            case 1:
                System.out.println(val+"的直系亲属有"+biTreeFamily.findRelatedValsInTree(val));
                break;
            case 2:
                System.out.println(val+"的旁系亲属有"+biTreeFamily.findRelatedValsInRelations(val));
                break;
            case 3:
                System.out.println(val+"的所有亲属有"+biTreeFamily.findAllRelatedVals(val));

                break;
        }
    }


}
