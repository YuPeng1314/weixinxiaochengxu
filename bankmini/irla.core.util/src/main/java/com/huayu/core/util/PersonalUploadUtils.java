package com.huayu.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
/**
 * @author fzh
 *
 * @time 2016年10月6日-下午3:53:30
 */
public class PersonalUploadUtils {
	

	private static final Logger logger = Logger.getLogger(PersonalUploadUtils.class);

	private static Properties props = new Properties();

	private static PersonalUploadUtils instance = null;

	private static final String FILENAME = "config/personaImg.properties";

	private PersonalUploadUtils() {

	}

	public static PersonalUploadUtils getInstance() {
		if (instance == null) {
			return new PersonalUploadUtils();
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
