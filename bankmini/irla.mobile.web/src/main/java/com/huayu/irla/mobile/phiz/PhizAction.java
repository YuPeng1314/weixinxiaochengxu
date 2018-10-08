package com.huayu.irla.mobile.phiz;

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
import com.huayu.irla.core.holiday.vo.HolidayVO;
import com.huayu.irla.core.phiz.vo.PhizVO;
import com.huayu.irla.core.service.holiday.IHolidayService;
import com.huayu.irla.core.service.phiz.IPhizService;
import com.huayu.irla.core.service.theme.IThemeService;
import com.huayu.irla.core.theme.vo.ThemeVO;
import com.huayu.irla.mobile.utils.CommonUtils;

/**
 * 
  * @ClassName: PhizAction
  * @Description: 表情包
  * @author ggt
  * @date 2018年7月24日 
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */
@Controller
@RequestMapping("/phizAction")
public class PhizAction {
	private Logger logger = Logger.getLogger(PhizAction.class);
	
	@Autowired
	private IPhizService phizService;
	@Autowired
	private IHolidayService holidayService;
	@Autowired
	private IThemeService themeService;
	/**
	 * 按节日查询表情
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findHolidayPhiz", method = RequestMethod.GET)
	public ResultVO getHolidayPhiz(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		PhizVO phiz = new PhizVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			Map<String, Object> data = new HashMap<String, Object>();
			String holidayCode=request.getParameter("holidayCode");
			
			//得到起始位置和每页条数
			//得到起始位置和每页条数
			String limit = request.getParameter("limit"); 
			String offset = request.getParameter("offset");
			Integer limitInt = null;
			Integer realOffset = null;
			if(StringUtils.isNotBlank(limit) && StringUtils.isNotBlank(offset)) {
				Integer tempInt = Integer.parseInt(offset);
				limitInt = Integer.parseInt(limit);
				realOffset = limitInt * (tempInt - 1);
			}
			
			
			if(StringUtils.isNotBlank(holidayCode)) {
				phiz.setHolidayCode(holidayCode);
				phiz.setIsValid("1");
				
				phiz.setOffset(realOffset);
				phiz.setLimit(limitInt);
				
				List<PhizVO> phizHolidayList = phizService.getPhizList(phiz);
				Integer holidayCount = phizService.getPhizCount(phiz);
				data.put("phizHolidayList", phizHolidayList);
				data.put("holidayCount", holidayCount);
			}else {
				HolidayVO holiday= new HolidayVO();
				holiday.setOffset(realOffset);
				holiday.setLimit(limitInt);
				
				List<HolidayVO> holidayList = holidayService.getHolidayList(holiday);
				Integer holidayCount = holidayService.getHolidayCount(holiday);
				data.put("holidayList", holidayList);
				data.put("holidayCount", holidayCount);
			}
			result.setData(data);
			result.setStandbyParams(getResImgPath());
			result.setNetCode(200);
		} catch (UnsupportedEncodingException e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
	/**
	 * 按主题查询表情
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findThemePhiz", method = RequestMethod.GET)
	public ResultVO getThemePhiz(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		PhizVO phiz = new PhizVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			Map<String, Object> data = new HashMap<String, Object>();
			String themeCode=request.getParameter("themeCode");
			
			//得到起始位置和每页条数
			String limit = request.getParameter("limit"); 
			String offset = request.getParameter("offset");
			Integer limitInt = null;
			Integer realOffset = null;
			if(StringUtils.isNotBlank(limit) && StringUtils.isNotBlank(offset)) {
				Integer tempInt = Integer.parseInt(offset);
				limitInt = Integer.parseInt(limit);
				realOffset = limitInt * (tempInt - 1);
			}
			
			
			if(StringUtils.isNotBlank(themeCode)) {
				phiz.setThemeCode(themeCode);
				phiz.setIsValid("1");
				
				//得到起始位置和每页条数
				phiz.setOffset(realOffset);
				phiz.setLimit(limitInt);
				
				List<PhizVO> phizThemeList = phizService.getPhizList(phiz);
				Integer themeCount = phizService.getPhizCount(phiz);
				data.put("phizThemeList", phizThemeList);
				data.put("themeCount", themeCount);
			}else {
				ThemeVO theme = new ThemeVO();
				//得到起始位置和每页条数
				theme.setOffset(realOffset);
				theme.setLimit(limitInt);
				List<ThemeVO> themeList = themeService.getThemeList(theme);
				Integer themeCount = themeService.getThemeCount(theme);
				data.put("themeList", themeList);
				data.put("themeCount", themeCount);
			}
			result.setData(data);
			result.setStandbyParams(getResImgPath());
			result.setNetCode(200);
		} catch (UnsupportedEncodingException e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
	/**
	 * 按推荐查询表情
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findPhiz", method = RequestMethod.GET)
	public ResultVO getPhizInfo(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		PhizVO phiz = new PhizVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			Map<String, Object> data = new HashMap<String, Object>();
			phiz.setIsRecommend("1");
			phiz.setIsValid("1");
			
			//得到起始位置和每页条数
			String limit = request.getParameter("limit"); 
			String offset = request.getParameter("offset");
			Integer limitInt = null;
			Integer realOffset = null;
			if(StringUtils.isNotBlank(limit) && StringUtils.isNotBlank(offset)) {
				Integer tempInt = Integer.parseInt(offset);
				limitInt = Integer.parseInt(limit);
				realOffset = limitInt * (tempInt - 1);
			}
			
			phiz.setOffset(realOffset);
			phiz.setLimit(limitInt);
			
			List<PhizVO> phizList = phizService.getPhizList(phiz);
			Integer Count = phizService.getPhizCount(phiz);
			data.put("phizList", phizList);
			data.put("Count", Count);
			result.setData(data);
			result.setStandbyParams(getResImgPath());
			result.setNetCode(200);
		} catch (UnsupportedEncodingException e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
	
	/**
	 * 得到资源图片路径
	 * @return
	 */
	private Map<String, Object> getResImgPath() {
		Map<String, Object> standbyParams = new HashMap<String, Object>();
		standbyParams.put("nginxPath", CommonUtils.getNginxPath());            //资源访问IP
		standbyParams.put("phizUrl", CommonUtils.getPhizFile());   
		return standbyParams;
	}
	
}
