package com.huayu.irla.manage.service.courseManage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huayu.irla.core.course.vo.CourseVO;
import com.huayu.irla.manage.dao.courseManage.ICourseManageDao;
import com.huayu.irla.manage.service.courseManage.ICourseManageService;
/**
 * 课程服务接口实现类
 * @author ggt
 *
 */
@Service
@Transactional
public class ICourseManageServiceImpl implements ICourseManageService {
	
	@Autowired
	private ICourseManageDao courseManageDao;
	
	@Override
	public List<CourseVO> findCourse(CourseVO course) {
		return courseManageDao.findCourse(course);
	}

	@Override
	public Integer getCourseCount(CourseVO course) {
		return courseManageDao.getCourseCount(course);
	}

	@Override
	public Integer addCourse(CourseVO course) {
		return courseManageDao.addCourse(course);
	}

	@Override
	public Integer updateCourse(CourseVO course) {
		return courseManageDao.updateCourse(course);
	}

	@Override
	public Integer deleteCategory(Integer id) {
		return courseManageDao.deleteCourse(id);
	}

}
