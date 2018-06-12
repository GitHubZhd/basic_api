package com.toefl.basic.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author hai
 * 给定一个非负数，表示一个数字数组，在该数的基础上 +1，返回一个新的数组。写一个函数将该数字按照大小进行排列，最大的数在列表的最前面。
 *
 * 格式：
 *
 * 输入行依次输入一个整数数组，最后输出排序后的数组。
 *
 * 样例输入
 *
 * [ 1，2，3 ]
 * [ 9，9，9 ]
 *
 * 样例输出
 *
 * [ 1，2，4 ]
 * [ 1，0，0，0 ]
 */
public class AddOne {

    public static void main(String[] args) {

        System.out.println("请输入参数：  ");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        line = line.trim();
        if(line == null ||"".equals(line)){
            System.out.println("不能输入空字符串，请重新输入");
            return;
        }
        int inputData = 0;//将输入 转换成 int类型
        line = line.replace("[","");
        line = line.replace(",","");
        line = line.replace("]","");
        line = line.replace(" ","");
        inputData = Integer.parseInt(line) + 1;

        List<Integer> list = new ArrayList<>();
        while(inputData > 0 ){
            list.add(inputData % 10);
            inputData = inputData / 10;
        }

        Collections.reverse(list);

        System.out.println(list);
    }
}
