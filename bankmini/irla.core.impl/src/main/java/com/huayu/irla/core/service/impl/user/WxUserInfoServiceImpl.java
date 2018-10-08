package com.huayu.irla.core.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huayu.irla.core.dao.user.IWxUserInfoDao;
import com.huayu.irla.core.service.user.IWxUserInfoService;
import com.huayu.irla.core.user.vo.WxUserFriendResVO;
import com.huayu.irla.core.user.vo.WxUserInfoVO;


@Service
@Transactional
public class WxUserInfoServiceImpl implements IWxUserInfoService{

	@Autowired
	private IWxUserInfoDao userInfoDao;
	
	@Override
	public void addUserInfo(WxUserInfoVO userInfoVO) {
		userInfoDao.addUserInfo(userInfoVO);
	}

	@Override
	public List<WxUserInfoVO> getUserMayKnowInfo(WxUserInfoVO userInfoVO) {
		return userInfoDao.getUserMayKnowInfo(userInfoVO);
	}

	@Override
	public List<WxUserFriendResVO> getUserFriendInfo(WxUserFriendResVO userFriend) {
		return userInfoDao.getUserFriendInfo(userFriend);
	}

	@Override
	public boolean addUserFriend(WxUserFriendResVO userFriendVO) {
		return userInfoDao.addUserFriend(userFriendVO);
	}


}
