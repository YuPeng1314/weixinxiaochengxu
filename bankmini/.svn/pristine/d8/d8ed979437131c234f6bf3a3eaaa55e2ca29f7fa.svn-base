package com.huayu.irla.privilege.manage.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.huayu.irla.privilege.manage.dao.ISysUsersRepositoryDao;
import com.huayu.irla.privilege.manage.vo.SysAuthoritiesVO;
import com.huayu.irla.privilege.manage.vo.SysRolesVO;
import com.huayu.irla.privilege.manage.vo.SysUsersVO;

public class DefaultUserDetailsService implements UserDetailsService {

		protected final Log logger = LogFactory.getLog(getClass());  
		
		public static final int MAX_WAIT_TIME = 30;
	      
	    @Autowired  
	    private  ISysUsersRepositoryDao sysUsersRepositoryDao;  
	      
	    @Autowired  
	    private MessageSource messageSource;  
	      
	    @Autowired  
	    private UserCache userCache;  
	    
	    /**
	     * 权限类型:前端\后台\手机端
	     */
	    private String[] ausTypes;
	   
		@Override  
	    public UserDetails loadUserByUsername(String username)  
	            throws UsernameNotFoundException {  
	          
	        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();  
	        //从缓存中读取值
	        SysUsersVO user = (SysUsersVO) this.userCache.getUserFromCache(username);  
	          
	        if(user == null){  
	            user = this.sysUsersRepositoryDao.getByUsername(username);  
	            if(user == null)  {
	                throw new UsernameNotFoundException(this.messageSource.getMessage("UserDetailsService.userNotFount", new Object[]{username}, null));
	            }
	            
	            //如果被锁，且已经超过30分钟，则解锁
	            if(!user.isAccountNonLocked()) {
	            	if(user.getGapMiu() > MAX_WAIT_TIME) {
	            		 this.sysUsersRepositoryDao.updateUserLockedState(true, username);
	            		 user.setAccountNonLocked(true);
	            	}
	            }
	            //得到用户角色信息
	            List<SysRolesVO> roleList = this.sysUsersRepositoryDao.getRoleInfo(username);
	            user.setRoleMess(roleList);
	            
	            //得到用户的权限信息
	            auths = this.loadUserAuthorities(username); 
	            user.setAuthorities(auths);  
	        }  
	          
	        logger.info("*********************"+username+"***********************");  
	        logger.info(user.getAuthorities());  
	        logger.info("********************************************************");  
	          
	        //加载缓存
	        this.userCache.putUserInCache(user);  
	          
	        return user;  
	    }  
	    
	    private Collection<GrantedAuthority> loadUserAuthorities(String username) {
	    	 List<SysAuthoritiesVO> list = this.sysUsersRepositoryDao.getSysAuthoritiesByUsername(username, getAusTypes());  
	          
	         List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();  
	           
	         for (SysAuthoritiesVO authority : list) {  
	             //权限标识
	        	 GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthorityMark());  
	             auths.add(grantedAuthority);  
	         }  
	   
	         return auths;  
	    }

		public SysUsersVO getByUsername(String userName) {
			return sysUsersRepositoryDao.getByUsername(userName);
		}
		
		
		public List<SysRolesVO> getRoleInfo(String userName) {
			return sysUsersRepositoryDao.getRoleInfo(userName);
		}
		
		   
	    public String[] getAusTypes() {
			return ausTypes;
		}

		public void setAusTypes(String[] ausTypes) {
			this.ausTypes = ausTypes;
		}

	}


