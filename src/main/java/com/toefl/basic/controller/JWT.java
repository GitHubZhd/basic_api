package com.toefl.basic.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * Created by thonatos on 2017/6/22.
 */
public class JWT {

    private byte[] secret;

    public JWT(String secret){
        this.secret = secret.getBytes();
    }

    public String sign(Map payload, Date expiration) {
        String token = Jwts.builder()
                .setClaims(payload)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return token;
    }

    public Claims verify(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return  claims;
    }

}
