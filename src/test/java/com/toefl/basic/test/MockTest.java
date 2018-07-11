package com.toefl.basic.test;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;
/**
 * @author hai
 * @description
 * @date 16:39 2018/6/28
 */
public class MockTest {

    @Test
    public void test1(){

        //create mock  创建 Mock 对象的语法为 mock(class or interface)
        List mockedList = mock(List.class);
        //use mock object
        mockedList.add("one");

        when(mockedList.add("one")).thenReturn(true);
        when(mockedList.add("one")).thenThrow(new RuntimeException());
//        mockedList.clear();
//        //验证add方法是否在前面被调用了一次，且参数为“one”。clear方法同样。
//        verify(mockedList).add("one");
//        verify(mockedList).clear();
//        //下面的验证会失败。因为没有调用过add("two")。
//        verify(mockedList).add("two");
    }
}
