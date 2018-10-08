package com.huayu.irla.core.service.role;

import java.util.List;

import com.huayu.irla.core.user.vo.UserInfoVO;


/**
 * 
  * @ClassName: IUserManageService
  * @Description: 用户管理服务层接口
  * @author liuwei
  * @date 2016年10月26日 上午10:53:18
  *
 */

public interface IUserManageService {

	/**
	 * 
	  * @Title: findUser
	  * @Description:取得用户信息 
	  * @author liuwei
	  * @date 2016年10月26日 上午10:53:39
	  * @param userInfoVO
	  * @return
	  * @
	 */
	
	List<UserInfoVO> findUser(UserInfoVO userInfoVO) ;
	
	/**
	 * 
	  * @Title: getCount
	  * @Description: 取得用户总数
	  * @author liuwei
	  * @date 2016年10月26日 上午10:53:49
	  * @param userInfoVO
	  * @return
	  * @
	 */
	
	Integer getCount(UserInfoVO userInfoVO) ;

	/**
	 * 
	  * @Title: updateUser
	  * @Description: 修改用户信息
	  * @author liuwei
	  * @date 2016年10月26日 上午10:50:15
	  * @param userInfoVO
	  * @
	 */
	
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
	
	void addUser(UserInfoVO userInfoVO) ;

	/**
	 * 
	  * @Title: deleteUser
	  * @Description: 删除用户信息
	  * @author liuwei
	  * @date 2016年10月26日 上午10:50:36
	  * @param id
	  * @
	 */
	
	void deleteUser(Integer id) ;
	
	/**
	 * 
	  * @Title: deleteUserIdCard
	  * @Description: 删除用户信息
	  * @author liuwei
	  * @date 2016年10月26日 上午10:50:36
	  * @param IdCard
	  * @
	 */
	void deleteUserIdCard(String  IdCard) ;
	
	/**
	 * 
	  * @Title: addSysUser
	  * @Description: 增加用户编码和初始化用户密码到用户权限信息管理表
	  * @author liuwei
	  * @date 2016年11月3日 下午6:05:25
	  * @param userInfoVO
	  * @
	 */
	
	void addSysUser(UserInfoVO userInfoVO) ;
	
	
	/**
	 * 
	  * @Title: deleteSysUser
	  * @Description: 删除SysUser里的用户信息
	  * @author liuwei
	  * @date 2016年11月18日 下午3:23:26
	  * @param userCode
	 */
	
	void deleteSysUser(String userCode) ;
	
	/**
	  * @Title: updateSysUser
	  * @Description: 修改SysUser里的用户信息
	  * @author liuwei
	  * @date 2016年11月29日 下午3:33:34
	  * @param userInfoVO
	 */
	
	void updateSysUser(UserInfoVO userInfoVO) ;
	
}
