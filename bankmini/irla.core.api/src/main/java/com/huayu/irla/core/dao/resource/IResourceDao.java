package com.huayu.irla.core.dao.resource;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;

/**
 * 
 * @ClassName: IDirectoryDao
 * @Description:
 * @author liuwei
 * @date 2016年9月30日 下午3:30:42
 *
 */
public interface IResourceDao {

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public long getResourceSeq();

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public long getJmsResources();

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public long getResourceID();


}
