package com.huayu.irla.core.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.user.vo.WxUserFriendResVO;
import com.huayu.irla.core.user.vo.WxUserInfoVO;

public interface IWxUserInfoDao {
	
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addUserInfo(@Param("userInfoVO")WxUserInfoVO userInfoVO);
	
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	boolean addUserFriend(@Param("userFriendVO")WxUserFriendResVO userFriendVO);
	
	/**
	 * 
	  * @Title: getUserMayKnowInfo
	  * @Description: 取得用户可能认识的人
	  * @return List<WxUserInfoVO>
	  * @author liuwei
	 */
	
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<WxUserInfoVO> getUserMayKnowInfo(@Param("userInfoVO")WxUserInfoVO userInfoVO);
	
	/**
	 * 
	  * @Title: getUserFriendInfo
	  * @Description: 用户关注的好友信息
	  * @return List<WxUserFriendResVO>
	  * @author liuwei
	 */
	
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<WxUserFriendResVO> getUserFriendInfo(@Param("userFriend")WxUserFriendResVO userFriend);
	
}
