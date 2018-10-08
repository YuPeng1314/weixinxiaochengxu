package com.huayu.irla.manage.service.userInfo;

import java.util.List;

import com.huayu.irla.core.user.vo.WxUserInfoVO;
/**
 * 小程序后台用户信息Service接口
 * @author 顾广婷
 *
 */
public interface IUserInfoService {
	/**
	 * 查询用户信息
	 * @param userInfo
	 * @return
	 */
	List<WxUserInfoVO> findUserInfo(WxUserInfoVO userInfo);
	/**
	 * 查询信息数量
	 * @param userInfo
	 * @return
	 */
	Integer getUserInfoCount(WxUserInfoVO userInfo);
	/**
	 * 删除用户信息
	 * @param id
	 */
	void deleteUserInfo(Integer id);
}
