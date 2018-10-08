package com.huayu.core.mysql;

public class DynamicDataSourceHolder {
	
	private static final ThreadLocal<String> holder = new ThreadLocal<String>();

	public static void putDataSource(String name) {
		holder.set(name);
	}

	public static String getDataSouce() {
		return holder.get();
	}
	
	public static void clearDataSource() {
		holder.remove();
	}
	
}
