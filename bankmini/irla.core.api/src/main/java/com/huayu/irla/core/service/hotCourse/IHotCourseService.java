package com.huayu.irla.core.service.hotCourse;

import java.util.List;

import com.huayu.irla.core.courseware.vo.CoursewareVO;
/**
 * @Description 按点击量倒序查询课程service接口
 * @author 顾广婷
 * @data 2018-08-29
 */
public interface IHotCourseService {
	/**
	 * @Description 按点击量倒序查询课程
	 * @param course
	 * @return 课程
	 */
	List<CoursewareVO> getHotCourseList(CoursewareVO course);
	/**
	 * @Description 查询课程数量
	 * @param course
	 * @return 课程数量
	 */
	Integer getHotCourseCount();
}
