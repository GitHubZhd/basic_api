package com.toefl.basic.guava;

import com.google.common.collect.ComparisonChain;

public class ComparisonChainTest {

    public static void main(String[] args) {

        Person p1=new Person();
        p1.setFirstName("zhang");
        Person p2=new Person();
        p2.setFirstName("wang");

        System.out.println(p1.compare(p2));
    }

    static class Person{

        private String lastName;
        private String firstName;
        private int zipCode;

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public int getZipCode() {
            return zipCode;
        }

        public void setZipCode(int zipCode) {
            this.zipCode = zipCode;
        }

        public int compare(Person person){
            return ComparisonChain.start()
                    .compare(this.zipCode,person.getZipCode())
                    .compare(this.firstName,person.getFirstName())
                    .compare(this.lastName,person.getLastName())
                    .result();
        }
    }
}
