package com.toefl.basic.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Test2 {

    public static void main(String[] args) {

        File file=new File("D:\\老托74-93加时间.xlsx");
//        if(file.isDirectory()){
//            System.out.println("directory");
//            System.out.println("++++++++++++");
//            return;
//        }else {
//            System.out.println(file.getParent());
//            return;
//        }

        SortedSet<Integer> set=new TreeSet<>();
        set.add(3);
        set.add(7);
        set.add(6);
        set.add(8);
        set.add(9);
        set.add(10);
        set.add(1);
        set.add(3);

        System.out.println(((TreeSet<Integer>) set).ceiling(0));

        StringBuilder stringBuilder=new StringBuilder();
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()){
            stringBuilder.append(iterator.next()).append(",");
        }
        System.out.println(stringBuilder.toString());

    }
}
