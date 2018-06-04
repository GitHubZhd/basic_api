package com.toefl.basic.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filtering是对大型Collection操作的一个通用操作，Stream提供filter()方法，接受一个Predicate对象，意味着你能传送lambda表达式作为一个过滤逻辑进入这个方法：
 */
public class FilterTest {

    public static void main(String[] args) {

        List<String> list = Arrays.asList("java","c#","php","python");

        List<String> newList=list.stream().filter((x) -> x.length()>4).collect(Collectors.toList());

        System.out.printf("Original List : %s, filtered list : %s %n",
                list, newList);
    }
}
