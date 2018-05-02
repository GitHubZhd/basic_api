package com.toefl.basic.controller;

import java.io.File;

public class Test2 {

    public static void main(String[] args) {

        File file=new File("D:\\老托74-93加时间.xlsx");
        if(file.isDirectory()){
            System.out.println("directory");
            System.out.println("------");
            System.out.println("------");
            return;
        }else {
            System.out.println(file.getParent());
            return;
        }
    }
}
