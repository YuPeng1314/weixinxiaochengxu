package com.huayu.irla.mobile.reslog;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.huayu.irla.privilege.manage.common.UserMesCommon;


/**
 * 访问历史日志记录
 * @author Administrator
 *
 */
@Aspect //该注解标示该类为切面类
@Component
public class ResVisitLogRecord {
	

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
   
    /**
     * 增加搜索词
     * @param jp
     */
    @Before("execution(* com.huayu.irla.mobile.category.CategoryAction.courseShow(..))")     
    public void addSearchWord(JoinPoint jp){ 
    	Object[] arg = jp.getArgs();
    	HttpServletRequest request = (HttpServletRequest)arg[0];
    	String searchContent = request.getParameter("searchkey");
    	String userCode = UserMesCommon.getUserCode(request);
    	if(StringUtils.isNotBlank(userCode) && StringUtils.isNotBlank(searchContent)) {
    		 String key = "recent_search_" + userCode;
    	     stringRedisTemplate.opsForZSet().add(key, searchContent, System.currentTimeMillis());
    	}
   } 
}
