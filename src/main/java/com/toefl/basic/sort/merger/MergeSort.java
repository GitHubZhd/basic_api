package com.toefl.basic.sort.merger;

import java.util.Arrays;

/**
 * @author hai
 * @description  归并排序
 *       速度仅次于快排，内存少的时候使用，可以进行并行计算的时候使用。
 *
 *       选择相邻两个数组成一个有序序列。
 *       选择相邻的两个有序序列组成一个有序序列。
 *       重复第二步，直到全部组成一个有序序列。
 * @date 11:12 2018/6/8
 */
public class MergeSort {

    public static int[] sort(int[] array, int left, int right) {
        if (left == right) {
            return new int[] { array[left] };
        }
        int mid = (right + left) / 2;
        int[] l = sort(array, left, mid);
        int[] r = sort(array, mid + 1, right);
        return merge(l, r);
    }



// 将两个数组合并成一个

    public static int[] merge(int[] l, int[] r) {
        int[] result = new int[l.length + r.length];
        int p = 0;
        int lp = 0;
        int rp = 0;

        while (lp < l.length && rp < r.length) {
            result[p++] = l[lp] < r[rp] ? l[lp++] : r[rp++];
        }
        while (lp < l.length) {
            result[p++] = l[lp++];
        }
        while (rp < r.length) {
            result[p++] = r[rp++];
        }
        return result;
    }

    public static void main(String[] args) {

        int[] a=new int[]{1,57,41,99,2,1,6,8,45,66,21,34,67,78,89};
        sort(a,0,a.length-1);
        System.out.println(Arrays.toString(a));

    }
}
