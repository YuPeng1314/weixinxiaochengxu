package com.huayu.irla.core.service.course;

import java.util.List;

import com.huayu.irla.core.course.vo.CourseVO;

/**
 * @Description 小程序最新课程Service接口
 * @author 顾广婷
 * @data 2018-08-30
 */
public interface ICourseService {
	/**
	 * 查询最新课程
	 * @return
	 */
	List<CourseVO> getNewCourse();
}
