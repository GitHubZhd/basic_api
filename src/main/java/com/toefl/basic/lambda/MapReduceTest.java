package com.toefl.basic.lambda;

import java.util.Arrays;
import java.util.List;

public class MapReduceTest {

    public static void main(String[] args) {
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
//        for (Integer cost : costBeforeTax) {
//            double price = cost + .12*cost;
//            System.out.println(price);
//        }

        //最流行的函数编程概念是map，它允许你改变你的对象
        costBeforeTax.stream().map((m) -> m + m * 0.12).forEach(System.out::println);
        costBeforeTax.stream().map((m) -> m + m * .12).forEach(System.out::println);
    }
}
