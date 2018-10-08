package com.huayu.irla.core.service.impl.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.course.vo.CourseVO;
import com.huayu.irla.core.dao.course.ICourseDao;
import com.huayu.irla.core.service.course.ICourseService;
/**
 * @Description 小程序最新课程Service接口实现类
 * @author 顾广婷
 * @data 2018-08-30
 */
@Service
public class CourseServiceImpl implements ICourseService {
	
	@Autowired
	private ICourseDao courseDao;

	@Override
	public List<CourseVO> getNewCourse() {
		return courseDao.getNewCourse();
	}
	

}
