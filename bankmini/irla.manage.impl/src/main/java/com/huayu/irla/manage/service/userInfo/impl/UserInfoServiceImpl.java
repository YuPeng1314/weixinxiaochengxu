package com.huayu.irla.manage.service.userInfo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.user.vo.WxUserInfoVO;
import com.huayu.irla.manage.dao.userInfo.IUserInfoDao;
import com.huayu.irla.manage.service.userInfo.IUserInfoService;
@Service
public class UserInfoServiceImpl implements IUserInfoService {
	
	@Autowired
	private IUserInfoDao userInfoDao;
	@Override
	public List<WxUserInfoVO> findUserInfo(WxUserInfoVO userInfo) {
		return userInfoDao.findUserInfo(userInfo);
	}

	@Override
	public Integer getUserInfoCount(WxUserInfoVO userInfo) {
		return userInfoDao.getUserInfoCount(userInfo);
	}

	@Override
	public void deleteUserInfo(Integer id) {
		userInfoDao.deleteUserInfo(id);
	}

}
