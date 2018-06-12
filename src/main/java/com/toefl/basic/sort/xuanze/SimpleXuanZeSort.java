package com.toefl.basic.sort.xuanze;

import java.util.Arrays;

/**
 * (如果每次比较都交换，那么就是交换排序；如果每次比较完一个循环再交换，就是简单选择排序。)
 * @author hai
 * @description 简单选择排序 常用于取序列中最大最小的几个数时。 T(n)=O(n2)
 * @date 11:06 2018/6/7
 */
public class SimpleXuanZeSort {

    public static void sort(int[] a){

        for (int i = 0; i < a.length ; i++) {

            int mix = a[i];
            int index = i;
            for (int j = i+1; j < a.length; j++) {
                //找出最小的数
                if(a[j] < mix){
                    mix = a[j];
                    index = j;
                }
            }
            //交换位置
            a[index] = a[i];
            a[i] = mix;
        }
    }

    public static void main(String[] args) {
        int[] a=new int[]{1,57,41,99,2,1,6,8,45,66,21,34,67,78,89};

        sort(a);
        System.out.println(Arrays.toString(a));

//        sort(b);
//        System.out.println(Arrays.toString(b));
    }
}
