package com.toefl.basic.controller;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisHighAccess1 {

    private static final String LOCK_KEY="LockKey";

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(20);
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("key","1");
        jedis.close();

        try{
            for (int i = 0; i <1000 ; i++) {
                executor.execute(() -> {
                    Jedis jedis1 = new Jedis("localhost", 6379);
                    String userid=UUID.randomUUID().toString().replace("-","");
                    if(tryGetDistributedLock(jedis1,LOCK_KEY,userid,10000)){
                        String temp=jedis1.get("key");
                        if(Integer.valueOf(temp)<50){
                            jedis1.incr("key");
                            System.out.println("用户：" + userid + "抢购成功，当前抢购成功人数:"+temp);
                        }else {
                            System.out.println("失败");
                        }
                        releaseDistributedLock(jedis1,LOCK_KEY,userid);
                    }else {
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
