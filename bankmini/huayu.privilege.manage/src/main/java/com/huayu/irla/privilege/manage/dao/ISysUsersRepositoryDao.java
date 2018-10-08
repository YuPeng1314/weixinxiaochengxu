package com.huayu.irla.privilege.manage.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.privilege.manage.vo.SysAuthoritiesVO;
import com.huayu.irla.privilege.manage.vo.SysLoginoutVO;
import com.huayu.irla.privilege.manage.vo.SysPersistentLoginsVO;
import com.huayu.irla.privilege.manage.vo.SysRolesVO;
import com.huayu.irla.privilege.manage.vo.SysUsersVO;

public interface ISysUsersRepositoryDao {
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	public SysUsersVO getByUsername(String username);

	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	public List<SysAuthoritiesVO> getSysAuthoritiesByUsername(@Param("username") String username,
			@Param("ausTypes") String[] ausTypes);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void createNewToken(SysPersistentLoginsVO perLoginVO);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void removeUserTokens(String userName);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void updateToken(@Param("series") String series, @Param("tokenValue") String tokenValue,
			@Param("endDate") Date endDate);

	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	public SysPersistentLoginsVO getTokenForSeries(String series);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void updatePassword(@Param("userName") String userName, @Param("password") String password,
			@Param("passType") int passType);

	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	public List<SysRolesVO> getRoleInfo(@Param("userName") String userName);
	

	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	public String getParentDeptCode(@Param("deptCode") String deptCode);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void updateUserLockedState(@Param("isLocked") boolean isLocked, @Param("userName") String userName);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void updateOtherUserInfo(SysUsersVO sysUsersVO);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void recordLoginoutLog(SysLoginoutVO sysLoginoutVO);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void recordOnlineUsers();

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void recordOfflineUsers();

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void clearOnlineUsers();

	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	public int getOnlineUsers();
	
}
