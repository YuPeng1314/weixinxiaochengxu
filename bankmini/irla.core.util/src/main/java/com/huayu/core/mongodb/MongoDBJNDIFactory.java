package com.huayu.core.mongodb;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import org.apache.commons.lang3.StringUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

/**
 * 实现jndi配置, ObjectFactory对象
 * @author Administrator
 *
 */
public class MongoDBJNDIFactory implements ObjectFactory {

	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
			throws Exception {
		if(obj == null)  {
			//验证第一个参数
			 throw new NamingException("对象为空");
		}
		
	    /**
	     * 多个host名称
	     */
	    String[] hostArr = null;
	    
	    /**
	     * 存用户名
	     */
	    String username = null;
	    
	    /**
	     * 存密码
	     */
	    String password = null;
	    
	    /**
	     * 存数据库
	     */
	    String database = null;
	    
	    
	    /**
	     * 多个端口号
	     */
	    String[] portArr = null;
	    
	    /**
	     * 是否replic Set,0为单点实例，1为replica set，2为cluster 
	     */
	    int refType = 0;
	    
	    //得到引用对象
	    Reference reference = (Reference) obj;
	    //得到对象枚举类型
	    Enumeration<RefAddr> props = reference.getAll();
	    
	    //取所有的参数进行控制,赋值
	    while(props.hasMoreElements()) {
	    	//得到对象
	    	RefAddr refAddr = (RefAddr) props.nextElement();
	    	//得到类型和值
	    	String propType = refAddr.getType();
	    	String propContent = (String)refAddr.getContent();
	    	if("database".equals(propType)) {
	    		database = propContent;
	    	} else if ("host".equals(propType)) {
	            if(StringUtils.isNotBlank(propContent)) {
	            	hostArr = propContent.split("\\s*,\\s*");
	            }
	        } else if ("username".equals(propType)) {
	            username = propContent;
	        } else if (("password").equals(propType)) {
	            password = propContent;
	        } else if ("port".equals(propType)) {
	            if(StringUtils.isNotBlank(propContent)) {
	            	portArr = propContent.split("\\s*,\\s*");
	            }
	        } else if (("refType").equals(propType)) {
	        	try {
	        		refType = Integer.parseInt(propContent);
	        	}catch(NumberFormatException ex) {
	        		
	        	}
	        } 
	    }
	    
	    if (hostArr==null || portArr==null || hostArr.length!=portArr.length) {
	        throw new NamingException("mongodb的host和端口配置有误");
	    }
	    for(int i=0; i<portArr.length; i++) {
	    	try {
        		Integer.parseInt(portArr[i]);
        	}catch(NumberFormatException ex) {
        		throw new NamingException("端口必须为数字");
        	}
	    }
	    
		//mongodb对象
		MongoClient mongoClient = null;
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
	
		builder.connectionsPerHost(300);
		builder.connectTimeout(10000);
		builder.maxWaitTime(120000); 
		builder.socketTimeout(0);
		builder.threadsAllowedToBlockForConnectionMultiplier(6000);
		
		//设置mongodb读在备份节点，读写分离
		MongoClientOptions options = null;
		if(refType == 0) {
			options = builder.build();
		} else if(refType == 1) {
			options = builder.readPreference(ReadPreference.secondaryPreferred()).build();
		}
		 //host配置的个数,多个逗号隔开
		 //两台服务器, replica set
		 List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
		 for(int i=0; i<hostArr.length; i++) {
			 ServerAddress no1 = new ServerAddress(hostArr[i], Integer.parseInt(portArr[i]));
			 serverAddresses.add(no1);
		 }
         
		
		if(StringUtils.isNotBlank(username) || StringUtils.isNotBlank(password)) {
		    List<MongoCredential> credentialList = new ArrayList<MongoCredential>();  
		    //认证
		    MongoCredential cre = MongoCredential.createCredential(username, database, password.toCharArray());
		    credentialList.add(cre);
		    
			mongoClient= new MongoClient(serverAddresses, credentialList, options);
		} else {
			mongoClient= new MongoClient(serverAddresses,  options);
		}
	    return mongoClient;
	}
}
