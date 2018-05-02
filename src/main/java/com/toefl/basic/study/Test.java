package com.toefl.basic.study;

public class Test {

    public static void main(String[] args) {

       check(1,2,3);
        System.out.println((1|2|-1));
        System.out.println((1|2|3));
        System.out.println((1|-2|-1));
        System.out.println((1|0|-6));
    }

    public static void check(int a, int b, int c){
        if((a|b|c)<2){
            System.out.println("----------");
        }
    }
}
