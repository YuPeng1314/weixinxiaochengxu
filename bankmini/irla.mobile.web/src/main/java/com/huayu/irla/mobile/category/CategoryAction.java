package com.huayu.irla.mobile.category;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.base.ResultVO;
import com.huayu.irla.core.category.vo.CategoryVO;
import com.huayu.irla.core.courseware.vo.CourseInfoVO;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.core.service.category.ICategoryService;
import com.huayu.irla.core.service.courseware.ICoursewareService;
import com.huayu.irla.core.service.hotCourse.IHotCourseService;
import com.huayu.irla.mobile.utils.CommonUtils;

/**
 * 
  * @ClassName: CategoryAction
  * @Description: 小程序分类
  * @author liuwei
  * @date 2018年6月27日 下午5:00:35
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

@Controller
@RequestMapping("/categoryAction")
public class CategoryAction {
	
	private Logger logger = Logger.getLogger(CategoryAction.class);
	
	@Autowired
	private ICategoryService categoryServiceImpl;
	
	@Autowired
	private ICoursewareService coursewareServiceImpl;
	
	@Autowired
	private IHotCourseService hotCourseService;
	/**
	 * 
	  * @Title: categoryShow
	  * @Description: 小程序分类数据展现
	  * @return ResultVO
	  * @author liuwei
	  * @date 2018年6月27日 下午5:03:49
	 */
	
	@ResponseBody
	@RequestMapping(value = "/categoryShow", method = RequestMethod.GET)
	public ResultVO categoryShow(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			JSONArray data = new JSONArray();
			List<CategoryVO> categoryList = categoryServiceImpl.findCategoryCore();
			if(CollectionUtils.isNotEmpty(categoryList)) {
				JSONObject root = new JSONObject();
				initTree(categoryList, root, "-1");
				String tempResult = root.getString("nodes");
				tempResult = tempResult.replaceAll("categoryName", "text");
				data = JSONArray.parseArray(tempResult);
			}
			result.setData(data);
			result.setNetCode(200);
		} catch (UnsupportedEncodingException e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}

	/**
	 * 
	  * @Title: initTree
	  * @Description: 初始化目录类别（递归）
	  * @return void
	  * @author liuwei
	  * @date 2018年6月27日 下午5:08:36
	 */
	
	private void initTree(List<CategoryVO> params, JSONObject root, String parentId) {
		for (CategoryVO type : params) {
			String parentCategoryCode = type.getParent_category_code();
			if (StringUtils.equals(parentCategoryCode, parentId)) {
				JSONArray children = root.getJSONArray("nodes");
				if (children == null) {
					children = new JSONArray();
				}
				JSONObject json = (JSONObject) JSONObject.toJSON(type);
				children.add(json);
				root.put("nodes", children);
				String categoryCode = type.getCategory_code();
				initTree(params, json, categoryCode);
			}
		}
	}
	
	
	/**
	 * 
	  * @Title: courseShow
	  * @Description: 根据类别查询课程
	  * @return ResultVO
	  * @author liuwei
	  * @date 2018年6月28日 上午9:15:28
	 */
	
	@ResponseBody
	@RequestMapping(value = "/courseShow", method = RequestMethod.GET)
	public ResultVO courseShow(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			String limit = request.getParameter("limit"); 
			String offset = request.getParameter("offset");
			String categoryCode = request.getParameter("categoryCode"); //类别编码
			String dirCode = request.getParameter("dirCode");
			String searchContent = request.getParameter("searchkey");
			
			//分页查询条件
			Integer limitInt = null;
			Integer realOffset = null;
			if(StringUtils.isNotBlank(limit) && StringUtils.isNotBlank(offset)) {
				Integer tempInt = Integer.parseInt(offset);
				limitInt = Integer.parseInt(limit);
				realOffset = limitInt * (tempInt - 1);
			}
			if(StringUtils.equals(categoryCode, "C005")) {
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
			}else {
				// 课程
				CourseInfoVO courseVo = new CourseInfoVO();
				courseVo.setIsValid("1");
				courseVo.setOffset(realOffset);
				courseVo.setLimit(limitInt);
				courseVo.setCategoryCode(categoryCode);
				courseVo.setDirCode(dirCode);
				courseVo.setSearch(searchContent);
				List<CourseInfoVO> courseList = coursewareServiceImpl.findCourseInfo(courseVo);
				Integer count = coursewareServiceImpl.findCourseInfoCount(courseVo);
				Map<String, Object> standbyParams = new HashMap<String, Object>();
				standbyParams.put("nginxPath", CommonUtils.getNginxPath());
				standbyParams.put("resUrl", CommonUtils.getResFile());
				standbyParams.put("count", count);
				result.setNetCode(200);
				result.setStandbyParams(standbyParams);
				result.setData(courseList);
			}
		} catch (Exception e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
	
	
}
