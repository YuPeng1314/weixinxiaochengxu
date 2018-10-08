package com.huayu.irla.mobile.personal;

import java.io.UnsupportedEncodingException;
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
import com.huayu.irla.core.service.courseware.ICoursewareService;
import com.huayu.irla.mobile.utils.CommonUtils;
import com.huayu.irla.privilege.manage.common.UserMesCommon;

/**
 * 
  * @ClassName: PersonalAction
  * @Description: 我的
  * @author liuwei
  * @date 2018年7月2日 下午2:56:28
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

@Controller
@RequestMapping("/personalInfo")
public class PersonalAction {
	
	private Logger logger = Logger.getLogger(PersonalAction.class);
	
	@Autowired
	private ICoursewareService coursewareServiceImpl;

	/**
	 * 
	  * @Title: myCollect
	  * @Description: 我的收藏
	  * @return ResultVO
	  * @author liuwei
	  * @date 2018年7月2日 下午4:02:45
	 */
	
	@ResponseBody
	@RequestMapping(value = "/myCollect", method = RequestMethod.GET)
	public ResultVO myCollect(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			String userCode = UserMesCommon.getUserCode(request);
			if (StringUtils.isNotBlank(userCode)) {
				CoursewareVO coursevo = new CoursewareVO();
				coursevo.setCreatedBy(userCode);
				List<CoursewareVO> courseList = coursewareServiceImpl.getResCollect(coursevo);
				Integer count = coursewareServiceImpl.getResCollectCount(coursevo);
				result.setData(courseList);
				Map<String, Object> standbyParams = new HashMap<String, Object>();
				standbyParams.put("nginxPath", CommonUtils.getNginxPath());            //访问IP
				standbyParams.put("videoUrl", CommonUtils.getVideoFile());               //图片访问配置路径
				standbyParams.put("count", count);
				result.setStandbyParams(standbyParams);
				result.setNetCode(200);
			} else {
				result.setNetCode(500);
				result.setErrorMessage("参数不能为空！");
			}
		} catch (UnsupportedEncodingException e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
	
}
