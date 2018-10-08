package com.huayu.irla.privilege.manage.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class PrivilegeTool {

    /**
     * 得到HttpEntity实体里面的对象内容
     * @param request
     * @return
     */
    public static JSONObject getObjJson(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String logInfo = sb.toString();
            JSONObject tmpObj = JSONObject.parseObject(logInfo);
            return tmpObj;
        } catch (Exception ex) {
            Logger.getLogger(PrivilegeTool.class).error(ex);
        } 
        return null;
    }
    
    /**
     * 得到真实ip地址，多层代码，第一个ip地址即为真实ip
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        } 
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) { 
          ip = request.getRemoteAddr(); 
        } 
        //取第一个ip地址
        if(ip != null) {
        	String[] ips = ip.split("\\s*[,]\\s*");
        	if(ips.length>0) {
        		return ips[0];
        	}
        }
        return "";
      } 
}
