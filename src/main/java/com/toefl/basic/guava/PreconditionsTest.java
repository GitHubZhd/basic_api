package com.toefl.basic.guava;

import com.google.common.base.Preconditions;

public class PreconditionsTest {

    public static void main(String[] args) {

        Preconditions.checkArgument(1==2,"结果不一致");
    }
}
