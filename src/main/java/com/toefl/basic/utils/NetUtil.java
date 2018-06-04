package com.zhan.common.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

/**
 * @author hai
 */
public class NetUtil {
    private static Logger log = LoggerFactory.getLogger(NetUtil.class);

    /**
     * 从连接池中获取可用连接最大超时时间 单位：毫秒
     */
    final static int CONNECT_REQUEST_TIMEOUT = 60000;
    /**
     * 连接目标url最大超时 单位：毫秒
     */
    final static int CONNECT_TIMEOUT = 60000;
    /**
     *  等待响应（读数据）最大超时 单位：毫秒
     */
    final static int SOCKET_TIMEOUT = 60000;

    /**
     * 连接池中的最大连接数
     */
    final static int MAX_CONN_TOTAL = 10;
    /**
     * 连接同一个route最大的并发数
     */
    final static int MAX_CONN_PER_ROUTE = 10;



    static {
            disableSslVerification();
    }

    private static void disableSslVerification() {
        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
    private static CloseableHttpClient buildSSLCloseableHttpClient()
            throws Exception {
        // 信任所有
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
                (TrustStrategy) (chain, authType) -> true).build();
        // ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
        HostnameVerifier hostnameVerifier= (s, sslSession) -> true;
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, new String[] { "TLSv1" }, null,
                hostnameVerifier);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        return HttpClients.custom().setMaxConnTotal(MAX_CONN_TOTAL)
                .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                .setDefaultRequestConfig(requestConfig).setSSLSocketFactory(sslsf).build();
    }

    public static String doPost(String url, String json,boolean isAccept) {

        log.info("json:{}",json);
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpclient =null; //HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        try {
            httpclient=buildSSLCloseableHttpClient();
            if (json != null) {
                StringEntity s = new StringEntity(json, "UTF-8");
                s.setContentType("application/json");// 发送json数据需要设置contentType
                post.setEntity(s);
            }
            if(isAccept){
                post.setHeader("accept", "application/json");
            }
            HttpResponse res = httpclient.execute(post);
            long endTime = System.currentTimeMillis();
            log.info("响应时间：url:{},time:{}",url,(endTime - startTime)+"毫秒");
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(entity, "utf-8");// 返回json格式：
                return result;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public static String doGet(String url) {
        try {
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            // 使用 URL 连接进行输出
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("contentType", "utf-8");
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);
            return startRequest(connection,null,null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            log.info("http post error ,url:{} ,error message : {}",url,e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("http post error ,url:{} ,error message : {}",url,e.getMessage());
        }
        return null;
    }

    public static JSONObject get(String url, Map<String,Object> param, Map<String, String> heads) {
        try {
            url = addParam(url,param);
            URL getUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            // 使用 URL 连接进行输出
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("contentType", "utf-8");
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);
            JSONObject json = JSONObject.fromObject(startRequest(connection,heads,null));
            return json;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            log.info("http post error ,url:{} ,error message : {}",url,e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("http post error ,url:{} ,error message : {}",url,e.getMessage());
        }
        return null;
    }

    public static JSONObject post(String requestUrl, Map<String,Object> param, Map<String, String> heads) {
        try {
            URL getUrl = new URL(requestUrl);

            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            // 使用 URL 连接进行输出
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("contentType", "utf-8");
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);
            JSONObject json = JSONObject.fromObject(startRequest(connection,heads,param));
            return json;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            log.info("http post error ,url:{} ,error message : {}",requestUrl,e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("http post error ,url:{} ,error message : {}",requestUrl,e.getMessage());
        }
        return null;
    }

    public static JSONObject post(String requestUrl, String body) {
        try {
            long startTime = System.currentTimeMillis();
            URL getUrl = new URL(requestUrl);

            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            // 使用 URL 连接进行输出
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("contentType", "utf-8");
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);
            byte[] byts = body.getBytes("UTF-8");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(byts);
            outputStream.close();
            connection.connect();
            InputStream is;
            if (connection.getResponseCode() >= 400) {
                is = connection.getErrorStream();
            } else {
                is = connection.getInputStream();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String lines;
            StringBuffer sb = new StringBuffer();
            while ((lines = reader.readLine()) != null) {
                sb.append(lines);
            }
            reader.close();
            connection.disconnect();
            long endTime = System.currentTimeMillis();
            log.info("响应时间：url:{},time:{}",requestUrl,(endTime - startTime));
            return JSONObject.fromObject(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            log.info("http post error ,url:{} ,error message : {}",requestUrl,e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("http post error ,url:{} ,error message : {}",requestUrl,e.getMessage());
        }
        return null;
    }
    private static String startRequest(HttpURLConnection connection,Map<String, String> heads,Map<String,Object> param) throws IOException {
        long startTime = System.currentTimeMillis();
        if (null != heads) {
            for (String key : heads.keySet()) {
                connection.setRequestProperty(key, heads.get(key));
            }
        }
        if (null != param) {
            byte[] byts = NetUtil.getParam(param).getBytes("UTF-8");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(byts);
            outputStream.close();
        }
        connection.connect();
        InputStream is;
        if (connection.getResponseCode() >= 400) {
            is = connection.getErrorStream();
        } else {
            is = connection.getInputStream();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
        String lines;
        StringBuffer sb = new StringBuffer();
        while ((lines = reader.readLine()) != null) {
            sb.append(new String(lines.getBytes()));
        }
        reader.close();
        connection.disconnect();
        long endTime = System.currentTimeMillis();
        log.info("响应时间：time:{}",(endTime - startTime)+"毫秒");
        return sb.toString();
    }

    public static String getParam(Map<String,Object> param) throws UnsupportedEncodingException {
        if(null == param || param.size() <=0 ){
            return "";
        }
        Set<String> set = param.keySet();
        StringBuffer sb = new StringBuffer();
        for (Object p:set){
            sb.append(p+"=");
            Object x = param.get(p);
            if (x instanceof String) {
                sb.append(URLEncoder.encode(String.valueOf(x),"utf-8"));
            } else {
                sb.append(x);
            }
            sb.append("&");
        }
        return sb.toString();
    }

    public static String addParam(String url,Map<String,Object> param) throws UnsupportedEncodingException {
        if(null == param || param.size() <=0 ){
            return url;
        }
        Set<String> set = param.keySet();
        StringBuffer sb = new StringBuffer();
        for (String p:set){
            sb.append(p+"=");
            if (param.get(p) instanceof String) {
                sb.append(URLEncoder.encode(String.valueOf(param.get(p)), "UTF-8"));
            } else {
                sb.append(param.get(p));
            }
            sb.append("&");
        }
        sb.deleteCharAt(sb.length()-1);
        if (url.indexOf("?") > -1) {
            String x = url.endsWith("?")?"":(url.endsWith("&")?"":"&");
            sb.insert(0,x);
        } else {
            sb.insert(0,"?");
        }
        sb.insert(0,url);
        return sb.toString();
    }
}
