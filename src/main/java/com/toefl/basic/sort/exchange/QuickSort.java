package com.toefl.basic.sort.exchange;

import java.util.Arrays;

/**
 * @author hai
 * @description 快速排序 要求时间最快时。
 * @date 15:05 2018/6/7
 */
public class QuickSort {

    /**
     * @author: hai
     * @description:
     * @date: 15:06 2018/6/7
     *
     * 选择第一个数为p，小于p的数放在左边，大于p的数放在右边。
     * 递归的将p左边和右边的数都按照第一步进行，直到不能递归。
     *
     * 4、分析
     *
     * 　　快速排序是不稳定的排序。
     *
     * 　　快速排序的时间复杂度为O(nlogn)。
     *
     * 　　当n较大时使用快排比较好，当序列基本有序时用快排反而不好。
     */
    public static void quickSort(int[] a){


    }

    public static void quickSort(int[] numbers, int start, int end) {
        if (start < end) {
            int base = numbers[start]; // 选定的基准值（第一个数值作为基准值）
            int temp; // 记录临时中间值
            int i = start, j = end;
            do {
                while ((numbers[i] < base) && (i < end))
                {i++;}
                while ((numbers[j] > base) && (j > start))
                {j--;}
                if (i <= j) {
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                    i++;
                    j--;
                }
            } while (i <= j);
            if (start < j){
                quickSort(numbers, start, j);
            }
            if (end > i){
                quickSort(numbers, i, end);
            }
        }
    }

    public static void main(String[] args) {

        int[] a=new int[]{1,5,8,42,15,19,3,87};

        quickSort(a,0,a.length-1);
        System.out.println(Arrays.toString(a));
    }
}
