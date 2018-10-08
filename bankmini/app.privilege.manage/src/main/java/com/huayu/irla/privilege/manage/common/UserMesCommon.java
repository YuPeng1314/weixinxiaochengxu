/**
 * @Title: UserMesUtil.java
 * @Package com.huayu.irla.privilege.manage.util
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author stef-shiqing
 * @date 2016年10月21日 下午4:17:47
 * @version V1.0
 */

package com.huayu.irla.privilege.manage.common;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.huayu.core.util.EhcacheUtils;
import com.huayu.irla.core.user.vo.MobSysUsersVO;
import com.huayu.irla.privilege.manage.dao.ISysUsersRepositoryDao;

/**
  * @ClassName: UserMesUtil
  * @author stef-shiqing
  * @date 2016年10月21日 下午4:17:47
  *
  */
@Component
public class UserMesCommon {


    /**
     * 用户访问对象
     */
    @Autowired
    private ISysUsersRepositoryDao sysUsersRepositoryDao;
    
	/**
	 * redis对象
	 */
    @Autowired
	private StringRedisTemplate stringRedisTemplate;
    

    /**
     * 用户信息 
     */
    private static UserMesCommon userMess;

    @PostConstruct
    public void init() {
        userMess = this;
        userMess.sysUsersRepositoryDao = this.sysUsersRepositoryDao;
        userMess.stringRedisTemplate = this.stringRedisTemplate;
    }
    
    /**
     * 
      * 
      * @Title: getUserName
      * @Description: 得到当前登录Code
      * @param @param request
      * @param @return
      * @return String
      * @throws
     */
    public static String getUserCode(HttpServletRequest request) {
        MobSysUsersVO userVO = getUserInfo(request);
        if (userVO != null) {
            return userVO.getUserCode();
        }
        return null;
    }
    

    /**
     * 
      * getUserInfo(得到用户全部信息)
      * @Title: getUserInfo
      * @param @param request
      * @param @return
      * @return SysUsersVO
      * @throws
     */
    public static MobSysUsersVO getUserInfo(HttpServletRequest request) {
    	return getAuthorizeInfo(request);
    }
    
    /**
     * 得到权限信息
     * @param request
     * @return
     */
    public static MobSysUsersVO getAuthorizeInfo(HttpServletRequest request) {
    	String sessionId = (String)request.getAttribute("sessionId");
    	if(StringUtils.isNotBlank(sessionId)) {
	    	String userCode = userMess.stringRedisTemplate.opsForValue().get(sessionId);
	    	if(StringUtils.isNotBlank(userCode)) {
	    		//从缓存中取值，如果没有取到就从数据库中读取
	            EhcacheUtils ehcacheUtils = EhcacheUtils.getInstance(new String[]{"mobileManager"});
	    		Object userEh = ehcacheUtils.get("userInfoEhCache", userCode);
	    		if(userEh == null) {
		    		MobSysUsersVO userVO = userMess.sysUsersRepositoryDao.getByUserCode(userCode);
		            
		            //放入缓存中
		            ehcacheUtils.put("userInfoEhCache", userCode, userVO);
		    		return userVO;
	    		} else {
	    			return (MobSysUsersVO)userEh;
	    		}
	    		
	    	}
    	}
    	return null;
    }
    
   
    /**
     * 
      * 得到访问地址
      * @Title: getWebAuthenticationDetails
      * @param @param request
      * @param @return
      * @return WebAuthenticationDetails
      * @throws
     */
    public static String getRemoteAddress(HttpServletRequest request) {
       return PrivilegeTool.getIpAddress(request);
    }

    /**
     * 更新用户信息
     * @param request
     */
    public static void updateUserInfo(HttpServletRequest request) {
	     String userCode = getUserCode(request);
	     if(StringUtils.isNotBlank(userCode)) {
			 //清理缓存
			 EhcacheUtils ehcacheUtils = EhcacheUtils.getInstance(new String[]{"mobileManager"});
			 ehcacheUtils.remove("userInfoEhCache", userCode); 
	     }
    }
}
