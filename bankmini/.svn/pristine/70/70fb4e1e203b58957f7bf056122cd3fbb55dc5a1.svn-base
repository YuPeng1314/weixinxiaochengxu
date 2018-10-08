package com.huayu.irla.core.dao.holiday;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.holiday.vo.HolidayVO;

/**
 * 
 * @ClassName: IHolidayDao
 * @Description: 节日Dao层接口类
 * @author ggt
 * @date 2018-07-24 Copyright: Copyright (c) 2017 Company:华煜网络教育有限公司
 */
public interface IHolidayDao {
	/**
	 * 查询节日记录
	 * @return 节日列表
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<HolidayVO> getHolidayList(@Param("holiday")HolidayVO holiday);
	/**
	 * 获取节日数量
	 * @param phiz 节日实体类
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getHolidayCount(@Param("holiday")HolidayVO holiday);
	/**
	 * 节日新增
	 * @param phiz 节日实体类
	 * @return 
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer addHoliday(@Param("holiday")HolidayVO holiday);
	/**
	 * 节日修改
	 * @param phiz 节日实体类
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer updateHoliday(@Param("holiday")HolidayVO holiday);
	/**
	 * 节日删除
	 * @param id 节日记录id
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer delHoliday(Integer id);
	
}
