package com.toefl.basic;

import com.toefl.basic.controller.TestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.MBeanExporter;

import javax.servlet.MultipartConfigElement;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class BasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }


    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("20MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("20MB");
        return factory.createMultipartConfig();
    }

//    @Bean
//    public MBeanExporter mBeanExporter(TestController testController){
//
//        MBeanExporter exporter=new MBeanExporter();
//        Map<String,Object> beans=new HashMap<>();
//        beans.put("spitter:name=testController",testController);
//        exporter.setBeans(beans);
//        return exporter;
//
//    }
}
