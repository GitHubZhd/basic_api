package com.toefl.basic.controller;

import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisHighAccess {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        final Jedis jedis = new Jedis("localhost", 6379);
        jedis.lpush("list","1","2","3","4","5");
        jedis.close();

        for (int i = 0; i < 10000; i++) {// 测试一万人同时访问
            executor.execute(() -> {
                Jedis jedis1 = new Jedis("localhost", 6379);
                try{
                    String userid=UUID.randomUUID().toString().replace("-","");
                    if(jedis1.lpop("list")!=null){
                        System.out.println("用户：" + userid + "抢购成功");
                    }else {
//                        System.out.println("用户：" + userid + "抢购失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}
