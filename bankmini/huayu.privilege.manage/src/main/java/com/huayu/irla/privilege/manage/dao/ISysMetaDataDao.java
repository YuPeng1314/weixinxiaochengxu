package com.huayu.irla.privilege.manage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.privilege.manage.vo.SysUrlPersistentVO;

public interface ISysMetaDataDao {
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	public List<Map<String, String>> getURLResourceMapping(@Param("types") List<String> types);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void deleteAuthorities();

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void deleteResources();

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void deleteAuthResRel();

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public long getAuthoritiySeq();

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public long getResSeq();

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void addAuthorities(SysUrlPersistentVO urlVO);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void addResources(SysUrlPersistentVO urlVO);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void addAuthResRel(SysUrlPersistentVO urlVO);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public long getPriSeq();

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void addPrivilegeTmp(SysUrlPersistentVO urlVO);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void initalPrivilege(Map<String, Object> map);
}
