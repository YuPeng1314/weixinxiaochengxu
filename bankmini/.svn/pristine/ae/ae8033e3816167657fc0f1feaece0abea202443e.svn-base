package com.huayu.irla.core.service.impl.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huayu.irla.core.comment.vo.CommentVO;
import com.huayu.irla.core.dao.comment.ICommentDao;
import com.huayu.irla.core.service.comment.ICommentService;
/**
 * @Description 评论service实现类
 * @author GuGuangting
 *
 */
@Service
public class CommentServiceImpl implements ICommentService {
	
	@Autowired
	private ICommentDao commentDao;
	
	@Override
	public List<CommentVO> findComment(CommentVO comt) {
		return commentDao.findComment(comt);
	}

	@Override
	public Integer addComment(CommentVO comt) {
		return commentDao.addComment(comt);
	}

	@Override
	public void deleteComment(Integer id) {
		commentDao.deleteComment(id);
	}

	@Override
	public Integer getCommentCount(CommentVO comt) {
		return commentDao.getCommentCount(comt);
	}

}
