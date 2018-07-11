package com.toefl.basic.test;

/**
 * @author hai
 * @description
 * @date 16:51 2018/6/28
 */
public interface PersonDao {
    Person getPerson(int id);
    boolean update(Person person);
}