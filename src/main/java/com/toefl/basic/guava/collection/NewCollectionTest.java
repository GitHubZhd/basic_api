package com.toefl.basic.guava.collection;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Iterator;
import java.util.Set;

public class NewCollectionTest {

    public static void main(String[] args) {

        Multiset<String> multiset=HashMultiset.create();
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("d");
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("b");
        multiset.add("b");
        multiset.add("b");
        multiset.add(null);
        //print the occurrence of an element
        System.out.println("Occurrence of 'b' : "+multiset.count("b"));
        //print the total size of the multiset
        System.out.println("Total Size : "+multiset.size());
        //get the distinct elements of the multiset as set
        Set<String> set = multiset.elementSet();
        //display the elements of the set
        System.out.println("Set [");
        for (String s : set) {
            System.out.println(s);
        }
        System.out.println("]");
        //display all the elements of the multiset using iterator
        Iterator<String> iterator  = multiset.iterator();
        System.out.println("MultiSet [");
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println("]");
        //display the distinct elements of the multiset with their occurrence count
        System.out.println("MultiSet [");
        for (Multiset.Entry<String> entry : multiset.entrySet())
        {
            System.out.println("Element: "+entry.getElement() +", Occurrence(s): " + entry.getCount());
        }
        System.out.println("]");

        //remove extra occurrences
        multiset.remove("b",2);
        //增加给定元素在Multiset中的计数
        multiset.add("b",10);
        //print the occurrence of an element
        System.out.println("Occurence of 'b' : "+multiset.count("b"));
    }
}
/**
 * 可以用两种方式看待Multiset：
 *
 * 没有元素顺序限制的ArrayList<E>
 * Map<E, Integer>，键为元素，值为计数
 * Guava的Multiset API也结合考虑了这两种方式：
 * 当把Multiset看成普通的Collection时，它表现得就像无序的ArrayList：
 *
 * add(E)添加单个给定元素
 * iterator()返回一个迭代器，包含Multiset的所有元素（包括重复的元素）
 * size()返回所有元素的总个数（包括重复的元素）
 * 当把Multiset看作Map<E, Integer>时，它也提供了符合性能期望的查询操作：
 *
 * count(Object)返回给定元素的计数。HashMultiset.count的复杂度为O(1)，TreeMultiset.count的复杂度为O(log n)。
 * entrySet()返回Set<Multiset.Entry<E>>，和Map的entrySet类似。
 * elementSet()返回所有不重复元素的Set<E>，和Map的keySet()类似。
 * 所有Multiset实现的内存消耗随着不重复元素的个数线性增长。
 */
