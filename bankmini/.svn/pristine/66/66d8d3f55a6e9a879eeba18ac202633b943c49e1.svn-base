package com.huayu.irla.manage.dao.userInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.user.vo.WxUserInfoVO;
/**
 * 小程序用户信息后台Dao接口
 * @author 顾广婷
 *
 */
public interface IUserInfoDao {
	/**
	 * 查询用户信息
	 * @param userInfo
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<WxUserInfoVO> findUserInfo(@Param("userInfo") WxUserInfoVO userInfo);
	/**
	 * 查询信息数量
	 * @param userInfo
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getUserInfoCount(@Param("userInfo") WxUserInfoVO userInfo);
	/**
	 * 删除用户信息
	 * @param id
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	void deleteUserInfo(Integer id);
}
