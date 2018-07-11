package com.toefl.basic.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class test3 {

    public static void main(String[] args) {

        String source = "\\xe5\\xad\\xa6\\xe4\\xb9\\xa0\\xe5\\xb0\\x8f\\xe7\\xbb\\x84";

        try {
            String decode = URLDecoder.decode(source, "UTF-8");
            System.out.println(decode);



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
