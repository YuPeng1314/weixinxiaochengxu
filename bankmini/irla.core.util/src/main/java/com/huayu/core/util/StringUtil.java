
package com.huayu.core.util;

import java.util.Arrays;

/**        
 * 类名称：StringUtil    
 *     
 */
public class StringUtil {

    /**
     * 
     * isEmpty 判断字符串是否为空
     * @param inValue
     * @return 
     * boolean
     * @exception
     */
    public static boolean isEmpty(String inValue) {

        if (inValue == null) {
            return true;
        } else {
            return (inValue.length() < 1);
        }
    }

    /**
     * 
     * isRealEmpty 判断字符串是否为空
     * @param inValue
     * @return 
     * boolean
     * @exception
     */
    public static boolean isRealEmpty(String inValue) {

        if (!isEmpty(inValue)) {
            return (inValue.trim().length() < 1);
        }
        return true;
    }

    public static String connectURL(String... args) {

        StringBuilder sb = new StringBuilder();
        boolean previous = false;
        for (String arg : args) {
            if (arg == null || ("/".equals(arg.trim()))) {
                continue;
            }
            arg = arg.trim();
            if (previous && arg.startsWith("/")) {
                arg = arg.substring(1);
            }
            if (!previous && !arg.startsWith("/")) {
                sb.append("/");
            }

            sb.append(arg);
            previous = arg.endsWith("/");
        }
        return sb.toString();
    }

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        if (null == fileName || "".equals(fileName)) {
            return "";
        } else {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
    }

    /**
      * @Title: sortCharInString
      * @Description: 字符串中字符的排序。返回一个有序的新字符串 比如：ADCE --->ACDE 
      * @author luozehua
      * @date 2016年12月14日 下午6:39:46
      * @param srcStr
      * @return
     */
    public static String sortCharInString(String srcStr) {
        char[] charArr = srcStr.toCharArray();
        Arrays.sort(charArr);
        return new String(charArr);
    }

}
