package com.toefl.basic.controller.weixin;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 微信access_token 获取、获取jsapiTicket
 * @author hai
 */
public class JsapiTicketUtil {

    /***
     * 模拟get请求
     * @param url
     * @param charset
     * @param timeout
     * @return
     */
    public static String sendGet(String url, String charset, int timeout)
    {
        String result = "";
        try
        {
            URL u = new URL(url);
            try
            {
                URLConnection conn = u.openConnection();
                conn.connect();
                conn.setConnectTimeout(timeout);
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
                String line="";
                while ((line = in.readLine()) != null)
                {

                    result = result + line;
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return result;
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            return result;
        }

        return result;
    }
    /***
     * 获取acess_token
     * 来源www.vxzsk.com
     * @return
     */
    public static String getAccessToken(){
        //应用ID-----你公众号基本设置里的应用id
        String appid="wx55095301b91743da";
        //(应用密钥)-----你公众号基本设置里的应用密钥
        String appSecret="67eb2cc82eb2ef1f92a7a633f6552048";
        String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret+"";
        String backData=sendGet(url, "utf-8", 10000);
        String accessToken = (String) JSONObject.fromObject(backData).get("access_token");
        System.out.println(accessToken);
        return accessToken;
    }
    /***
     * 获取jsapiTicket
     * 来源 www.vxzsk.com
     * @return
     */
    public static String getJSApiTicket(){
        //获取token
        String acess_token= JsapiTicketUtil.getAccessToken();

        String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+acess_token+"&type=jsapi";
        String backData=sendGet(urlStr, "utf-8", 10000);
        String ticket = (String) JSONObject.fromObject(backData).get("ticket");
        return  ticket;

    }
    public static void main(String[] args) {
        String jsapiTicket = JsapiTicketUtil.getJSApiTicket();
        System.out.println("调用微信jsapi的凭证票为："+jsapiTicket);
    }
}