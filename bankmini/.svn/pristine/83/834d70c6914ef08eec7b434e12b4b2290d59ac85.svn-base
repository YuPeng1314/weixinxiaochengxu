package com.huayu.core.util;

import org.apache.commons.lang.StringUtils;

/** 
* @author 作者 fzh: 
* @version 创建时间：2016年11月25日 上午11:05:31 
* 类说明 
*/
public class SafeUsernameUtil {

    /*****手机号码验证****/
    public static final String REGULAR_MOBLIE = "^(1\\d{10}$)";
    /******邮箱验证******/
    public static final String REGULAR_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

    /**
     * 补***处理
     *            原字符串
     * @param leastlength
     *            截取前几个
     * @return
     */
    public static String leastSting(String string, int leastlength) {
        if (string.length() >= leastlength)
            return string.substring(0, leastlength) + "***";
        else
            return string.substring(0, string.length()) + "***";
    }

    /**
     * 隐藏手机 规则 显示 第一位和最后一位中间用*替换。
     * 
     * @param mobile(137****1208)
     * @author fzh
     * @return 2016年11月25日 上午11:05:31
     */
    public static String safeMobile(String validMobile) {
        if (StringUtils.isBlank(validMobile)) {
            return "";
        }
        if (validMobile.length() > 7) {
            return validMobile.substring(0, 3) + "****"
                    + validMobile.substring(validMobile.length() - 4, validMobile.length());
        }
        return validMobile.substring(0, 1) + "*********"
                + validMobile.substring(validMobile.length() - 1, validMobile.length());

    }

    /**
     * 隐藏真实姓名。 规则，展示最后一位。
     * 
     * @param safeRealName
     * @return 2016年11月25日 上午11:05:31
     */
    public static String safeRealName(String realName) {
        if (StringUtils.isBlank(realName)) {
            return "";
        }
        return "***" + realName.substring(realName.length() - 1, realName.length());

    }

}
