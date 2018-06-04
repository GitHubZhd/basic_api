package com.toefl.basic.lambda;

import java.util.Arrays;
import java.util.List;

public class ListTest {

    public static void main(String[] args) {

        List<String> features = Arrays.asList("Lambdas", "Default Method",
                "Stream API", "Date and Time API");
//        for (String feature : features) {
//            System.out.println(feature);
//        }

        features.stream().forEach((element) -> System.out.println(element));
        features.stream().forEach(System.out::println);

    }
}
