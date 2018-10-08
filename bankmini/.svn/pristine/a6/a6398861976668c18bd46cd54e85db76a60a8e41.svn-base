package com.huayu.irla.core.service.impl.hotCourse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.core.dao.hotCourse.IHotCourseDao;
import com.huayu.irla.core.service.hotCourse.IHotCourseService;
/**
 * @Description 按点击量倒序查询课程service接口实现类
 * @author 顾广婷
 * @data 2018-08-29
 */
@Service
public class HotCourseServiceImpl implements IHotCourseService {
	
	@Autowired
	private IHotCourseDao hotCourseDao;
	
	@Override
	public List<CoursewareVO> getHotCourseList(CoursewareVO course) {
		return hotCourseDao.getHotCourseList(course);
	}

	@Override
	public Integer getHotCourseCount() {
		return hotCourseDao.getHotCourseCount();
	}

}
