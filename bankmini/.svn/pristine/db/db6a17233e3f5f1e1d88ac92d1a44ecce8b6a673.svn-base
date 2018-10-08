package com.huayu.core.mongodb;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@Configuration
@Qualifier("customMongoClient")
public class CustomMongoClient {
	 public @Bean MongoClient mongoTemplate() throws Exception {
		 //得到上下文
	     Context initCtx = new InitialContext();
	     Context envCtx = (Context) initCtx.lookup("java:comp/env");
	     return (MongoClient) envCtx.lookup("jndi/ietlMongBean");
    }
}
