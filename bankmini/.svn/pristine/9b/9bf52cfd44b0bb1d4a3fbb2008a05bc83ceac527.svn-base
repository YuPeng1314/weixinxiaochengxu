package com.huayu.irla.core.dao.role;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.user.vo.UserInfoVO;

/**
 * 
 * @ClassName: IUserManageDao
 * @Description: 用户管理DAO层接口
 * @author liuwei
 * @date 2016年10月26日 上午10:49:21
 *
 */
public interface IUserManageDao {

	/**
	 * 
	 * @Title: findUser
	 * @Description: 取得用户信息
	 * @author liuwei
	 * @date 2016年10月26日 上午10:49:49
	 * @param userInfoVO
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<UserInfoVO> findUser(@Param("userInfoVO") UserInfoVO userInfoVO);

	/**
	 * 
	 * @Title: getCount
	 * @Description: 取得用户总数
	 * @author liuwei
	 * @date 2016年10月26日 上午10:50:00
	 * @param userInfoVO
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getCount(@Param("userInfoVO") UserInfoVO userInfoVO);

	/**
	 * 
	 * @Title: updateUser
	 * @Description: 修改用户信息
	 * @author liuwei
	 * @date 2016年10月26日 上午10:50:15
	 * @param userInfoVO
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateUser(UserInfoVO userInfoVO);

	/**
	 * 
	 * @Title: addUser
	 * @Description: 增加用户信息
	 * @author liuwei
	 * @date 2016年10月26日 上午10:50:25
	 * @param userInfoVO
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addUser(UserInfoVO userInfoVO);

	/**
	 * 
	 * @Title: deleteUser
	 * @Description: 删除用户信息
	 * @author liuwei
	 * @date 2016年10月26日 上午10:50:36
	 * @param id
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteUser(Integer id);

	/**
	 * 
	 * @Title: deleteUserIdCard
	 * @Description: 删除用户信息
	 * @author liuwei
	 * @date 2016年10月26日 上午10:50:36
	 * @param id
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteUserIdCard(String IdCard);

	/**
	 * 
	 * @Title: addSysUser
	 * @Description: 增加用户编码和初始化用户密码到用户权限信息管理表
	 * @author liuwei
	 * @date 2016年11月3日 下午6:05:25
	 * @param userInfoVO
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addSysUser(UserInfoVO userInfoVO);

	/**
	 * 
	 * @Title: deleteSysUser
	 * @Description: 删除SysUser里的用户信息
	 * @author liuwei
	 * @date 2016年11月18日 下午3:23:26
	 * @param userCode
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteSysUser(String userCode);

	/**
	 * @Title: updateSysUser
	 * @Description: 修改SysUser里的用户信息
	 * @author liuwei
	 * @date 2016年11月29日 下午3:33:34
	 * @param userInfoVO
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateSysUser(UserInfoVO userInfoVO);

	/**
	 * 
	 * @Title: getImportCodeSeq
	 * @Description: 查出导入用户批次编码
	 * @return long
	 * @author liuwei
	 * @date 2017年12月1日 下午2:41:04
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public long getImportCodeSeq();

}
