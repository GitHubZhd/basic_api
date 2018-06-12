package com.toefl.basic.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toefl.basic.utils.NetUtil;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hai
 */
@RestController
@RequestMapping("/net/json")
public class NetSfJsonTestController {

    /**
     * 活动信息接口
     * @return
     */
    @RequestMapping(value = "/activityDetail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String activityDetail() {
        try {
            String requestUrl="https://uatermapi.tpooo.net/PublicClass/GetActivityDetails?activityId=12714";
            String result=NetUtil.doGet(requestUrl);
            System.out.println(result);
            JSONObject jsonObject = JSONObject.fromObject(result);
            String json = "{\"Code\": 0, \"Message\": \"\", \"Data\":"+jsonObject.getJSONObject("Data").toString()+" }";
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 活动信息接口
     * @return
     */
    @RequestMapping(value = "/activityDetailV", method = RequestMethod.GET)
    public Result activityDetailV() {
        try {
            JsonConfig cfg = new JsonConfig();
            cfg.setJavaPropertyFilter((source, name, value) -> {
                //value为null时返回true，返回true的就是需要过滤调的
                return value == null;
            });

            cfg.setJsonPropertyFilter((o, s, o1) -> o1 == null);

            String requestUrl="https://uatermapi.tpooo.net/PublicClass/GetActivityDetails?activityId=12714";
            String result=NetUtil.doGet(requestUrl);
            JSONObject jsonObject = JSONObject.fromObject(result,cfg);
            return new Result(Result.Status.OK,jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Result.Status.ERROR,"error");
        }
    }

    /**
     * test
     * @return
     */
    @RequestMapping(value = "/zhanTuan/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String activity() {
        try {
            String requestUrl="http://localhost:8066/zhanTuan/list";
            String result=NetUtil.doGet(requestUrl);
            System.out.println(result);
            JSONObject jsonObject = JSONObject.fromObject(result);
            String json = "{\"Code\": 0, \"Message\": \"\", \"Data\":"+jsonObject.getJSONObject("Data").toString()+" }";
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
