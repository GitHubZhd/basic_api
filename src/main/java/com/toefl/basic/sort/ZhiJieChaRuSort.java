package com.toefl.basic.sort;

import java.util.Arrays;

/**
 * @author hai
 * @description 经常碰到这样一类排序问题：把新的数据插入到已经排好的数据列中。
 * @date 10:09 2018/6/6
 */
public class ZhiJieChaRuSort {

    /**
     * @author: hai
     * @description: 直接插入排序，时间复杂度 O(n^2)
     * @date: 10:13 2018/6/6
     *
     * 如何写写成代码：
     *
     * 首先设定插入次数，即循环次数，for(int i=1;i<length;i++)，1个数的那次不用插入。
     * 设定插入数和得到已经排好序列的最后一个数的位数。insertNum和j=i-1。
     * 从最后一个数开始向前循环，如果插入数小于当前数，就将当前数向后移动一位。
     * 将当前数放置到空着的位置，即j+1。
     */
    public static void insertSort(int[] a){
        //插入数
        int inserNum;
        for (int i = 1; i < a.length ; i++) {
            int j = i-1;
            inserNum = a[i];
            for (; j >= 0 ; j--) {
                if(a[j]>inserNum){
                    a[j+1] = a[j];
                }else {
                    break;
                }
            }
            a[j+1] = inserNum;
        }
    }

    public static void insertSort1(int[] a){
        int length=a.length;//数组长度，将这个提取出来是为了提高速度。
        int insertNum;//要插入的数
        for(int i=1;i<length;i++){//插入的次数
            insertNum=a[i];//要插入的数
            int j=i-1;//已经排序好的序列元素个数
            while(j>=0&&a[j]>insertNum){//序列从后到前循环，将大于insertNum的数向后移动一格
                a[j+1]=a[j];//元素移动一格
                j--;
            }
            a[j+1]=insertNum;//将需要插入的数放在要插入的位置。
        }
    }

    public static void main(String[] args) {
        int[] a=new int[]{1,57,41,99,2,1,6,8,45,66,21,34,67,78,89};
        int[] b=new int[]{1,57,41,99,2,1,6,8,45,66,21,34,67,78,89};

        insertSort(a);
        System.out.println(Arrays.toString(a));

        insertSort1(b);
        System.out.println(Arrays.toString(b));
    }
}
