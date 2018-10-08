package com.huayu.irla.core.service.comment;

import java.util.List;
import com.huayu.irla.core.comment.vo.CommentVO;
/**
 * @Title: findComment
 * @Description:评论service接口
 * @author GuGuangting
 * @date 2018年8月27日 上午9:15:46
 */
public interface ICommentService {
	/**
	 * 
	 * @Title: findComment
	 * @Description:查询评论列表
	 * @author: GuGuangting
	 * @param comment
	 * @return List<CommentVO>
	 * @date 2018年8月27日 上午9:15:46
	 */
	List<CommentVO> findComment(CommentVO comt);
	/**
	 * @Description:查询评论数量
	 * @author: GuGuangting
	 * @param comt
	 * @return 评论数量
	 * @date 2018年8月27日 上午9:15:46
	 */
	Integer getCommentCount(CommentVO comt);
	/**
	 * 
	 * @Title: addComment
	 * @Description:评论新增
	 * @author: GuGuangting
	 * @date 2018年8月27日 上午9:15:46
	 * @param comment
	 */
	Integer addComment(CommentVO comt);
	/**
	 *
	 * @Title: deleteComment
	 * @Description:评论删除
	 * @author: GuGuangting
	 * @date 2018年8月27日 上午9:15:46
	 * @param id
	 */
	void deleteComment(Integer id);
}
