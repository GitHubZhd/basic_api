package com.toefl.basic.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Predicate2 {

    public static void main(String[] args) {

        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        Predicate<String> p1=(n) -> n.startsWith("J");
        Predicate<String> p2=(n) -> n.length()==4;

        languages.stream().filter(p1.and(p2)).forEach(System.out::println);

    }
}
