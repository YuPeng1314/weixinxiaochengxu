/**
 * @Title: RandomUtil.java
 * @Package com.huayu.core.util
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年12月15日 下午2:40:51
 * @version V1.0
 */

package com.huayu.core.util;

/**
  * @ClassName: RandomUtil
  * @Description: TODO
  * @author luozehua
  * @date 2016年12月15日 下午2:40:51
  *
  */

public class RandomUtil {
    private RandomUtil() {

    }

    /**
      * @Title: 
      * @Description: 随机生成min到max之间的不重复的n个随机数
      * @author luozehua
      * @date 2016年12月15日 下午2:41:24
      * @param min
      * @param max
      * @param n
     */
    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }
}
