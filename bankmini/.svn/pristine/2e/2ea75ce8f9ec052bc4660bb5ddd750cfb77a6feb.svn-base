package com.huayu.irla.core.service.impl.roleManage;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huayu.irla.core.dao.roleManage.IRoleManageDao;
import com.huayu.irla.core.service.roleManage.IRoleManageService;
import com.huayu.irla.core.user.vo.RoleInfoVO;
import com.huayu.irla.core.user.vo.UserInfoVO;

/**
 * 
  * @ClassName: RoleManageServiceImpl
  * @Description: 角色管理服务层实现
  * @author liuwei
  * @date 2016年10月24日 下午5:32:57
  *
 */

@Service
@Transactional
public class RoleManageServiceImpl implements IRoleManageService {

	@Autowired
	private IRoleManageDao roleManageDao;
	
	@Override
	public List<RoleInfoVO> getRole(RoleInfoVO role)  {
		return roleManageDao.getRole(role);
	}

	@Override
	public Integer getCount(RoleInfoVO role)  {
		return roleManageDao.getCount(role);
	}

	@Override
	public void updateRole(RoleInfoVO role)  {
		 roleManageDao.updateRole(role);

	}

	@Override
	public void addRole(RoleInfoVO role)  {
		roleManageDao.addRole(role);

	}

	@Override
	public void deleteRole(Integer roleId)  {
		roleManageDao.deleteRole(roleId);

	}

	@Override
	public List<RoleInfoVO> getRoleUser(RoleInfoVO role)  {
		return roleManageDao.getRoleUser(role);
	}

	@Override
	public void deleteRoleUser(Integer id)  {
		roleManageDao.deleteRoleUser(id);
		
	}

	@Override
	public List<RoleInfoVO> filterUser(RoleInfoVO role)  {
		return roleManageDao.filterUser(role);
	}

	@Override
	public void addRoleUser(RoleInfoVO role)  {
		roleManageDao.addRoleUser(role);
		
	}



	@Override
	public Integer getRoleUserCount(RoleInfoVO role)  {
		return roleManageDao.getRoleUserCount(role);
	}

	@Override
	public Integer getUserCount(RoleInfoVO role)  {
		return roleManageDao.getUserCount(role);
	}

	@Override
	public void updateRoleUser(RoleInfoVO role)  {
		roleManageDao.updateRoleUser(role);
		
	}

	@Override
	public RoleInfoVO findUserID(RoleInfoVO role)  {
		return roleManageDao.findUserID(role);		
	}



	@Override
	public List<RoleInfoVO> getAuthority(RoleInfoVO role)  {
		return roleManageDao.getAuthority(role);
	}

	@Override
	@Cacheable(value = "RoleInfoCache", key="#role.roleId")
	public List<RoleInfoVO> getRoleAuthority(RoleInfoVO role)  {
		return roleManageDao.getRoleAuthority(role);
	}


	@Override
	public void addRoleAuthority(RoleInfoVO role)  {
		roleManageDao.addRoleAuthority(role);
	}

	@Override
	public void deleteRoleAuthority(Integer roleId)  {
		roleManageDao.deleteRoleAuthority(roleId);
		
	}

	@Override
	public void deleteRoleUserUserID(Integer userId) {
		roleManageDao.deleteRoleUserUserID(userId);
		
	}

	@Override
	public List<UserInfoVO> findMessageUserInfoList(UserInfoVO userInfoVO) {
		return roleManageDao.findMessageUserInfoList(userInfoVO);
	}

	@Override
	public Integer getMessageUserInfoCount(UserInfoVO userInfoVO) {
		return roleManageDao.getMessageUserInfoCount(userInfoVO);
	}

}
