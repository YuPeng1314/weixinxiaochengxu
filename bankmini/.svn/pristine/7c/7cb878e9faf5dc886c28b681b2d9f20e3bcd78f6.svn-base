package com.huayu.ietl.remote.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import net.sf.ehcache.Element;

/**
 * 两级缓存，一级:ehcache,二级为redisCache
 *
 */
public class EhRedisCache implements Cache{
    private String name;

    private CacheManager cacheManager;

    private RedisTemplate<String, Object> redisTemplate;

    private long liveTime = 1*60*60; //默认1h=1*60*60

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    public ValueWrapper get(Object key) {
         Element value = cacheManager.getCache(this.getName()).get(key);
         if (value!=null) {
             return (value != null ? new SimpleValueWrapper(value.getObjectValue()) : null);
         } 
         final String keyStr = getName()+":" + key.toString();  
         
         //切换数据库为1
         JedisConnectionFactory factory = (JedisConnectionFactory)redisTemplate.getConnectionFactory();
		 factory.setDatabase(1);
         Object objectValue = redisTemplate.execute(new RedisCallback<Object>() {  
            public Object doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                byte[] key = keyStr.getBytes();  
                byte[] value = connection.get(key);  
                if (value == null) {  
                    return null;  
                }  
                //每次获得，重置缓存过期时间
                if (liveTime > 0) {  
                    connection.expire(key, liveTime);  
                }  
                return toObject(value);  
            }  
        },true);  
         cacheManager.getCache(this.getName()).put(new Element(key, objectValue));//取出来之后缓存到本地
         return  (objectValue != null ? new SimpleValueWrapper(objectValue) : null);

    }

    @Override
    public void put(Object key, Object value) {
    	cacheManager.getCache(this.getName()).put(new Element(key, value));
        final String keyStr =  getName()+":"+key.toString(); 
        final Object valueStr = value;  
        
        //切换数据库为1
        JedisConnectionFactory factory = (JedisConnectionFactory)redisTemplate.getConnectionFactory();
		factory.setDatabase(1);
        redisTemplate.execute(new RedisCallback<Long>() {  
            public Long doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                byte[] keyb = keyStr.getBytes();  
                byte[] valueb = toByteArray(valueStr);  
                connection.set(keyb, valueb);  
                if (liveTime > 0) {  
                    connection.expire(keyb, liveTime);  
                }  
                return 1L;  
            }  
        },true);  

    }

    @Override
    public void evict(Object key) {
    	cacheManager.getCache(this.getName()).remove(key);
        final String keyStr =  getName()+":"+key.toString();
        //切换数据库为1
        JedisConnectionFactory factory = (JedisConnectionFactory)redisTemplate.getConnectionFactory();
		factory.setDatabase(1);
        redisTemplate.execute(new RedisCallback<Long>() {  
            public Long doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                return connection.del(keyStr.getBytes());  
            }  
        },true); 
    }

    @Override
    public void clear() {
    	cacheManager.getCache(this.getName()).removeAll();
    	//切换数据库为1
    	JedisConnectionFactory factory = (JedisConnectionFactory)redisTemplate.getConnectionFactory();
		factory.setDatabase(1);
        redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
            	connection.flushDb(); 
                return "clear done.";  
            }  
        },true);
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public long getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(long liveTime) {
        this.liveTime = liveTime;
    }

    public void setName(String name) {
        this.name = name;
    }
    /** 
     * 描述 : Object转byte[]. <br> 
     * @param obj 
     * @return 
     */  
    private byte[] toByteArray(Object obj) {  
        byte[] bytes = null;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        try {  
            ObjectOutputStream oos = new ObjectOutputStream(bos);  
            oos.writeObject(obj);  
            oos.flush();  
            bytes = bos.toByteArray();  
            oos.close();  
            bos.close();  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
        return bytes;  
    }  

    /** 
     * 描述 :  byte[]转Object . <br> 
     * @param bytes 
     * @return 
     */  
    private Object toObject(byte[] bytes) {  
        Object obj = null;  
        try {  
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);  
            ObjectInputStream ois = new ObjectInputStream(bis);  
            obj = ois.readObject();  
            ois.close();  
            bis.close();  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        } catch (ClassNotFoundException ex) {  
            ex.printStackTrace();  
        }  
        return obj;  
    }

    
	@Override
	public <T> T get(Object key, Class<T> type) {
		 final String keyf = (String) key;  
	        Object object = null;  
	        object = redisTemplate.execute(new RedisCallback<Object>() {  
	            public Object doInRedis(RedisConnection connection) throws DataAccessException {  
	  
	                byte[] key = keyf.getBytes();  
	                byte[] value = connection.get(key);  
	                if (value == null) {  
	                    return null;  
	                }  
	                return toObject(value);  
	            }  
	        });  
	        return (T) object;  
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		 put(key, value);  
	     return new SimpleValueWrapper(value);  
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		// TODO Auto-generated method stub
		return null;
	}
	
    public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}


}
