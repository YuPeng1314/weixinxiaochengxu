package com.huayu.irla.core.service.user;

import java.util.List;

import com.huayu.irla.core.user.vo.WxUserFriendResVO;
import com.huayu.irla.core.user.vo.WxUserInfoVO;

/**
 * @Description: 用户信息服务层的接口类
 *
 */

public interface IWxUserInfoService {

	/**
	 * 新增用户
	 * */
	void addUserInfo(WxUserInfoVO userInfoVO);
	
	/**
	 * 添加关注好友
	 * */
	boolean addUserFriend(WxUserFriendResVO userFriendVO);
	
	/**
	 * 
	  * @Title: getUserMayKnowInfo
	  * @Description: 取得用户可能认识的人
	  * @return List<WxUserInfoVO>
	  * @author liuwei
	 */
	
	List<WxUserInfoVO> getUserMayKnowInfo(WxUserInfoVO userInfoVO);
	
	/**
	 * 
	  * @Title: getUserFriendInfo
	  * @Description: 用户关注的好友信息
	  * @return List<WxUserFriendResVO>
	  * @author liuwei
	 */
	
	List<WxUserFriendResVO> getUserFriendInfo(WxUserFriendResVO userFriend);



}
