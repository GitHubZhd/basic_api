package com.toefl.basic.nio;

public class TimeClient {
    public static void main(String[] args) {
        int port = 8020;
        if(args!=null && args.length>0){
            port = Integer.valueOf(args[0]);
        }
        for (int i = 0; i <100 ; i++) {
            new Thread(new TimeClientHandler("127.0.0.1", port)).start();
        }
    }
}
