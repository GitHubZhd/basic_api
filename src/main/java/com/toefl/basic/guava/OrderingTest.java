package com.toefl.basic.guava;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderingTest {

    public static void main(String[] args) {

//        Ordering.natural().nullsFirst().onResultOf(new Function<Foo, Comparable>() {
//            @Override
//            public Comparable apply(Foo input) {
//                return input.sortedBy;
//            }
//        });

        List<Foo> alist=new ArrayList<>();
        alist.add(new Foo("1234",5));
        alist.add(new Foo("3434",8));
        alist.add(new Foo("1255",2));
        System.out.println("ordering pre"+alist);

        Ordering ordering1=Ordering.natural().nullsFirst().onResultOf(new Function<Foo, Comparable>() {
            @Override
            public Comparable apply(Foo input) {
                return input.sortedBy;
            }
        });

        Collections.sort(alist,ordering1);
        System.out.println("ordering after"+alist);


        List<Integer> numbers = new ArrayList<>();
        numbers.add(new Integer(5));
        numbers.add(new Integer(2));
        numbers.add(new Integer(15));
        numbers.add(new Integer(51));
        numbers.add(new Integer(53));
        numbers.add(new Integer(35));
        numbers.add(new Integer(45));
        numbers.add(new Integer(32));
        numbers.add(new Integer(43));
        numbers.add(new Integer(16));

        Ordering ordering = Ordering.natural();//从小到大
        System.out.println("Input List: ");
        System.out.println(numbers);

        Collections.sort(numbers,ordering );
        System.out.println("Sorted List: ");
        System.out.println(numbers);

        System.out.println("======================");
        System.out.println("List is sorted: " + ordering.isOrdered(numbers));
        System.out.println("Minimum: " + ordering.min(numbers));
        System.out.println("Maximum: " + ordering.max(numbers));

        Collections.sort(numbers,ordering.reverse());
        System.out.println("Reverse: " + numbers);

        numbers.add(null);
        System.out.println("Null added to Sorted List: ");
        System.out.println(numbers);

        Collections.sort(numbers,ordering.nullsFirst());
        System.out.println("Null first Sorted List: ");
        System.out.println(numbers);
        System.out.println("======================");

        System.out.println("获取可迭代对象中最大的k个元素"+Ordering.natural().nullsFirst().greatestOf(numbers,4));

        List<String> names = new ArrayList<>();
        names.add("Ram");
        names.add("Shyam");
        names.add("Mohan");
        names.add("Sohan");
        names.add("Ramesh");
        names.add("Suresh");
        names.add("Naresh");
        names.add("Mahesh");
        names.add(null);
        names.add("Vikas");
        names.add("Deepak");

        System.out.println("Another List: ");
        System.out.println(names);

        Collections.sort(names,ordering.nullsFirst().reverse());
        System.out.println("Null first then reverse sorted list: ");
        System.out.println(names);
    }
}

class Foo {
    @Nullable String sortedBy;
    int notSortedBy;

    public Foo(String sortedBy, int notSortedBy) {
        this.sortedBy = sortedBy;
        this.notSortedBy = notSortedBy;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "sortedBy='" + sortedBy + '\'' +
                ", notSortedBy=" + notSortedBy +
                '}';
    }
}

