package com.toefl.basic.nio;

public class TimeServer {

    public static void main(String[] args) {
        int port = 8020;
        if(args!=null && args.length>0){
            port = Integer.valueOf(args[0]);
        }

        MultTimeServer server = new MultTimeServer(port);
        new Thread(server).start();
    }
}
