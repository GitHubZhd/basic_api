package com.toefl.basic.io;

/**
 * 小括号里的资源最后一句可以加“;”，但其实没有必要，可去掉。想要关闭的资源放里面，不需要在finally里 JDK1.7新增
 *
 * 注：关于带资源的try语句的3个关键点：
 *    由带资源的try语句管理的资源必须是实现了AutoCloseable接口的类的对象。
 *    在try代码中声明的资源被隐式声明为fianl。
 *    通过使用分号分隔每个声明可以管理多个资源。
 *
 *  声明资源时要分析好资源关闭顺序,先声明的后关闭
 *
 *  try-catch-resources
 *
 *  在使用try-catch-resources语法创建的资源抛出异常后，JVM会自动调用close 方法进行资源释放，当没有抛出异常正常退出try-block时候也会调用close方法
 */
public class AutoCloseableTest {

    public static void main(String[] args) {

        //示例，声明自己的两个资源类，实现AutoCloseable接口。
        try (MyResource myResource = new MyResource();
             MyResource2 myResource2 = new MyResource2()) {
            myResource.readResource();
            myResource2.readResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyResource implements AutoCloseable {

    @Override
    public void close() {
        System.out.println("close resource");
    }

    public void readResource() {
        System.out.println("read resource");
    }

}

class MyResource2 implements AutoCloseable {

    @Override
    public void close() {
        System.out.println("close resource2");
    }

    public void readResource() {
        System.out.println("read resource2");
    }

}
