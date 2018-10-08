package com.huayu.irla.manage.application.sectionCourse.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.core.service.courseware.ICoursewareService;
import com.huayu.irla.manage.application.sectionCourse.ISectionCourse;
import com.huayu.irla.manage.util.UserUtils;
@Component("sectioncourse")
public class SectionCourseImpl implements ISectionCourse {
	private static final Logger logger = Logger.getLogger(SectionCourseImpl.class);
	@Autowired
	private ICoursewareService coursewareServiceImpl;
	@Override
	public JSONObject findResourse(CoursewareVO resourse) {
		JSONObject result = new JSONObject();
		if(resourse==null) {
			resourse=new CoursewareVO();
		}
		List<CoursewareVO> datalist = Collections.emptyList();
		//取得数据的总数
        Integer count = coursewareServiceImpl.findCoursewareCount(resourse);
		//取得数据
		datalist = coursewareServiceImpl.findCourseware(resourse);
        result.put("rows", JSONArray.toJSON(datalist));
        result.put("total", count);
        return result;
	}

	@Override
	public boolean updateResourseSection(Map<String, String>[] resourses) {
		try {
			String userName = UserUtils.getLoginName();
			for(int i=0;i<resourses.length;i++) {
				CoursewareVO resourse=new CoursewareVO();
				resourse.setId(resourses[i].get("id"));
				resourse.setSequenceNumber(Integer.valueOf(resourses[i].get("sequenceNumber")));
				resourse.setLastUpdatedBy(userName);
			    coursewareServiceImpl.updateSequenceNumber(resourse);
			}
			return true;
		} catch (NumberFormatException e) {
			logger.error("集数修改失败！");
		}
		return false;
	}

}
