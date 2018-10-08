package com.huayu.irla.mobile.hotCourse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huayu.irla.core.base.ResultVO;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.core.service.hotCourse.IHotCourseService;

/**
 * @Description 按点击量倒序查询课程小程序
 * @author 顾广婷
 * @data 2018-08-29
 */
@Controller
@RequestMapping("/hotCourseAction")
public class HotCourseAction {
	
	private Logger logger = Logger.getLogger(HotCourseAction.class);
	
	@Autowired
	private IHotCourseService hotCourseService;

	@ResponseBody
	@RequestMapping(value = "/courseShow", method = RequestMethod.GET)
	public ResultVO commentShow(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			String limit = request.getParameter("limit"); 
			String offset = request.getParameter("offset");
			//分页查询条件
			Integer limitInt = null;
			Integer realOffset = null;
			if(StringUtils.isNotBlank(limit) && StringUtils.isNotBlank(offset)) {
				Integer tempInt = Integer.parseInt(offset);
				limitInt = Integer.parseInt(limit);
				realOffset = limitInt * (tempInt - 1);
			}
			CoursewareVO course = new CoursewareVO();
			course.setOffset(realOffset);
			course.setLimit(limitInt);
			List<CoursewareVO> courseList = hotCourseService.getHotCourseList(course);
			Integer count = hotCourseService.getHotCourseCount();
			Map<String, Object> standbyParams = new HashMap<String, Object>();
			standbyParams.put("count", count);
			result.setNetCode(200);
			result.setStandbyParams(standbyParams);
			result.setData(courseList);
		} catch (Exception e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
}
