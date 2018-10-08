package com.huayu.irla.core.dao.active;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.active.vo.ActiveVO;

/**
 * @Description 小程序活动Dao接口
 * @author 顾广婷
 * @data 2018-08-29
 */
public interface IActiveDao {
	/**
	 * @Description 查询活动列表
	 * @param active
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<ActiveVO> getActiveList(@Param("active")ActiveVO active);
	/**
	 * @Description 查询活动数量
	 * @param active
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getActiveCount(@Param("active")ActiveVO active);
	/**
	 * @Description 新增活动
	 * @param active
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addActive(@Param("active")ActiveVO active);
	/**
	 * @Description 修改活动
	 * @param active
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateActive(@Param("active")ActiveVO active);
	/**
	 * @Description 删除活动
	 * @param id
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteActive(Integer id);
}
