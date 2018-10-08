package com.huayu.irla.core.dao.hotCourse;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.courseware.vo.CoursewareVO;

/**
 * @Description 按点击量倒序查询课程Dao接口
 * @author 顾广婷
 * @data 2018-08-29
 */
public interface IHotCourseDao {
	/**
	 * @Description 按点击量倒序查询课程
	 * @param course
	 * @return 课程
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CoursewareVO> getHotCourseList(@Param("course") CoursewareVO course);
	/**
	 * @Description 查询课程数量
	 * @param course
	 * @return 课程数量
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getHotCourseCount();
}
