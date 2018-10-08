package com.huayu.irla.manage.application.role.impl;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.core.service.role.IUserManageService;
import com.huayu.irla.core.service.roleManage.IRoleManageService;
import com.huayu.irla.core.user.vo.RoleInfoVO;
import com.huayu.irla.core.user.vo.UserInfoVO;
import com.huayu.irla.manage.application.role.IUserManage;
import com.huayu.irla.manage.util.UserUtils;
import com.huayu.irla.privilege.manage.common.PasswordHandle;
import com.huayu.irla.privilege.manage.common.UserMesCommon;

/**
 * 
 * @ClassName: UserManageImpl
 * @Description: 用户管理action层的实现
 * @author liuwei
 * @date 2016年10月26日 上午10:57:34
 *
 */
@Component("userManage")
public class UserManageImpl implements IUserManage {
	

    //清理缓存
    @Autowired
    private UserCache userCache;

    @Autowired
    private IUserManageService userManageServiceImpl;

    @Autowired
    private PasswordHandle passwordHandle;
 
    @Autowired
    private IRoleManageService roleManageServiceImpl;
    
    
    @Override
    public JSONObject findUserList(UserInfoVO userInfoVO) {
        List<UserInfoVO> userlist = Collections.emptyList();
        JSONObject result = new JSONObject();
        if (userInfoVO == null) {
            userInfoVO = new UserInfoVO();
        }
        //加入权限控制
       /* Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        //甲方管理员
        if (UserMesCommon.isUnitAdmin(request)) {
        	String plateCode = UserMesCommon.getplateCode(request);
        	userInfoVO.setPlateformCode(plateCode);
        	result.put("plateformType", plateCode);
        }else if (UserMesCommon.isSystemAdmin(request)){ //系统管理员
        	result.put("plateformType", "1");
        }else {
        	userInfoVO.setPlateformCode("PL");
        	result.put("plateformType", "2");  //平台管理员
        }*/
        userlist = userManageServiceImpl.findUser(userInfoVO);
        // 取得所有数据记录总数
        Integer count = userManageServiceImpl.getCount(userInfoVO);
        result.put("rows", JSONArray.toJSON(userlist));
        result.put("total", count);
        return result;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(UserInfoVO userInfoVO) {
        String userName = UserUtils.getLoginName();
        if (userInfoVO != null) {
        	/*if(StringUtils.isBlank(userInfoVO.getPlateformCode())) {
        		Message message = PhaseInterceptorChain.getCurrentMessage();
                HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
                String plateCode = UserMesCommon.getplateCode(request);
                userInfoVO.setPlateformCode(plateCode);
        	}*/
            // 判断ID是否为空，若为空，则进入增加方法，若不为空，则进入修改方法
            if (userInfoVO.getId() == null) {
                userInfoVO.setCreatedBy(userName);
                //将用户信息插入用户信息表中
                userManageServiceImpl.addUser(userInfoVO);
              

                String passtemp = DataDricCommon.getValueByKey("password");
                
                String passTool = passwordHandle.getMD5Password(passtemp);
              
                //插入用户权限表中
                userInfoVO.setPassword(passTool);
                userManageServiceImpl.addSysUser(userInfoVO);
                //初始化用户角色
                designRole(userInfoVO,userName);
                return true;
            } else {
                userInfoVO.setLastUpdatedBy(userName);
                UserInfoVO tempVO = new UserInfoVO();
                //通过id查询
                tempVO.setId(userInfoVO.getId());
                //取得数据
                List<UserInfoVO> daptlist = userManageServiceImpl.findUser(tempVO);
                UserInfoVO userVO = null;
                if (CollectionUtils.isNotEmpty(daptlist)) {
                    userVO = daptlist.get(0);
                    if(!StringUtils.equals(userInfoVO.getUserCode(), userVO.getUserCode())) {
                    	//加入权限控制
                        Message message = PhaseInterceptorChain.getCurrentMessage();
                        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
                        //判断是否为系统管理员
                        if (UserMesCommon.isSystemAdmin(request)) {
                        	userManageServiceImpl.updateSysUser(userInfoVO);
                        } else {
                        	userInfoVO.setUserCode(userVO.getUserCode());
                        }	
                    }
                    userManageServiceImpl.updateUser(userInfoVO);
        			userCache.removeUserFromCache(userInfoVO.getUserCode());
        			return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 
      * @Title: designRole
      * @Description: 用户角色初始化
      * @return void
      * @author liuwei
      * @date 2017年10月18日 下午2:18:35
     */
    
	public void designRole(UserInfoVO userInfoVO,String userName) {
		RoleInfoVO role = new RoleInfoVO();
        role.setUserCode(userInfoVO.getUserCode());
        //通过UserCode查询到UserId
        RoleInfoVO tempVO = roleManageServiceImpl.findUserID(role);
        role.setUserId(tempVO.getUserId());
        role.setCreatedBy(userName);
        role.setRoleId(5);  //为普通用户
        roleManageServiceImpl.addRoleUser(role);
       /* if(StringUtils.isBlank(userInfoVO.getDepartmentCode())) {
        	if(StringUtils.equals(userInfoVO.getPlateformCode(), "PL")) {
        		role.setRoleId(8);  //为平台管理员
        	}else {
        		role.setRoleId(7);  //为甲方管理员
        	}
        }else {
        	if(StringUtils.isNotBlank(userInfoVO.getCertificationAuthority())) {
        		role.setRoleId(6);  //为专家导师
        	}else {
        		role.setRoleId(5);  //为普通用户
        	}
        }*/
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoVO updateUser(Integer id) {
        if (id != null) {

            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setId(id);

            //取得数据
            List<UserInfoVO> daptlist = userManageServiceImpl.findUser(userInfoVO);
            UserInfoVO infovo = null;
            if (CollectionUtils.isNotEmpty(daptlist)) {
                infovo = daptlist.get(0);
                //加入权限控制
               /* Message message = PhaseInterceptorChain.getCurrentMessage();
                HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
                //判断是否为系统管理员
                if (UserMesCommon.isSystemAdmin(request)) {
                    infovo.setUserType("1");
                }
                //判断是否为单位管理员
                else {
                    infovo.setUserType("2");
                }*/
                return infovo;
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(Integer id) {
        if (id != null) {
            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setId(id);
            //取得数据
            List<UserInfoVO> daptlist = userManageServiceImpl.findUser(userInfoVO);
            UserInfoVO tempVO = null;
            if (CollectionUtils.isNotEmpty(daptlist)) {
                tempVO = daptlist.get(0);

                // 删除用户基本信息表当前ID信息
                userManageServiceImpl.deleteUser(id);
                // 删除用户当前ID信息
                userManageServiceImpl.deleteSysUser(tempVO.getUserCode());
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean validUser(Integer id, String state) {
        if (id != null) {
            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setId(id);
            //取得数据
            List<UserInfoVO> daptlist = userManageServiceImpl.findUser(userInfoVO);
            UserInfoVO tempVO = null;
            if (CollectionUtils.isNotEmpty(daptlist)) {
                tempVO = daptlist.get(0);
                // 判断state状态，若为false，则存入0，若为true,则存入1
                if ("false".equals(state)) {
                    tempVO.setIsValid("0");
                } else {
                    tempVO.setIsValid("1");
                }
                //清理缓存
                userCache.removeUserFromCache(tempVO.getUserCode());
                userManageServiceImpl.updateUser(tempVO);
                tempVO.setInterimCode(tempVO.getUserCode());
                userManageServiceImpl.updateSysUser(tempVO);
                return true;
            }
        }
        return false;
    }

    @Override
    public void resetPassword(UserInfoVO userInfoVO) {
        if (userInfoVO != null) {
            //取得数据
            List<UserInfoVO> daptlist = userManageServiceImpl.findUser(userInfoVO);
            UserInfoVO tempVO = null;
            if (CollectionUtils.isNotEmpty(daptlist)) {
                tempVO = daptlist.get(0);
                String passtemp = DataDricCommon.getValueByKey("password", "123456");
                

                passwordHandle.initPassword(tempVO.getUserCode(), passtemp);


            }
        }
    }
}
