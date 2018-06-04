package com.toefl.basic.lambda;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class MapReduceTest {

    public static void main(String[] args) {
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
//        for (Integer cost : costBeforeTax) {
//            double price = cost + .12*cost;
//            System.out.println(price);
//        }

        //最流行的函数编程概念是map，它允许你改变你的对象
        costBeforeTax.stream().map((m) -> m + m * 0.12).forEach(System.out::println);
        costBeforeTax.stream().map((m) -> m + m * .12).forEach(System.out::println);

        double total = 0;
//        for (Integer cost : costBeforeTax) {
//            double price = cost + .12*cost;
//            total = total + price;
//        }
//        System.out.println("Total : " + total);

        //reduce() 是将集合中所有值结合进一个，Reduce类似SQL语句中的sum(), avg() 或count(),
        total=costBeforeTax.stream().map((num) -> num + num * .12).reduce((sum,num) -> sum + num).get();
        System.out.println(total);


        final List<BigDecimal> prices = Arrays.asList(
                new BigDecimal("10"), new BigDecimal("30"), new BigDecimal("17"),
                new BigDecimal("20"), new BigDecimal("15"), new BigDecimal("18"),
                new BigDecimal("45"), new BigDecimal("12"));

        final BigDecimal totalOfDiscountedPrices = prices.stream()
                .filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
                .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
                .reduce(new BigDecimal("100"), BigDecimal::add);

        final BigDecimal total2 = prices.stream()
                .filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
                .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                .reduce((sum,price) -> sum.add(price)).get();

        System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);
        System.out.println("Total of discounted prices: " + total2);
    }
}
