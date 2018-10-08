package com.huayu.irla.manage.application.roleManage.impl;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.core.util.EhcacheUtils;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.core.service.roleManage.IRoleManageService;
import com.huayu.irla.core.user.vo.RoleInfoVO;
import com.huayu.irla.core.user.vo.UserInfoVO;
import com.huayu.irla.manage.application.roleManage.IRoleManage;
import com.huayu.irla.manage.util.UserUtils;
import com.huayu.irla.privilege.manage.common.UserMesCommon;

/**
 * 
  * @ClassName: RoleManageImpl
  * @Description: 角色管理action层实现
  * @author liuwei
  * @date 2016年10月24日 下午5:32:29
  *
 */
@Component("partmanage")
public class RoleManageImpl implements IRoleManage {

    @Autowired
    private IRoleManageService roleManageServiceImpl;
    

    @Override
    public JSONObject getRoleList(RoleInfoVO role) {
        List<RoleInfoVO> rolelist = Collections.emptyList();
        
        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        JSONObject result = new JSONObject();
        List<String> projectCodes = UserMesCommon.getProjectCode(request);
        //是否管理员
        boolean isSystemAdmin = UserMesCommon.isSystemAdmin(request);
        boolean isPlateAdmin = UserMesCommon.isPlatformAdmin(request);
        
        if(isSystemAdmin) {
    		role.setType("1");
    	}
    	if(isPlateAdmin) {
    		role.setType("5");
    	}
        
        if(null==projectCodes && !isSystemAdmin && !isPlateAdmin) {	
        	result.put("rows", JSONArray.toJSON(0));
 	        result.put("total", 0);
        } else {
	        role.setProjectCode(UserMesCommon.getProjectCode(request));
	
	        //取得所有数据信息
	        rolelist = roleManageServiceImpl.getRole(role);
	        //取得所有数据记录总数
	        Integer count = roleManageServiceImpl.getCount(role);
	        
	        result.put("rows", JSONArray.toJSON(rolelist));
	        result.put("total", count);
        }
        return result;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(RoleInfoVO role) {
        String userName = UserUtils.getLoginName();
        if (role != null) {
            //判断角色类型是否为空，若为空则默认为其他类型
            if (StringUtils.isBlank(role.getType())) {
                role.setType("4");
            }
            //判断ID是否为空，若为空，则进入增加方法，若不为空，则进入修改方法
            if (role.getRoleId() == null) {
                role.setCreatedBy(userName);
                roleManageServiceImpl.addRole(role);
            } else {
                role.setLastUpdatedBy(userName);
                roleManageServiceImpl.updateRole(role);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleInfoVO updateRole(Integer roleId) {
        if (roleId != null) {
            RoleInfoVO role = new RoleInfoVO();
            role.setRoleId(Integer.valueOf(roleId));
            Message message = PhaseInterceptorChain.getCurrentMessage();
            HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);

            if(UserMesCommon.isSystemAdmin(request)) {
            	role.setType("1");
            }
            //取得数据
            List<RoleInfoVO> daptlist = roleManageServiceImpl.getRole(role);
            RoleInfoVO updateVO = null;
            if (CollectionUtils.isNotEmpty(daptlist)) {
                updateVO = daptlist.get(0);
                return updateVO;
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRole(Integer roleId) {
        if (roleId == null) {
            return false;
        }
        RoleInfoVO role = null;
        if (role == null) {
            role = new RoleInfoVO();
        }
        List<RoleInfoVO> rolelist = Collections.emptyList();
        role.setRoleId(Integer.valueOf(roleId));
        //找到当前角色下的用户信息 
        rolelist = roleManageServiceImpl.getRoleUser(role);
        for (RoleInfoVO roleInfoVO : rolelist) {
            //删除角色中的用户信息
            roleManageServiceImpl.deleteRoleUser(roleInfoVO.getUrId());
        }
        //删除角色信息
        roleManageServiceImpl.deleteRole(roleId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean validRole(Integer id, String state) {
        if (id != null) {
        	String userName = UserUtils.getLoginName();
            RoleInfoVO role = new RoleInfoVO();
            role.setRoleId(id);
            //取得数据
            List<RoleInfoVO> daptlist = roleManageServiceImpl.getRole(role);
            RoleInfoVO roleVO = null;
            if (CollectionUtils.isNotEmpty(daptlist)) {
                roleVO = daptlist.get(0);
                //判断state状态，若为false，则存入0，若为true,则存入1
                List<RoleInfoVO> rolelist = Collections.emptyList();
                rolelist = roleManageServiceImpl.getRoleUser(role);
                if (rolelist.size() > 0) {
                    if ("false".equals(state)) {
                        roleVO.setIsValid("0");
                        for (RoleInfoVO ResVO : rolelist) {
                            ResVO.setIsValid("0");
                            ResVO.setLastUpdatedBy(userName);
                            roleManageServiceImpl.updateRoleUser(ResVO);
                        }
                    } else {
                        roleVO.setIsValid("1");
                        for (RoleInfoVO ResVO : rolelist) {
                            ResVO.setIsValid("1");
                            ResVO.setLastUpdatedBy(userName);
                            roleManageServiceImpl.updateRoleUser(ResVO);
                        }
                    }
                    roleManageServiceImpl.updateRole(roleVO);
                } else {
                    if ("false".equals(state)) {
                        roleVO.setIsValid("0");
                    } else {
                        roleVO.setIsValid("1");
                    }
                    roleManageServiceImpl.updateRole(roleVO);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject getRoleUserList(RoleInfoVO role) {
        List<RoleInfoVO> rolelist = Collections.emptyList();
        JSONObject result = new JSONObject();
        if (role.getRoleId() == null) {
            if (StringUtils.isBlank(role.getUserName())) {
                result.put("rows", JSONArray.toJSON(0));
                result.put("total", 0);
                return result;
            }
        }
        
        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        List<String> projectCodes = UserMesCommon.getProjectCode(request);
        //是否管理员
        boolean isSystemAdmin = UserMesCommon.isSystemAdmin(request);
        boolean isPlateAdmin = UserMesCommon.isPlatformAdmin(request);
        
        if(isSystemAdmin) {
    		role.setType("1");
    	}
    	if(isPlateAdmin) {
    		role.setType("5");
    	}
        
        if(null==projectCodes && !isSystemAdmin && !isPlateAdmin) {
        	result.put("rows", JSONArray.toJSON(0));
 	        result.put("total", 0);
        } else  {
	        //取得所有数据信息
	        rolelist = roleManageServiceImpl.getRoleUser(role);
	        //取得所有数据记录总数
	        Integer count = roleManageServiceImpl.getRoleUserCount(role);
	        result.put("rows", JSONArray.toJSON(rolelist));
	        result.put("total", count);
        }
        return result;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoleUser(Integer urId) {
        if (urId == null) {
            return false;
        }
        roleManageServiceImpl.deleteRoleUser(urId);
        return true;
    }

    @Override
    public JSONObject filterUserList(RoleInfoVO role) {
        List<RoleInfoVO> rolelist = Collections.emptyList();
        if (role == null) {
            role = new RoleInfoVO();
        }
        //取得所有数据信息
        rolelist = roleManageServiceImpl.filterUser(role);
        //取得所有数据记录总数
        Integer count = roleManageServiceImpl.getUserCount(role);
        JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(rolelist));
        result.put("total", count);
        return result;
    }
    
    //发送消息时选技发送人员(查询所有用户数据)
    @Override
	public JSONObject findMessageUserInfoList(UserInfoVO userInfoVO) {
    	 List<UserInfoVO> userInfoVOlist = Collections.emptyList();
         if (userInfoVO == null) {
        	 userInfoVO = new UserInfoVO();
         }
         //取得所有数据信息
         userInfoVOlist = roleManageServiceImpl.findMessageUserInfoList(userInfoVO);
         //取得所有数据记录总数
         Integer count = roleManageServiceImpl.getMessageUserInfoCount(userInfoVO);
         JSONObject result = new JSONObject();
         result.put("rows", JSONArray.toJSON(userInfoVOlist));
         result.put("total", count);
         return result;
	}

    //添加进角色用户维护表中
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoleUser(String roleId, String tem) {
        if (!StringUtils.isBlank(roleId)) {
            String userName = UserUtils.getLoginName();
            //截取逗号分割的UserCode
            String a[] = tem.split(",");
            RoleInfoVO role = new RoleInfoVO();
            //将数组a进行遍历
            for (int i = 0; i < a.length; i++) {
                role.setUserCode(a[i]);
                //通过UserCode查询到UserId
                RoleInfoVO tempVO = roleManageServiceImpl.findUserID(role);
                role.setUserId(tempVO.getUserId());
                role.setRoleId(Integer.valueOf(roleId));
                role.setCreatedBy(userName);
                roleManageServiceImpl.addRoleUser(role);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean validRoleUser(Integer id, String state) {
        if (id != null) {
        	String userName = UserUtils.getLoginName();
            RoleInfoVO role = new RoleInfoVO();
            role.setUrId(Integer.valueOf(id));
            //取得数据
            List<RoleInfoVO> daptlist = roleManageServiceImpl.getRoleUser(role);
            RoleInfoVO roleVO = null;
            if (CollectionUtils.isNotEmpty(daptlist)) {
                roleVO = daptlist.get(0);
                //判断state状态，若为false，则存入0，若为true,则存入1
                if ("false".equals(state)) {
                    roleVO.setIsValid("0");
                } else {
                    roleVO.setIsValid("1");
                }
                roleVO.setLastUpdatedBy(userName);
                roleManageServiceImpl.updateRoleUser(roleVO);
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject getAuthorityList(RoleInfoVO role) {
        List<RoleInfoVO> rolelist = Collections.emptyList();
        if (role == null) {
            role = new RoleInfoVO();
        }
        //取得所有数据信息
        //得到用户对应信息
        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        boolean isSystemAdmin = UserMesCommon.isSystemAdmin(request);
        role.setType(isSystemAdmin?"1":"-1");
        role.setUserCode(UserMesCommon.getUserName(request));
        
        rolelist = roleManageServiceImpl.getAuthority(role);
        JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(rolelist));
        return result;
    }

    @Override
    public JSONObject getRoleAuthorityList(Integer roleId) {
        if (roleId == null) {
            return null;
        }
        RoleInfoVO role = null;
        List<RoleInfoVO> rolelist = Collections.emptyList();
        if (role == null) {
            role = new RoleInfoVO();
            role.setRoleId(Integer.valueOf(roleId));
            ;
        }
        //取得所有数据信息
        rolelist = roleManageServiceImpl.getRoleAuthority(role);
        JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(rolelist));
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoleAuthority(Integer roleId, String tem) {
        if (roleId == null) {
            return false;
        }
        roleManageServiceImpl.deleteRoleAuthority(roleId);
        String userName = UserUtils.getLoginName();
        String a[] = tem.split(",");
        RoleInfoVO role = new RoleInfoVO();
        //将数组a进行遍历
        for (int i = 0; i < a.length; i++) {
            if (!StringUtils.equals(a[i], "A")) {
                role.setAuthorityId(a[i]);
                role.setRoleId(roleId);
                role.setCreatedBy(userName);
                roleManageServiceImpl.addRoleAuthority(role);
            }
        }
        /*userCache.removeUserFromCache(userName);*/
        //清理缓存,对应缓存块中的全部缓存
    	EhcacheUtils ehcacheUtils = EhcacheUtils.getInstance(new String[]{"adminManager"});
    	ehcacheUtils.removeAll("RoleInfoCache");
    	
    	
        return true;
    }

    /**
     * 
      * @Title: findRoleType
      * @Description: 角色类型
      * @author liuwei
      * @date 2017年9月26日 下午5:27:32
     */
    
	@Override
	public JSONObject findRoleType() {
		Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		List<String> projectCodes = UserMesCommon.getProjectCode(request);
		boolean isSystemAdmin = UserMesCommon.isSystemAdmin(request);
		boolean isPlateformAdmin = UserMesCommon.isPlatformAdmin(request);
		
		//取得数据记录总数
		JSONObject result = new JSONObject();
		String type = DataDricCommon.getValueByKey("osp.role.type");
		JSONArray parseObject = JSONArray.parseArray(type);
		if(null!= projectCodes && !isSystemAdmin && !isPlateformAdmin) {
			result.put("types", parseObject);
		} else {
			String sysType = DataDricCommon.getValueByKey("role.type");
			JSONArray sysParseObject = JSONArray.parseArray(sysType);
			sysParseObject.addAll(parseObject);
			result.put("types", sysParseObject);
		}
		return result ;
	}
}
