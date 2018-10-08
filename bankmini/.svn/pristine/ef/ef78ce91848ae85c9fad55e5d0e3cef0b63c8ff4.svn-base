package com.huayu.irla.mobile.active;

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

import com.huayu.irla.core.active.vo.ActiveVO;
import com.huayu.irla.core.base.ResultVO;
import com.huayu.irla.core.service.active.IActiveService;

/**
 * @Description 小程序活动
 * @author 顾广婷
 * @date 2018-08-29
 */
@Controller
@RequestMapping("/activeAction")
public class ActiveAction {
	
	private Logger logger = Logger.getLogger(ActiveAction.class);
	
	@Autowired
	private IActiveService activeService;
	
	@ResponseBody
	@RequestMapping(value = "/activeShow", method = RequestMethod.GET)
	public ResultVO activeShow(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			String limit = request.getParameter("limit"); 
			String offset = request.getParameter("offset");
			String kinds = request.getParameter("kinds");//活动种类0：官方，1：社区
			String activeCode = request.getParameter("activeCode");//活动编码
			String isHot = request.getParameter("isHot");//是否热门0：非热门，1：热门
			//分页查询条件
			Integer limitInt = null;
			Integer realOffset = null;
			if(StringUtils.isNotBlank(limit) && StringUtils.isNotBlank(offset)) {
				Integer tempInt = Integer.parseInt(offset);
				limitInt = Integer.parseInt(limit);
				realOffset = limitInt * (tempInt - 1);
			}
			ActiveVO active = new ActiveVO();
			active.setKinds(kinds);
			active.setIsHot(isHot);
			active.setActiveCode(activeCode);
			active.setOffset(realOffset);
			active.setLimit(limitInt);
			List<ActiveVO> activeList = activeService.getActiveList(active);
			Integer count = activeService.getActiveCount(active);
			Map<String, Object> standbyParams = new HashMap<String, Object>();
			standbyParams.put("count", count);
			result.setNetCode(200);
			result.setStandbyParams(standbyParams);
			result.setData(activeList);
		} catch (UnsupportedEncodingException e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
}
