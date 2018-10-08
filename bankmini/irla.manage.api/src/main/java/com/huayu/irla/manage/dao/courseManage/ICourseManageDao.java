package com.huayu.irla.manage.dao.courseManage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.course.vo.CourseVO;

public interface ICourseManageDao {
	/**
	 * 
	 * @Title: findCourse
	 * @Description:查询类别
	 * @return List<CourseVO>
	 * @author ggt
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CourseVO> findCourse(@Param("course") CourseVO course);

	/**
	 * 
	 * @Title: getCourseCount
	 * @Description: 取得类别数量
	 * @return Integer
	 * @author ggt
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getCourseCount(@Param("course") CourseVO course);

	/**
	 * 
	 * @Title: addCourse
	 * @Description: 添加类别
	 * @author ggt
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	Integer addCourse(@Param("course") CourseVO course);

	/**
	 * 
	 * @Title: updateCourse
	 * @Description: 修改类别
	 * @author ggt
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	Integer updateCourse(@Param("course") CourseVO course);

	/**
	 * 
	 * @Title: deleteCourse
	 * @Description: 删除类别
	 * @author ggt
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	Integer deleteCourse(Integer id);
}
