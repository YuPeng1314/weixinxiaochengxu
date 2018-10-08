package com.huayu.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author luozehua
 *
 * @time 2016年9月6日-下午3:53:30
 */
public class PropertiesUtils {

	private static final Logger logger = Logger.getLogger(PropertiesUtils.class);

	private static Properties props = new Properties();

	private static PropertiesUtils instance = null;

	private static final String FILENAME = "config/propConfig.properties";

	private PropertiesUtils() {

	}

	public static PropertiesUtils getInstance() {
		if (instance == null) {
			return new PropertiesUtils();
		}
		return instance;
	}

	/**
	 * 根据键获取值
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public String getValue(String key, String defValue) {
		String value = "";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILENAME);
		if (in == null) {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILENAME);
		}
		try {
			props.load(in);
			value = props.getProperty(key);
		} catch (IOException e) {
			logger.error("获取值失败", e);
		}
		return StringUtils.isNoneBlank(value) ? value : defValue;
	}

}
