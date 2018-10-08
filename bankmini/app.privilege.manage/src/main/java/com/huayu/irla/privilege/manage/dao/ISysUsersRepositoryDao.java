package com.huayu.irla.privilege.manage.dao;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.user.vo.MobSysUsersVO;

public interface ISysUsersRepositoryDao {
	
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	public MobSysUsersVO getByUserCode(String userCode);
}
