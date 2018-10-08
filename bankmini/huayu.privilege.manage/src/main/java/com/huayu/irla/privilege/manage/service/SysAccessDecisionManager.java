package com.huayu.irla.privilege.manage.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.huayu.irla.privilege.manage.vo.SysRolesVO;
import com.huayu.irla.privilege.manage.vo.SysUsersVO;

public class SysAccessDecisionManager implements AccessDecisionManager {

	/**
	 * 系统管理员
	 */
	private static final int  SYSTEM_ADMIN = 1;
	
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		   if(configAttributes == null) {    
	            return;    
	        } 
		   if(authentication instanceof UsernamePasswordAuthenticationToken) {
	 		   SysUsersVO sysVO = (SysUsersVO)authentication.getPrincipal();
			   if(null != sysVO) {
				   List<SysRolesVO> roleInfo = sysVO.getRoleMess();
				   if(null != roleInfo) {
						for(SysRolesVO tmpVO:roleInfo) {
							if(tmpVO.getType() == SYSTEM_ADMIN) {
								return;
							}
						}
					}
			   }
		   }
	        //所请求的资源拥有的权限(一个资源只对应一个权限)    
	        Iterator<ConfigAttribute> iterator = configAttributes.iterator();    
	        while(iterator.hasNext()) {    
	            ConfigAttribute configAttribute = iterator.next();    
	            //访问所请求资源所需要的权限    
	            String needPermission = configAttribute.getAttribute();    
	            //用户所拥有的权限authentication    
	            for(GrantedAuthority ga : authentication.getAuthorities()) {    
	                if(needPermission.equals(ga.getAuthority())) {    
	                    return;    
	                }    
	            }    
	        }    
	        //没有权限    
	        throw new AccessDeniedException("没有权限访问"); 

	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
