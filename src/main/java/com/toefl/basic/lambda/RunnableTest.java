package com.toefl.basic.lambda;

public class RunnableTest {

    public static void main(String[] args) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("1111111111111");
//            }
//        }).start();

        new Thread(() -> System.out.println("1111111111111")).start();
    }
}
