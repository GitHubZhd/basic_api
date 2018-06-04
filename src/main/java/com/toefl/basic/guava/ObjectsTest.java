package com.toefl.basic.guava;

import com.google.common.base.Objects;

public class ObjectsTest {

    public static void main(String[] args) {

        System.out.println(Objects.equal(1,2));
        System.out.println(Objects.equal(1,null));
        System.out.println(Objects.equal(null,null));

        System.out.println(Objects.hashCode(1111));
        System.out.println(Objects.hashCode("sdasdhasdajk"));
        java.util.Objects.toString(1111);



    }
}
