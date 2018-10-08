package com.huayu.irla.manage.application.userInfoManage.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.user.vo.WxUserInfoVO;
import com.huayu.irla.manage.application.comment.impl.CommentManageImpl;
import com.huayu.irla.manage.application.userInfo.IUserInfoManage;
import com.huayu.irla.manage.service.userInfo.IUserInfoService;
@Component("userInfoManage")
public class UserInfoManageImpl implements IUserInfoManage {
	
	private static final Logger logger = Logger.getLogger(CommentManageImpl.class);
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Override
	public JSONObject findUserInfo(WxUserInfoVO userInfo) {
		JSONObject result = new JSONObject();
		try {
			if(userInfo==null) {
				userInfo=new WxUserInfoVO();
			}
			List<WxUserInfoVO> userInfoList = userInfoService.findUserInfo(userInfo);
			Integer count = userInfoService.getUserInfoCount(userInfo);
			result.put("rows", JSONArray.toJSON(userInfoList));
			result.put("total", count);
		} catch (Exception e) {
			logger.error(e);
		}
        return result;
	}

	@Override
	public Integer deleteUserInfo(Integer id) throws Exception {
		if(id==null) {
			return 1;
		}
		userInfoService.deleteUserInfo(id);
		return 0;
	}

}
