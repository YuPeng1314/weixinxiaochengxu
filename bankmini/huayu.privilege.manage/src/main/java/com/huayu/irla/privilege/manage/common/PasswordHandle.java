/**
 * @Title: ResetPassword.java
 * @Package com.huayu.irla.privilege.manage.password
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author stef-shiqing
 * @date 2016年10月21日 下午4:05:57
 * @version V1.0
 */

package com.huayu.irla.privilege.manage.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.huayu.irla.privilege.manage.dao.ISysUsersRepositoryDao;
import com.huayu.irla.privilege.manage.vo.SysUsersVO;

/**
  * @ClassName: ResetPassword
  * @Description: 密码重置和初始化
  * @author stef-shiqing
  * @date 2016年10月21日 下午4:05:57
  *
  */

@Named("passwordHandle")
public class PasswordHandle {
	
	/**
	 * 
	 */
	private int iterations = 1;
	
    @Autowired  
    private UserCache userCache;  
    
    @Autowired  
    private  ISysUsersRepositoryDao sysUsersRepositoryDao;  

    /**
     * 用户自定义的密码
     */
    public static final int USER_PASSWORD_TYPE = 0;
    
    /**
     * 初始化密码
     */
    public static final int INIT_PASSWORD_TYPE = 1;
    
    /**
     * 
      * restPassword(初始化密码)
      *
      * @Title: restPassword
      * @param @param userName
      * @param @param initPassword
      * @return void
      * @throws
     */
    public void initPassword(String userName, String passwordStr, int passType) {
    	 //密码进行MD5加密后
        String salt = getSalt();
        String secretStr = encodePassword(passwordStr, salt);
	
        //更新密码
        sysUsersRepositoryDao.updatePassword(userName, secretStr, passType);
    }
    
    /**
     * 
      * restPassword(初始化重置密码)
      *
      * @Title: restPassword
      * @param @param userName
      * @param @param initPassword
      * @return void
      * @throws
     */
    public void initPassword(String userName, String passwordStr) {
    	 //密码进行MD5加密后
        String salt = getSalt();
        String secretStr = encodePassword(passwordStr, salt);
        	
        //更新密码
        sysUsersRepositoryDao.updatePassword(userName, secretStr, INIT_PASSWORD_TYPE);
    }
    
    /**
     * 
      * updatePassword(修改密码手机端调用)
      *
      * @Title: updatePassword
      * @param @param userName
      * @param @param initPassword
      * @return void
      * @throws
     */
    public void updatePassword(String userName, String passwordStr) {
    	 //密码进行MD5加密后
        String salt = getSalt();
        String secretStr = encodePassword(passwordStr, salt);
        	
        //更新密码
        sysUsersRepositoryDao.updatePassword(userName, secretStr, USER_PASSWORD_TYPE);
    }
    
    
    
    /**
     * 
      * getMD5Password(初始化密码)
      *
      * @Title: restPassword
      * @param @param initPassword
      * @return void
      * @throws
     */
    public String getMD5Password(String passwordStr) {
    	 //密码进行MD5加密后
        String salt = getSalt();
        String secretStr = encodePassword(passwordStr, salt);
        return secretStr;
    }
    
	/**
	 * 
	  * changePassword(修改用户密码)
	  *
	  * @Title: changePassword
	  * @param @param oldPassword
	  * @param @param newPassword
	  * @return void
	  * @throws
	 */
    @Transactional
	public void changePassword(HttpServletRequest request, String oldPassword, String newPassword) throws AccessDeniedException {
		//得到当前用户名
		//判断输入的旧密码是否正确
		String password = UserMesCommon.getLoginPassword(request);
		if(!password.equals(oldPassword)) {
			 throw new AccessDeniedException("旧密码不正确"); 
		}
		
		//封装新密码并更新到数据库中
		changePassword(request, newPassword);
	}
	
	  
    private void changePassword(HttpServletRequest request, String newPassword)  
    {   //得到当前用户token
        Authentication currentuser=UserMesCommon.getAuthentication(request); 
        
        //得到用户名
        String userName = UserMesCommon.getUserName(request);
        SysUsersVO sysUserVO = UserMesCommon.getUserInfo(request);
        sysUserVO.setPassword(newPassword);
        
        //密码进行MD5加密后
        String salt = getSalt();
        String secretStr = encodePassword(newPassword, salt);
        
        //更新密码
        sysUsersRepositoryDao.updatePassword(userName, secretStr, USER_PASSWORD_TYPE);
          
        if(currentuser==null)  
        {  
            // This would indicate bad coding somewhere  
            throw new AccessDeniedException("Can't change password as no Authentication object found in context " +  
                    "for current user.");  
        }  
          
          
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(sysUserVO, newPassword, currentuser.getAuthorities());  
        newAuthentication.setDetails(currentuser.getDetails());  
        
        SecurityContextImpl securityContextImpl = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if(null != securityContextImpl) {  
			 SecurityContextHolder.getContext().setAuthentication(newAuthentication);
		}
        
		//把当前用户的权限信息从缓存中去除
        userCache.removeUserFromCache(userName);  
    }  
       
    /**
	 * Encodes the rawPass using a MessageDigest. If a salt is specified it will be merged
	 * with the password before encoding.
	 *
	 * @param rawPass The plain text password
	 * @param salt The salt to sprinkle
	 * @return Hex string of password digest (or base64 encoded string if
	 * encodeHashAsBase64 is enabled.
	 */
	private String encodePassword(String rawPass, Object salt) {
		String saltedPass = mergePasswordAndSalt(rawPass, salt, false);

		MessageDigest messageDigest = getMessageDigest();

		byte[] digest = messageDigest.digest(Utf8.encode(saltedPass));

		// "stretch" the encoded value if configured to do so
		for (int i = 1; i < this.iterations; i++) {
			digest = messageDigest.digest(digest);
		}

		if (getEncodeHashAsBase64()) {
			return Utf8.decode(Base64.encode(digest));
		} else {
			return new String(Hex.encode(digest));
		}
		
	}
	
	private String mergePasswordAndSalt(String password, Object salt, boolean strict) {
		if (password == null) {
			password = "";
		} 
			
		return password + "{" + salt.toString() + "}";
	}
	
	protected MessageDigest getMessageDigest() throws IllegalArgumentException {
		try {
			return MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(
					"No such algorithm [MD5]");
		}
	}
	
	/**
	 * 
	  * getEncodeHashAsBase64(哈希值做为base64）
	  *
	  * @Title: getEncodeHashAsBase64
	  * @param @return
	  * @return boolean
	  * @throws
	 */
	private boolean getEncodeHashAsBase64(){
		return true;
	}
	
	/**
	 * 
	  * setIterations(设置循环)
	  *
	  * @Title: setIterations
	  * @param @param iterations
	  * @return void
	  * @throws
	 */
	public void setIterations(int iterations) {
		Assert.isTrue(iterations > 0, "Iterations value must be greater than zero");
		this.iterations = iterations;
	}
	
	/**
	 * 
	  * getSalt(定义salt)
	  *
	  * @Title: getSalt
	  * @param @return
	  * @return String
	  * @throws
	 */
	public String getSalt() {
		//和applicationContext-security.xml保持一致
		return "saltZX";
	}
}
