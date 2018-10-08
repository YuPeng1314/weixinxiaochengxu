package com.huayu.irla.core.service.impl.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huayu.irla.core.dao.role.IUserManageDao;
import com.huayu.irla.core.service.role.IUserManageService;
import com.huayu.irla.core.user.vo.UserInfoVO;

/**
 * 
  * @ClassName: UserManageServiceImpl
  * @Description: 用户管理服务层的实现
  * @author liuwei
  * @date 2016年10月26日 上午11:04:00
  *
 */
@Service
@Transactional
public class UserManageServiceImpl implements IUserManageService {

	
	@Autowired
	private IUserManageDao userManageDao;
	
	@Override
	public List<UserInfoVO> findUser(UserInfoVO userInfoVO) {
			return userManageDao.findUser(userInfoVO);
		
	}

	@Override
	public void updateUser(UserInfoVO userInfoVO) {
			userManageDao.updateUser(userInfoVO);
	}

	@Override
	public void addUser(UserInfoVO userInfoVO){
			userManageDao.addUser(userInfoVO);
	}

	@Override
	public Integer getCount(UserInfoVO userInfoVO){
			return userManageDao.getCount(userInfoVO);
	}

	@Override
	public void deleteUser(Integer id) {
			userManageDao.deleteUser(id);
	}
	
	@Override
	public void deleteUserIdCard(String IdCard) {
		userManageDao.deleteUserIdCard(IdCard);
	}

	@Override
	public void addSysUser(UserInfoVO userInfoVO)  {
		    userManageDao.addSysUser(userInfoVO);
		
	}

	@Override
	public void deleteSysUser(String userCode) {
		userManageDao.deleteSysUser(userCode);
		
	}

	@Override
	public void updateSysUser(UserInfoVO userInfoVO) {
		userManageDao.updateSysUser(userInfoVO);		
	}






}
