/**
 * @Title: UserMesUtil.java
 * @Package com.huayu.irla.privilege.manage.util
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author stef-shiqing
 * @date 2016年10月21日 下午4:17:47
 * @version V1.0
 */

package com.huayu.irla.privilege.manage.common;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.huayu.irla.privilege.manage.dao.ISysUsersRepositoryDao;
import com.huayu.irla.privilege.manage.vo.SysRolesVO;
import com.huayu.irla.privilege.manage.vo.SysUsersVO;

/**
  * @ClassName: UserMesUtil
  * @author stef-shiqing
  * @date 2016年10月21日 下午4:17:47
  *
  */
@Component
public class UserMesCommon {

    /**
     * 系统管理员
     */
    private static final int SYSTEM_ADMIN = 1;

    /**
     * 专家导师
     */
	private static final int EXPERT_ADMIN = 2;

/*    *//**
     * 普通用户
     *//*
    private static final int NORMAL_USER = 3;*/
    
    /**
     * 甲方管理员
     */
    private static final int UNIT_ADMIN = 4;
    
    /**
     * 平台管理员
     */
    private static final int PLATFORM_ADMIN = 5;

    /**
     * 用户访问对象
     */
    @Autowired
    private ISysUsersRepositoryDao sysUsersRepositoryDao;

    /**
     * 用户信息 
     */
    private static UserMesCommon userMess;

    @PostConstruct
    public void init() {
        userMess = this;
        userMess.sysUsersRepositoryDao = this.sysUsersRepositoryDao;
    }

    /**
     * 
      * 
      * @Title: getUserName
      * @Description: 得到当前登录Code
      * @param @param request
      * @param @return
      * @return String
      * @throws
     */
    public static String getUserName(HttpServletRequest request) {
        SysUsersVO userVO = getUserInfo(request);
        if (userVO != null) {
            return userVO.getUsername();
        }
        return "";
    }
    
    /**
     * 
      * 
      * @Title: getUserName
      * @Description: 得到当前登录用户名称
      * @param @param request
      * @param @return
      * @return String
      * @throws
     */
    public static String getUserRealName(HttpServletRequest request) {
        SysUsersVO userVO = getUserInfo(request);
        if (userVO != null) {
            return userVO.getName();
        }
        return "";
    }

    /**
     * 
      * getUserInfo(得到用户全部信息)
      * @Title: getUserInfo
      * @param @param request
      * @param @return
      * @return SysUsersVO
      * @throws
     */
    public static SysUsersVO getUserInfo(HttpServletRequest request) {
        Authentication auth = getAuthentication(request);
        if (null != auth) {
            SysUsersVO userVO = (SysUsersVO) auth.getPrincipal();
            return userVO;
        }
        return null;
    }
    
    /**
     * 
      * @Title: getplateCode
      * @Description: 取得平台编码
      * @return String
      * @author liuwei
      * @date 2017年10月17日 下午3:45:10
     */
    
    public static String getplateCode(HttpServletRequest request) {
        SysUsersVO userVO = getUserInfo(request);
        if (userVO != null) {
            return userVO.getPlateformCode();
        }
        return "";
    }
    
    /**
     * 
      * @Title: getplateCode
      * @Description: 取得项目code
      * @return String
      * @author liuwei
      * @date 2017年10月17日 下午3:45:10
     */ 
    public static List<String> getProjectCode(HttpServletRequest request) {
        SysUsersVO userVO = getUserInfo(request);
        if (userVO != null) {
            return userVO.getProjectFlag();
        }
        return null;
    }

    /**
     * 
      * getAuthentication(得到权限信息)
      *
      *
      * @Title: getAuthentication
      * @param @param request
      * @param @return
      * @return Authentication
      * @throws
     */
    public static Authentication getAuthentication(HttpServletRequest request) {
        //权限上下文对象
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                .getAttribute("SPRING_SECURITY_CONTEXT");
        if (null != securityContextImpl) {
            Authentication auth = securityContextImpl.getAuthentication();
            return auth;
        }
        return null;
    }

    /**
     * 
      * 得到web权限详情
      * @Title: getWebAuthenticationDetails
      * @param @param request
      * @param @return
      * @return WebAuthenticationDetails
      * @throws
     */
    public static WebAuthenticationDetails getWebAuthenticationDetails(HttpServletRequest request) {
        Authentication auth = getAuthentication(request);
        if (null != auth) {
            WebAuthenticationDetails webDetail = (WebAuthenticationDetails) auth.getDetails();
            return webDetail;
        }
        return null;
    }

    /**
     * 
      * 得到访问地址
      * @Title: getWebAuthenticationDetails
      * @param @param request
      * @param @return
      * @return WebAuthenticationDetails
      * @throws
     */
    public static String getRemoteAddress(HttpServletRequest request) {
    	return PrivilegeTool.getIpAddress(request);
    }

    /**
     * 
      * getSessionId(得到sessionid)
      * @Title: getSessionId
      * @param @param request
      * @param @return
      * @return String
      * @throws
     */
    public static String getSessionId(HttpServletRequest request) {
        WebAuthenticationDetails webDetail = getWebAuthenticationDetails(request);
        if (null != webDetail) {
            String seesionID = webDetail.getSessionId();
            return seesionID;
        }
        return null;
    }

    /**
     * 
      * getSessionId(得到sessionid)
      * @Title: getSessionId
      * @param @param request
      * @param @return
      * @return String
      * @throws
     */
    public static String getLoginPassword(HttpServletRequest request) {
        Authentication auth = getAuthentication(request);
        if (null != auth) {
            String password = (String) auth.getCredentials();
            return password;
        }
        return null;
    }

    /**
     * 根据用户得打角色信息的列表
     * @return
     */
    public static List<SysRolesVO> getRoleInfo(HttpServletRequest request) {
        //权限上下文对象
        SysUsersVO sysUser = getUserInfo(request);
        if (null != sysUser) {
            List<SysRolesVO> roleInfo = sysUser.getRoleMess();
            return roleInfo;
        }
        return null;
    }

    /**
     * 根据角色名称找是否系统管理员角色
     * @param roleName
     * @return
     */
    public static boolean isSystemAdmin(HttpServletRequest request) {
        List<SysRolesVO> roleInfo = getRoleInfo(request);
        if (null != roleInfo) {
            for (SysRolesVO tmpVO : roleInfo) {
                if (tmpVO.getType() == SYSTEM_ADMIN) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据角色名称找是否甲方管理员角色
     * @param roleName
     * @return
     */
    public static boolean isUnitAdmin(HttpServletRequest request) {
        List<SysRolesVO> roleInfo = getRoleInfo(request);
        if (null != roleInfo) {
            for (SysRolesVO tmpVO : roleInfo) {
                if (tmpVO.getType() == UNIT_ADMIN) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 是否平台管理员
     * @param request
     * @return
     */
    public static boolean isPlatformAdmin(HttpServletRequest request) {
    	 List<SysRolesVO> roleInfo = getRoleInfo(request);
         if (null != roleInfo) {
             for (SysRolesVO tmpVO : roleInfo) {
                 if (tmpVO.getType() == PLATFORM_ADMIN) {
                     return true;
                 }
             }
         }
         return false;
    }

    /**
     * 根据角色名称找是否专家导师角色
     * @param roleName
     * @return
     */
    public static boolean isResourceAdmin(HttpServletRequest request) {
        List<SysRolesVO> roleInfo = getRoleInfo(request);
        if (null != roleInfo) {
            for (SysRolesVO tmpVO : roleInfo) {
                if (tmpVO.getType() == EXPERT_ADMIN) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 得到部门单位管理员
     * @param request
     * @return
     */
    public static boolean isFullProgess(HttpServletRequest request) {
        //权限上下文对象
        SysUsersVO sysUser = getUserInfo(request);
        if (null != sysUser) {
            String userCode = sysUser.getUsername();
            HttpSession httpSession = request.getSession();
            if(null != httpSession) {
            	Boolean isFull = (Boolean)httpSession.getAttribute(userCode+"fullProgress");
            	return isFull;
            }
        }
        return false;
    }
    
    /**
     * 得到用户当前在线人数
     * @return
     */
    public static int getOnlineUsers() {
        return userMess.sysUsersRepositoryDao.getOnlineUsers();
    }

}
