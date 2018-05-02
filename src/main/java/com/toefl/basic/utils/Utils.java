package com.toefl.basic.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toefl.basic.dto.Lrc;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Utils {

    private static Gson gson=new GsonBuilder().disableHtmlEscaping().create();

    public static void convertLrcModel(String[] lrcArr,String[] messArr){
        List<Lrc> list=new ArrayList();

        for (int i = 0,j=1; i <lrcArr.length && j<messArr.length ; i++,j++) {
            Lrc lrc=new Lrc();
            lrc.setTime(lrcArr[i].split("\\|\\|")[1]);
            lrc.setEn(lrcArr[i].split("\\|\\|")[2]);
            lrc.setCn(messArr[i+1]);
            list.add(lrc);
        }

        String ss=gson.toJson(list);
        System.out.println(ss);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String fileName="toefl_old_"+sdf.format(new Date())+"_"+messArr[0].replace("。","")+".json";
        write(fileName,ss);

    }

    public static void write(String fileName,String json){
//        File f = new File("D:\\lrc\\"+fileName) ;
        File f = new File("D:\\zhanghaidong\\temp\\audio\\template\\template\\"+fileName) ;
        OutputStream out = null;
        try {
            out = new FileOutputStream(f) ;
            out.write(json.getBytes());
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                try{
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 返回长度为【strLength】的随机数，在前面补0
     */
    private static String getFixLenthString(int strLength) {
        Random rm = new Random();
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }
}


