package com.huayu.irla.privilege.manage.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.huayu.irla.privilege.manage.dao.ISysMetaDataDao;

public class URLFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	protected final Log logger = LogFactory.getLog(getClass());
	
	//权限集合  
    private Map<String, Collection<ConfigAttribute>> requestMap = new HashMap<String, Collection<ConfigAttribute>>();  
      
    @Autowired  
    private ISysMetaDataDao sysMetoDataDao;  
      
    /* (non-Javadoc) 
     * @see org.springframework.security.access.SecurityMetadataSource#getAttributes(java.lang.Object) 
     */  
    @Override  
    public Collection<ConfigAttribute> getAttributes(Object object)  
            throws IllegalArgumentException {  
       String requestURL = ((FilterInvocation) object).getRequestUrl();
       //url处理，去掉后缀
       int pointIndex = requestURL.lastIndexOf(".");
       if(pointIndex != -1) {
    	   requestURL = requestURL.substring(0, pointIndex);
       }
       
       Collection<ConfigAttribute> metaData = requestMap.get(requestURL);
       if(null != metaData) {
    	   return metaData;
       }
       //从map中取到相似的URL,对于后台处理逻辑
       Set<String> keys = requestMap.keySet();
       Iterator<String> tempIt = keys.iterator();
       while(tempIt.hasNext()) {
    	   String urlKey = tempIt.next();
    	   if(StringUtils.isNotBlank(urlKey) && requestURL.startsWith(urlKey)) {
    		   return requestMap.get(urlKey);
    	   }
    	   
       }
       
       return null;  
    }  
  
    /* (non-Javadoc) 
     * @see org.springframework.security.access.SecurityMetadataSource#getAllConfigAttributes() 
     */  
    @Override  
    public Collection<ConfigAttribute> getAllConfigAttributes() {  
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();  
  
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {  
            allAttributes.addAll(entry.getValue());  
        }  
  
        return allAttributes;  
    }  
  
    /* (non-Javadoc) 
     * @see org.springframework.security.access.SecurityMetadataSource#supports(java.lang.Class) 
     */  
    @Override  
    public boolean supports(Class<?> clazz) {  
        return FilterInvocation.class.isAssignableFrom(clazz);  
    }  
      
    private Map<String,String> loadResuorce(List<String> types){  
        Map<String,String> map = new LinkedHashMap<String,String>();  
          
        List<Map<String,String>> list = this.sysMetoDataDao.getURLResourceMapping(types);  
        Iterator<Map<String,String>> it = list.iterator();  
        while(it.hasNext()){  
            Map<String,String> rs = it.next();  
            String resourcePath = rs.get("resourcePath");  
            String authorityMark = rs.get("authorityMark");  
              
            if(map.containsKey(resourcePath)){  
                String mark = map.get("resourcePath");  
                map.put(resourcePath, mark+","+authorityMark);  
            }else{  
                map.put(resourcePath, authorityMark);  
            }  
        }  
        return map;  
    }  
      
    protected Map<String, Collection<ConfigAttribute>> bindRequestMap(List<String> types){  
        Map<String, Collection<ConfigAttribute>> map =   
                new LinkedHashMap<String, Collection<ConfigAttribute>>();  
          
        Map<String,String> resMap = this.loadResuorce(types);  
        for(Map.Entry<String,String> entry:resMap.entrySet()){  
            String key = entry.getKey();  
            Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();  
            atts = SecurityConfig.createListFromCommaDelimitedString(entry.getValue());  
            map.put(key, atts);  
        }  
          
        return map;  
    }  
        
    public void refreshResuorceMap(List<String> types){  
        this.requestMap = this.bindRequestMap(types);  
    }  
  
}
