package com.huayu.irla.mobile.comment;

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
import com.huayu.irla.core.comment.vo.CommentVO;
import com.huayu.irla.core.service.comment.ICommentService;


/**
 * 
  * @ClassName: CommentAction
  * @Description: 小程序分类
  * @author GuGuangting
  * @date 2018年8月27日 下午5:00:35
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

@Controller
@RequestMapping("/CommentAction")
public class CommentAction {
	private Logger logger = Logger.getLogger(CommentAction.class);
	@Autowired
	private ICommentService commentService;
	
	
	@ResponseBody
	@RequestMapping(value = "/commentShow", method = RequestMethod.GET)
	public ResultVO commentShow(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			String limit = request.getParameter("limit"); 
			String offset = request.getParameter("offset");
			String resourseCode = request.getParameter("resourseCode"); //资源编码
			
			//分页查询条件
			Integer limitInt = null;
			Integer realOffset = null;
			if(StringUtils.isNotBlank(limit) && StringUtils.isNotBlank(offset)) {
				Integer tempInt = Integer.parseInt(offset);
				limitInt = Integer.parseInt(limit);
				realOffset = limitInt * (tempInt - 1);
			}
			//评论
			CommentVO comt = new CommentVO();
			comt.setOffset(realOffset);
			comt.setLimit(limitInt);
			comt.setResourseCode(resourseCode);
			List<CommentVO> commentList = commentService.findComment(comt);
			Integer count = commentService.getCommentCount(comt);
			Map<String, Object> standbyParams = new HashMap<String, Object>();
			standbyParams.put("count", count);
			result.setNetCode(200);
			result.setStandbyParams(standbyParams);
			result.setData(commentList);
		} catch (Exception e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/addComment", method = RequestMethod.GET)
	public ResultVO addComment(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			String resourseCode = request.getParameter("resourseCode"); //资源编码
			String userCode = request.getParameter("userCode");//用户编码
			String comment = request.getParameter("comment");//评论内容
			String photo = request.getParameter("photo");//用户头像
			//评论
			CommentVO comt = new CommentVO();
			comt.setResourseCode(resourseCode);
			comt.setUserCode(userCode);
			comt.setComment(comment);
			comt.setPhoto(photo);
			Integer num = commentService.addComment(comt);
			result.setData(num);
		} catch (Exception e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
	
	
}
