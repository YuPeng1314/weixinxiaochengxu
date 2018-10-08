package com.huayu.core.util;

import java.util.UUID;

/**
  * @ClassName: UUIDGenerator
  * @Description: TODO
  * @author ao.chen
  * @date 2016年10月17日 上午10:14:45
  *
 */
public class UUIDGenerator {

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		// 去掉“-”符号
		return uuid.replaceAll("-", "");
	}

}
