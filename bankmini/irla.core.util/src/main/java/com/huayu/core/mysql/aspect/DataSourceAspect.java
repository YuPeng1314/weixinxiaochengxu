package com.huayu.core.mysql.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.huayu.core.mysql.DynamicDataSourceHolder;


public class DataSourceAspect {
	
	public void before(JoinPoint point) {
		
		Object target = point.getTarget();
		String method = point.getSignature().getName();

		Class<?>[] classz = target.getClass().getInterfaces();

		Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
		try {
			Method m = classz[0].getMethod(method, parameterTypes);
			if (m != null && m.isAnnotationPresent(DataSource.class)) {
				DataSource data = m.getAnnotation(DataSource.class);
				DynamicDataSourceHolder.putDataSource(data.value());
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void invokeAfter(JoinPoint point) {
		//清除数据源
		DynamicDataSourceHolder.clearDataSource();
	}

}