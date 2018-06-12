package com.toefl.basic.sort;

import java.util.Arrays;

/**
 * @author hai
 * @description 希尔排序  O(n^2)     对于直接插入排序问题，数据量巨大时。
 * @date 11:09 2018/6/6
 */
public class XiErSort {

    /**
     * @author: hai
     * @description:
     * @date: 11:25 2018/6/6
     * 如何写成代码：
     *
     * 首先确定分的组数。
     * 然后对组中元素进行插入排序。
     * 然后将length/2，重复1,2步，直到length=0为止。
     *
     */
    public static void sort(int[] a){
        int k = a.length;
        while (k!=0){
            k = k/2;
            // k 组
            for (int x = 0; x < k ; x++) {
                //组内元素
                for (int i = x+k; i < a.length ; i+=k) {
                    //内部是直接插入排序
                    int j = i-k;
                    int inserNum = a[i];
                    for (; j >= 0 && a[j]>inserNum; j-=k) {
                        a[j+k] = a[j];
                    }
                    a[j+k] = inserNum;
                }
            }
        }

    }

    public static void sheelSort(int[] a){
        int d  = a.length;
        while (d!=0) {
            d=d/2;
            for (int x = 0; x < d; x++) {//分的组数
                for (int i = x + d; i < a.length; i += d) {//组中的元素，从第二个数开始
                    int j = i - d;//j为有序序列最后一位的位数
                    int temp = a[i];//要插入的元素
                    for (; j >= 0 && temp < a[j]; j -= d) {//从后往前遍历。
                        a[j + d] = a[j];//向后移动d位
                    }
                    a[j + d] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {

        int[] a=new int[]{1,57,41,99,2,1,6,8,45,66,21,34,67,78,89};
        int[] b=new int[]{1,57,41,99,2,1,6,8,45,66,21,34,67,78,89};

        sheelSort(a);
        System.out.println(Arrays.toString(a));

        sort(b);
        System.out.println(Arrays.toString(b));
        System.out.println(5/2);
    }
}
