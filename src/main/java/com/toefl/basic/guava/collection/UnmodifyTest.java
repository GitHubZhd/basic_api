package com.toefl.basic.guava.collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;

import java.util.*;

/**
 * 不可变对象有很多优点，包括：
 *
 * 当对象被不可信的库调用时，不可变形式是安全的；
 * 不可变对象被多个线程调用时，不存在竞态条件问题
 * 不可变集合不需要考虑变化，因此可以节省时间和空间。所有不可变的集合都比它们的可变形式有更好的内存利用率（分析和测试细节）；
 * 不可变对象因为有固定不变，可以作为常量来安全使用。
 */
public class UnmodifyTest {

    public static void main(String[] args) {

//        List<String> list=new ArrayList<>();
//        list.add("1213");
//        list.add("2547");
//        list.add("asda");

        /**
         * 笨重而且累赘：不能舒适地用在所有想做防御性拷贝的场景；
         * 不安全：要保证没人通过原集合的引用进行修改，返回的集合才是事实上不可变的；
         * 低效：包装过的集合仍然保有可变集合的开销，比如并发修改的检查、散列表的额外空间，等等。
         */
//        List<String> list1=Collections.unmodifiableList(list);


        /**
         * 重要提示：所有Guava不可变集合的实现都不接受null值。我们对Google内部的代码库做过详细研究，发现只有5%的情况需要在集合中允许null元素，剩下的95%场景都是遇到null值就快速失败。
         * 如果你需要在不可变集合中使用null，请使用JDK中的Collections.unmodifiableXXX方法。更多细节建议请参考“使用和避免null”。
         */

//        ImmutableSet<String> foobar = ImmutableSet.of("foo", "bar", "baz",null); //java.lang.NullPointerException
        ImmutableSet<String> foobar = ImmutableSet.of("foo", "bar", "baz");
//        ImmutableList<String> fList=foobar.asList();
        List<String> fList=foobar.asList();
        System.out.println(fList);
        doSomething(foobar);

        //构造方法一: copyOf方法
        List<String> list=Arrays.asList("1","3","2");
        ImmutableSet<String> immutableSet1 = ImmutableSet.copyOf(list);
        immutableSet1.forEach(System.out::println);
        System.out.println("-----------------------");
        //构造方法二: of方法
        ImmutableSet<String> immutableSet2 = ImmutableSet.of("a", "c", "b");
        immutableSet2.forEach(System.out::println);
        System.out.println("-----------------------");
        //构造方法三: Builder工具
        ImmutableSet<Color> immutableSet3 = ImmutableSet.<Color>builder()
                .add(new Color(0, 191, 255))
                .add(new Color(12, 191, 255))
                .add(new Color(14, 191, 255))
                .build();
        immutableSet3.forEach(System.out::println);
        System.out.println("-----------------------");

        //此外，对有序不可变集合来说，排序是在构造集合的时候完成的，如：
        ImmutableSortedSet<String> sortedSet = ImmutableSortedSet.of("e","a", "c", "b", "a", "d", "b");
        UnmodifiableIterator<String> iterator = sortedSet.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());//a, b, c, d
        }

    }

    static void doSomething(Collection<String> collection){
        ImmutableList<String> defensiveCopy = ImmutableList.copyOf(collection);
        System.out.println("======================");
        System.out.println(defensiveCopy.asList());
        Iterator<String> iterator=defensiveCopy.asList().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    static class Color{
        private int r;
        private int g;
        private int b;

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public Color(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        @Override
        public String toString() {
            return "Color{" +
                    "r=" + r +
                    ", g=" + g +
                    ", b=" + b +
                    '}';
        }
    }
}
