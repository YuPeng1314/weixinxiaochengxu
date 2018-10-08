package com.huayu.core.send;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * 远程连接通信
 * @author stef-shiqing
 *
 */
public class HttpConnection {
	
	 /**
	  * 得到返回值
	  * @param urlget
	  * @return
	  */
	 public static String getHttpResult(String urlget) throws Exception {
		   StringBuffer result = new StringBuffer();
		   InputStreamReader  bis = null;
		   try {
		     URL url = new URL(urlget);
		        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		        
		        
		        httpURLConnection.setDoOutput(true);
		        httpURLConnection.setDoInput(true);
		        httpURLConnection.setConnectTimeout(10000);
		        httpURLConnection.setRequestMethod("GET");
		        httpURLConnection.connect();  
		        bis = new InputStreamReader(httpURLConnection.getInputStream(),"utf-8");
		        int c = 0;
		        while((c = bis.read()) != -1){        
		        	result.append((char)c);   
		        }
		   } finally {
			   if(null != bis) {
				   try {
					bis.close();
				} catch (IOException e) {
					Logger.getLogger(HttpConnection.class).error(e);
				}
			   }
		   } 
		 
		   return result.toString();
	 }
}
