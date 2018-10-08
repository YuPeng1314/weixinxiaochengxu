package com.huayu.irla.mobile.home;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huayu.irla.core.base.ResultVO;
import com.huayu.irla.core.carousel.vo.CarouselVO;
import com.huayu.irla.core.service.carousel.ICarouselService;
import com.huayu.irla.mobile.utils.CommonUtils;

/**
 * 
  * @ClassName: MobileHome
  * @Description: 小程序首页
  * @author liuwei
  * @date 2018年6月27日 上午10:41:59
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

@Controller
@RequestMapping("/mobileHome")
public class MobileHome {
	
	private static final Logger logger = Logger.getLogger(MobileHome.class);
	
	@Autowired
	private ICarouselService carouselServiceImpl;
	
	/**
	 * 
	  * @Title: carouselShow
	  * @Description: 首页轮播图
	  * @return ResultVO
	  * @author liuwei
	  * @date 2018年6月27日 上午11:08:16
	 */
	
	@ResponseBody
	@RequestMapping(value = "/carouselShow", method = RequestMethod.GET)
	public ResultVO carouselShow(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
			try {
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=utf-8");
				List<CarouselVO> caslList = carouselServiceImpl.getCarouselValid();
				Map<String, Object> standbyParams = new HashMap<String, Object>();
				standbyParams.put("nginxPath", CommonUtils.getNginxPath());
				standbyParams.put("resUrl", CommonUtils.getResFile());
				result.setNetCode(200);
				result.setStandbyParams(standbyParams);
				result.setData(caslList);
			} catch (UnsupportedEncodingException e) {
				result.setNetCode(500);
				logger.error(e);
			}
			return result;
	}
		
			

}
