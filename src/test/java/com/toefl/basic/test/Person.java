package com.toefl.basic.test;

/**
 * @author hai
 * @description
 * @date 16:51 2018/6/28
 */
public class Person {
    private final int id;
    private final String name;
    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
