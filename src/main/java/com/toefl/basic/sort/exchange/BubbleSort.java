package com.toefl.basic.sort.exchange;

import java.util.Arrays;

/**
 * @author hai
 * @description 冒泡排序
 * @date 14:33 2018/6/7
 */
public class BubbleSort {

    public static void bubbleSort(int[] a){

        for (int i = 0; i < a.length ; i++) {

            for (int j = 0; j < a.length-1-i ; j++) {

                if(a[j]>a[j+1]){
                    int temp=a[j+1];
                    a[j+1]= a[j];
                    a[j]=temp;
                }
            }
        }
    }

    public static void main(String[] args) {

        int[] a=new int[]{1,5,8,42,15,19,3,87};

        bubbleSort(a);
        System.out.println(Arrays.toString(a));
    }
}
