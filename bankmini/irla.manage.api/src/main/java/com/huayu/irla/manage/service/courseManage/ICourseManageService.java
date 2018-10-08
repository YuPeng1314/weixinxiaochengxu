package com.huayu.irla.manage.service.courseManage;

import java.util.List;

import com.huayu.irla.core.course.vo.CourseVO;

/**
 * 课程接口服务类
 * @author ggt
 *
 */
public interface ICourseManageService {
	/**
	 * 课程查询
	 * @param course
	 * @return
	 */
	List<CourseVO> findCourse(CourseVO course);
	/**
	 * 获取课程数量
	 * @param course
	 * @return
	 */
	Integer getCourseCount(CourseVO course);
	/**
	 * 课程新增
	 * @param course
	 */
	Integer addCourse(CourseVO course);
	/**
	 * 课程修改
	 * @param course
	 */
	Integer updateCourse(CourseVO course);
	/**
	 * 课程删除
	 * @param id
	 */
	Integer deleteCategory(Integer id);
}
