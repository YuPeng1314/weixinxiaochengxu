package com.huayu.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreateMD5 {

    //静态方法，便于作为工具类  
    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            //32位加密  
            return buf.toString();
            // 16位的加密  
            //return buf.toString().substring(8, 24);  
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    /**
	 * hash算法分表
	 * @param str
	 * @param tableNum
	 * @return
	 */
	public static int getHashCodeInt(String str, int tableNum) {  
        // 最终计算得出的哈希值，转化为int以后的哈希值  
        int hashcode = 0;  
        // 临时哈希值变量  
        int hash = 0;  
        if (hash == 0) {  
            // 当前char的索引  
            int off = 0;  
            // 字符串str的字符数组表示  
            char val[] = str.toCharArray();  
            // 字符串str的长度  
            int len = str.length();  
            for (int i = 0; i < len; i++) {  
                hash = 31 * hash + val[off++];  
            }  
            hashcode = hash;  
        }  
        return Math.abs(hashcode)%tableNum;  
    }  
}
