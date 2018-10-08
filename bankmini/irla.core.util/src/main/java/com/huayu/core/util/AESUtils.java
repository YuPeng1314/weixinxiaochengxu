package com.huayu.core.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import org.apache.commons.codec.binary.Base64;

 
/**
 * 128位AES加解密算法 
 * @author Administrator
 *
 */
public class AESUtils {  
  
	private static Logger logger = Logger.getLogger(AESUtils.class);
	
	
    private  static final String AES="AES";  
    private  static final String UTF8="UTF-8";  
    
    private static final String IV_STRING = "yB4WyK8nZWSzhwla";
    
    static KeyGenerator kgen =null;  
    static{  
        try {  
            kgen= KeyGenerator.getInstance(AES);  
        } catch (NoSuchAlgorithmException e) {  
        	logger.error(e);
        }  
    }  
    
    /**
     * 加密
     * @param content
     * @param password
     * @return
     */
    public static String encrypt(String content, String password) {  
        try {  
            // 使用128 位  
        	password = DigestUtils.md5Hex(password.getBytes("UTF-8")).substring(5, 21);
        	byte[] keyByte = password.getBytes(); 
            SecretKeySpec key = new SecretKeySpec(keyByte, AES); 
            
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

             // 指定加密的算法、工作模式和填充方式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);

            // 正式执行加密操作  
            byte[] result = cipher.doFinal(content.getBytes(UTF8));  
            
            return Base64.encodeBase64String(result);
        } catch (NoSuchAlgorithmException e) {  
        	logger.error(e);  
        } catch (NoSuchPaddingException e) {  
        	logger.error(e); 
        } catch (InvalidKeyException e) {  
        	logger.error(e);  
        } catch (InvalidAlgorithmParameterException e) {  
        	logger.error(e);   
        } catch (IllegalBlockSizeException e) {  
        	logger.error(e);   
        } catch (BadPaddingException e) {  
        	logger.error(e);   
        }  catch (UnsupportedEncodingException e) {  
        	logger.error(e);   
        }  
        return null;  
    }  
  
    /**
     * 解密
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password) {  
        try {
        	password = DigestUtils.md5Hex(password.getBytes("UTF-8")).substring(5, 21);
        	byte[] keyByte = password.getBytes();  
        	
            SecretKeySpec key = new SecretKeySpec(keyByte, AES);  
            // Cipher对象实际完成加密操作  
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

             // 指定加密的算法、工作模式和填充方式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            
            // 正式执行解密操作  
            byte[] contentByte = Base64.decodeBase64(content);
            byte[] result = cipher.doFinal(contentByte);  
            return new String(result, UTF8); 
        } catch (NoSuchAlgorithmException e) {  
        	logger.error(e);  
        } catch (NoSuchPaddingException e) {  
        	logger.error(e);   
        } catch (UnsupportedEncodingException e) {  
        	logger.error(e);   
        } catch (InvalidKeyException e) {  
        	logger.error(e);   
        } catch (IllegalBlockSizeException e) {  
        	logger.error(e);   
        } catch (BadPaddingException e) {  
        	logger.error(e);   
        }  catch (InvalidAlgorithmParameterException e) {  
        	logger.error(e);   
        } 
  
        return null;  
    }  
}