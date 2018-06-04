package com.toefl.basic.guava;

import com.google.common.base.Optional;

public class OptionalTest {

    public static void main(String[] args) {

        Optional<Integer> possible= Optional.of(100);
        if(possible.isPresent()){
            System.out.println(possible.get());
            System.out.println(possible.asSet());
            possible=Optional.absent();
//            System.out.println(possible.get());
            System.out.println(possible.or(50));
            System.out.println(possible.orNull());
            System.out.println(possible.asSet());
        }

    }
}
