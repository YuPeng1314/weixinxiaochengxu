package com.huayu.irla.core.dao.statistics;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.statistics.vo.StatisticsVO;

/**
 * @Description 小程序后台统计Dao层接口
 * @author 顾广婷
 * @date 2018-08-29
 */
public interface IStatisticsDao {
	/**
	 * 查询资源统计列表
	 * @param statistics
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<StatisticsVO> getResStatisticsList(@Param("statistics")StatisticsVO statistics);
	/**
	 * 查询课程统计列表
	 * @param statistics
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<StatisticsVO> getCourseStatisticsList(@Param("statistics")StatisticsVO statistics);
	/**
	 * 查询资源统计列表记录数量
	 * @param statistics
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getResStatisticsCount(@Param("statistics")StatisticsVO statistics);
	/**
	 * 查询课程统计列表记录数量
	 * @param statistics
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getCourseStatisticsCount(@Param("statistics")StatisticsVO statistics);
}
