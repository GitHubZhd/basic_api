package com.toefl.basic.controller;

import it.sauronsoftware.jave.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedResource(objectName = "spitter:name=TestController")
@RestController
public class TestController {

    private RedisTemplate redisTemplate=new RedisTemplate();

    public static final int DEFAULT_PER=25;

    private int per=DEFAULT_PER;

    @ManagedAttribute
    public int getPer() {
        return per;
    }

    @ManagedAttribute
    public void setPer(int per) {
        this.per = per;
    }

    @RequestMapping("/select")
    public Object get(){
        Map<String,Object> map=new HashMap<>();
        map.put("Task","12313131313");
        System.out.println(per);
        return map;
    }


    @RequestMapping("/ss")
    public Object fff(){

        File source = new File("source.avi");
        File target = new File("target.avi");
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(new Integer(56000));
        audio.setChannels(new Integer(1));
        audio.setSamplingRate(new Integer(22050));
        VideoAttributes video = new VideoAttributes();
        video.setCodec(VideoAttributes.DIRECT_STREAM_COPY);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("avi");
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        Encoder encoder = new Encoder();
        try {
            encoder.encode(source, target, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        }

        Map<String,Object> map=new HashMap<>();
        map.put("Task","12313131313");
        return map;
    }

    @RequestMapping("/upload")
    public Object upload(@RequestParam(value = "file", required = false) MultipartFile file){

        System.out.println(file.getContentType());
        System.out.println(file.getOriginalFilename());

        return "";
    }

    @RequestMapping("/upload/file")
    public Object file_upload(String filepath){
//        C:\Users\tlfu\Documents\QQEIM Files\2885008013\FileRecv\老托74-93加时间.xlsx
        return "";
    }

    /**
     * /**
     *      * 　(.*)(<li class=\'dn on\' data-dn=\'7d1\'>)(.*?)(</li>)(.*) 这个正则表达式，很容易看出可以分为下面5组：
     *
     *      　　(.*)　　　　　　　　　　　　　　　　　　　　　　　    ：匹配除换行符外任意东西0-N次
     *
     *      　　(<li class=\'dn on\' data-dn=\'7d1\'>)　　　　：匹配中间那段heml代码一次
     *
     *      　　(.*?)　　　　　  : .*?为匹配的懒惰模式，意思是匹配除换行符外任意东西尽可能少次
     *
     *      　　(</li>)　　 ：匹配中间那段html代码一次
     *
     *      　　(.*)　　　　：匹配除换行符外任意东西0-N次
     *      * @param html
     *      * @return
     *      */
    public static void main(String[] args) {

        String s="74.巅峰对决是否会。重新发的说法。回合肥回复回复？定时达大。防守对方的说法？准许证！";
        String s1="除了探寻戏剧的起源，学者们还从人类发展戏剧的动机上建立理论。@@为什么戏剧会发展，为什么在戏剧完全脱离宗教仪式以后还有这么大的价值？@@大部分答案都回到那些关于人类心智和人类基本需求的理论中。@@首先，亚里士多德在公元前4世纪提出，人们天生好模仿，并从模仿他人、事物和动作以及观看模仿中获得乐趣。@@另外，20世纪提出的先进理论认为人类擅长幻想，通过幻想将日常生活中的现实重塑成更加令人满意的形式。@@因此，人们通过幻想或虚构（戏剧的一个形式）把他们的焦虑和恐惧具体化，再通过这种方式面对焦虑和恐惧，并从虚构中满足他们现实中无法实现的愿望。@@所以，戏剧成为了一种帮助人们认识和理解这个世界，或是帮助人们逃避不满现实的工具。@@";;

        handleCn(s);
    }

    public static String[] handleCn(String str) {
        Map map = new HashMap();
        // 替换特使字符
        str = replaceString(str, getParam());
        str = str.replaceAll("@@", "");
        str = str.replaceAll("(\\d+)(\\.)(\\d+)", "$1#=#$3");
        Pattern pattern = Pattern.compile("(?!\\s).+?(?:[!?.]|$)");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer("");
        int num = 0;
        List<String> list=new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group(0).replace(".","。"));
            sb.append(matcher.group(0)).append("\n");
        }


        // 替换回来特殊字符
        String resultStr = sb.toString().replace(".","。");
        System.out.println(resultStr);

        String[] messArr = new String[list.size()];
        list.toArray(messArr);
        return messArr;
    }

    public static Map<String, String> getParam() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("A\\.D\\.", "#ad#");
        map.put("B\\.C\\.", "#bc#");
        map.put("Mr\\.", "#mr#");
        map.put("Dr\\.", "#dr#");
        map.put("\\.\\.\\.\\.\\.", "#5d#");
        map.put("\\.\\.\\.\\.", "#4d#");
        map.put("\\.\\.\\.", "#3d#");
        map.put("\\.\\.", "#2d#");

        map.put("。", ".");
        map.put("？", "?");
        map.put("！", "!");
        map.put("\n","#enter#");

        return map;
    }

    public static Map<String, String> getParam2() {
        Map<String, String> map = new LinkedHashMap<String, String>();
//		map.put(".", "。");
//		map.put("?", "？");
//		map.put("!", "！");

        map.put("#ad#", "A.D.");
        map.put("#bc#", "B.C.");
        map.put("#mr#", "Mr.");
        map.put("#dr#", "Dr.");
        map.put("#5d#", ".....");
        map.put("#4d#", "....");
        map.put("#3d#", "...");
        map.put("#2d#", "..");
        map.put("#enter#","\n");
        return map;
    }

    public static String replaceString(String str, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            str = str.replaceAll(entry.getKey(), entry.getValue());
        }
        //System.out.println(str);
        return str;

    }






}
