package com.huayu.irla.privilege.manage.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.privilege.manage.common.PrivilegeTool;
import com.huayu.irla.privilege.manage.dao.ISysUsersRepositoryDao;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;

public class SysUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	public static final String USERNAME = "username";    
    public static final String PASSWORD = "password"; 
    
    public static final int MAX_VISIT_NUM = 5;
    
    //清理缓存
  	@Autowired  
    private UserCache userCache;
    
    private UsernamePasswordAuthenticationToken authRequest = null;
    
	@Autowired  
    private  ISysUsersRepositoryDao sysUsersRepositoryDao;
	
	/**
	 * 登录次数控制开关
	 */
	private boolean loginVali = false;
    
    @Override
  	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
  			AuthenticationException failed) throws IOException, ServletException {
    	
    	if(loginVali) {
    		loginCache(authRequest, response,failed);
    	}
    	
    	if(null != authRequest) {
  			userCache.removeUserFromCache(authRequest.getName());
  		}
  		super.unsuccessfulAuthentication(request, response, failed);
  		
  	}

	
    @Override    
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {    
        if (!request.getMethod().equals("POST")) {    
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());    
        }    
          
        String username = obtainUsername(request);    
        String pd = obtainPassword(request);   
        
        //用户和密码为空的情况判断
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(pd)) {
        	JSONObject obj = PrivilegeTool.getObjJson(request);
        	if(null != obj) {
        		username = obj.getString("username");
        		pd = obj.getString("password");
        	}
        }
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(pd)) {
        	username = (String)request.getAttribute("username");
        	pd = (String)request.getAttribute("password");
        }
         
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(pd)) {
        	throw new AuthenticationServiceException("User Name or password don't null. method：" + request.getMethod()); 
        }
        	
        //验证用户账号与密码是否对应    
        username = username.trim();       
              
        authRequest = new UsernamePasswordAuthenticationToken(username, pd);    
        // Place the last username attempted into HttpSession for views    
            
        // 允许子类设置详细属性    
        setDetails(request, authRequest);    
            
        // 运行UserDetailsService的loadUserByUsername 再次封装Authentication    
        return this.getAuthenticationManager().authenticate(authRequest);    
    }        
    
    /**
     * 登录缓存次数判断
     * @param response 
     * @param failed 
     * @param authRequest2
     */
    private void loginCache(UsernamePasswordAuthenticationToken authRequest, HttpServletResponse response, AuthenticationException failed) {
		try {
			 if(failed instanceof LockedException) {
				 return;
			 }
			 //用户名不为空才进行判断
			 if(userCache.getUserFromCache(authRequest.getName())== null ){
				 return;
			 }
		     //登录失败，即缓存
			 Configuration config = new Configuration();
			 config.setName("privilegeManager");
			 CacheManager cacheManager = CacheManager.newInstance(config);
			 Cache cache = cacheManager.getCache("pdSeriesLogin");
			 
			 
			 //得到用户code
			 String userCode = authRequest.getName();
			 Element element = cache.get(userCode);
			 if(null != element) {
				 int numStr = (Integer)element.getObjectValue();
				 numStr++;
				 cache.put(new Element(userCode, numStr));
				 response.setHeader("login-num", String.valueOf(numStr));
				 if(numStr == MAX_VISIT_NUM) {
					 //锁账号，刷新缓存
					 sysUsersRepositoryDao.updateUserLockedState(false, userCode);
					 cache.remove(userCode);
				 } 
			 } else {
				 int numStr = 1;
				 response.setHeader("login-num", String.valueOf(numStr));
				 cache.put(new Element(userCode, numStr));
			 }
			
    	} catch(Exception ex) {
    		Logger.getLogger(this.getClass()).error(ex);
    	}
	}


	public boolean isLoginVali() {
		return loginVali;
	}


	public void setLoginVali(boolean loginVali) {
		this.loginVali = loginVali;
	}
    
}
