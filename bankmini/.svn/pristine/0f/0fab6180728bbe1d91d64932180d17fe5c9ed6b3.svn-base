package com.huayu.irla.core.dao.course;

import java.util.List;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.course.vo.CourseVO;

/**
 * @Description 小程序最新课程Dao接口
 * @author 顾广婷
 * @data 2018-08-30
 */
public interface ICourseDao {
	/**
	 * 查询最新课程
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CourseVO> getNewCourse();
}
