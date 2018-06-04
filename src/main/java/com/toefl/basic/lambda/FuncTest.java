package com.toefl.basic.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FuncTest {

    public static void main(String[] args) {

        //我们经常需要对集合中元素运用一定的功能，如表中的每个元素乘以或除以一个值等等.

        // Convert String to Uppercase and join them using coma
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany",
                "Italy", "U.K.","Canada");
        String G7Countries = G7.stream().map(x -> x.toUpperCase())
                .collect(Collectors.joining(", "));

        //上面是将字符串转换为大写，然后使用逗号串起来。
        System.out.println(G7Countries);
    }
}
