package com.huayu.irla.core.dao.share;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.share.vo.ShareVO;

/**
 * 
 * @ClassName: IShareDao
 * @Description: 转发量DAO层接口
 * @author GuGuangting
 * @date 2018年8月28日 pm4:49:21
 *
 */
public interface IShareDao {
	/**
	 * 
	 * @Title: findShare
	 * @Description: 查询转发的资源是否存在
	 * @author GuGuangting
	 * @date 2018年8月28日 pm4:49:21
	 * @param ShareVO
	 * 
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getShareCount(@Param("share")ShareVO share);
	/**
	 * 
	 * @Title: addShare
	 * @Description: 转发新增
	 * @author GuGuangting
	 * @date 2018年8月28日 pm4:49:21
	 * @param ShareVO
	 * 
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addShare(@Param("share")ShareVO share);
	/**
	 * 
	 * @Title: updateShare
	 * @Description: 转发量更新
	 * @author GuGuangting
	 * @date 2018年8月28日 pm4:49:21
	 * @param ShareVO
	 * 
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateShare(@Param("share")ShareVO share);
}
