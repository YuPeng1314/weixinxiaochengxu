package com.huayu.irla.mobile.userinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huayu.irla.core.base.ResultVO;
import com.huayu.irla.core.service.user.IWxUserInfoService;
import com.huayu.irla.core.user.vo.WxUserFriendResVO;
import com.huayu.irla.core.user.vo.WxUserInfoVO;

@Controller
@RequestMapping("/userInfo")
public class UserInfoAction {
private Logger logger = Logger.getLogger(UserInfoAction.class);
	
	@Autowired
	private IWxUserInfoService userInfoServiceImpl;

	/**
	 * 新增用户信息，用户点击授权后记录用户信息，位置信息
	 * @param request
	 * @param response
	 * @param courseInfoVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
	public ResultVO addUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody WxUserInfoVO wxUserInfoVO) {
		ResultVO result = new ResultVO();
		try {
			userInfoServiceImpl.addUserInfo(wxUserInfoVO);
		} catch (Exception e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
	
	/**
	 * 关注成为好友
	 * @param usercode 当前用户code
	 * @param friendCode 关注的朋友code
	 * */
	@ResponseBody
	@RequestMapping(value = "/addUserFriend", method = RequestMethod.POST)
	public ResultVO addUserFriend(HttpServletRequest request, HttpServletResponse response, @RequestBody WxUserFriendResVO friendVO){
		ResultVO result = new ResultVO();
		try {
		    userInfoServiceImpl.addUserFriend(friendVO);
		} catch (Exception e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
}