package com.toefl.basic.controller;

import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thonatos on 2017/6/22.
 */
public class Main {

    public static void main(String[] args) {

        // secret
        String secret = "Uncle_Suyi";
        JWT jwt = new JWT(secret);

        // payload
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("name", "suyi");

        // expiration
        Date expiration = new Date(System.currentTimeMillis() + 5 * 60 * 1000);

        // sign
        String token = jwt.sign(payload, expiration);
        System.out.println(token);

        // verify
        Claims claims = jwt.verify(token);
        System.out.println(claims.toString());
    }
}
