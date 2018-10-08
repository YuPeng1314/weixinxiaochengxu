package com.huayu.core.send;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {
	
	 /**
     * 发送get请求（json格式）
     *
     * @return result
     */
    public static String sendGetRequest(String reqURL, Map<String, String> params, Map<String, String> header) throws Exception {
    	HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    	httpClientBuilder.setConnectionTimeToLive(30, TimeUnit.SECONDS);
        CloseableHttpClient  client = httpClientBuilder.build();
        
        //返回值
        String result = "";
        try {
        	
             //拼参数信息
             if(null!=params && !params.isEmpty()) {
            	 Set<String> keyStr = params.keySet();
            	 StringBuffer paramBuf = new StringBuffer();
            	 for(String tmpStr:keyStr) {
            		 paramBuf.append(tmpStr).append("=").append(URLEncoder.encode(params.get(tmpStr), "UTF-8")).append("&");
            	 }
            	 if(paramBuf.length()>0) {
            		 String reqUrl = paramBuf.substring(0, paramBuf.length()-1);
            		 reqURL += "?"+reqUrl;
            	 }
            	 
             }
             
             HttpGet httpGet = new HttpGet(reqURL);
             //拼head信息
             if(null!=header && !header.isEmpty()) {
            	 Set<String> keyStr = header.keySet();
            	 for(String tmpStr:keyStr) {
            		 httpGet.setHeader(tmpStr,header.get(tmpStr));
            	 }
             }
             
             
             HttpResponse response = client.execute(httpGet);
             StatusLine statusObj = response.getStatusLine();
             if(statusObj.getStatusCode()==200) {
            	 result = EntityUtils.toString(response.getEntity(), "UTF-8");
             }
             
        } catch (Exception e) {
            Logger.getLogger(HttpUtils.class).error("请求[" + reqURL + "]时有异常", e);  
            throw e;
        }finally{
            try {
				client.close();
			} catch (IOException e) {
				Logger.getLogger(HttpUtils.class).error(e);
			}
        }
        return result;
    }
	
	 /**
     * 发送Post请求（json格式）
     * @return result
     */
    public static String sendPostRequest(String reqURL, String data, Map<String, String> header, String contextType){
    	HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    	httpClientBuilder.setConnectionTimeToLive(30, TimeUnit.SECONDS);
        CloseableHttpClient  client = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(reqURL);
        HttpResponse response = null;
        String result = "";
        try {
        	 if(StringUtils.isNotBlank(data)) {
	             StringEntity entity = new StringEntity(data, "UTF-8");
	             entity.setContentType(contextType);
	             httpPost.setEntity(entity);
        	 }
             if(null!=header && !header.isEmpty()) {
            	 Set<String> keyStr = header.keySet();
            	 for(String tmpStr:keyStr) {
            		 httpPost.setHeader(tmpStr,header.get(tmpStr));
            	 }
             }
             
             response = client.execute(httpPost);
             System.out.println("响应请求:" + response.getStatusLine().getStatusCode());
             result =EntityUtils.toString(response.getEntity(), "UTF-8");
             
             System.err.println("tokenStr值:" + result);
        } catch (Exception e) {
        	e.printStackTrace();
            Logger.getLogger(HttpUtils.class).error("请求[" + reqURL + "]时有异常", e);  
        }finally{
            try {
				client.close();
			} catch (IOException e) {
				Logger.getLogger(HttpUtils.class).error(e);
			}
        }
        return result;
    }
}
