package com.huayu.irla.manage.application.comment.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.comment.vo.CommentVO;
import com.huayu.irla.core.service.comment.ICommentService;
import com.huayu.irla.manage.application.commentManage.ICommentManage;

/**
 * @Description:权限接口实现类
 * @author GuGuangTing
 *
 */
@Component("commentManage")
public class CommentManageImpl implements ICommentManage{
	private static final Logger logger = Logger.getLogger(CommentManageImpl.class);
	@Autowired
	private ICommentService commentService;
	@Override
	public JSONObject findComment(CommentVO comt) {
		JSONObject result = new JSONObject();
		try {
			if(comt==null) {
				comt=new CommentVO();
			}
			List<CommentVO> commentList = commentService.findComment(comt);
			Integer count = commentService.getCommentCount(comt);
			result.put("rows", JSONArray.toJSON(commentList));
			result.put("total", count);
		} catch (Exception e) {
			logger.error(e);
		}
        return result;
	}
	@Override
	public Integer deleteComment(Integer id) {
		if(id==null) {
			return 1;
		}
		commentService.deleteComment(id);
		return 0;
	}
	
}
