package com.huayu.irla.privilege.manage.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;


@EnableRedisHttpSession(redisNamespace="www.udangyuan.com")
public class RedisHttpSessionConfig {
	
	@Bean
	public DefaultCookieSerializer cookieSerializer() {
		DefaultCookieSerializer defaultObj = new DefaultCookieSerializer();
		//域名共享,一级域名相同都是共享的
		defaultObj.setDomainNamePattern("^.*?\\.?.*?\\.?(\\w+\\.[a-z]+)$");//udangyuan.com
		defaultObj.setCookiePath("/");
		return defaultObj;
	}
}