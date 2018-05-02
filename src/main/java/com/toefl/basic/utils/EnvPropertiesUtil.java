package com.toefl.basic.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by neo on 16-9-20.
 */
public class EnvPropertiesUtil {
    private static Properties pro = new Properties();
    static {
        try {
            String env = System.getenv("resources_path");
            if (null == env) {
                env = System.getProperty("resources_path");
            }
            InputStream in = EnvPropertiesUtil.class.getResourceAsStream("/"+env+"/env.properties");
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String key){
        return pro.getProperty(key);
    }

    public static List<String> getList(String pName){
        String p = EnvPropertiesUtil.getProperties(pName);
        List<String> list = Arrays.asList(p.split(","));
        return  list;
    }
}
