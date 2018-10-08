package com.huayu.irla.core.dao.comment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.comment.vo.CommentVO;

/**
 * 
 * @ClassName: ICommentDao
 * @Description: 评论dao层接口类
 * @author GuGuangting
 * @date 2018年8月27日 上午9:12:43
 * Copyright: Copyright (c) 2018
 * Company:华煜网络教育有限公司
 */
public interface ICommentDao {
	/**
	 * 
	 * @Title: findComment
	 * @Description:查询评论列表
	 * @author: GuGuangting
	 * @param comment
	 * @return List<CommentVO>
	 * @date 2018年8月27日 上午9:15:46
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CommentVO> findComment(@Param("comt") CommentVO comt);
	/**
	 * @Description:查询评论数量
	 * @author: GuGuangting
	 * @param comt
	 * @return 评论数量
	 * @date 2018年8月27日 上午9:15:46
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getCommentCount(@Param("comt") CommentVO comt);
	/**
	 * 
	 * @Title: addComment
	 * @Description:评论新增
	 * @author: GuGuangting
	 * @date 2018年8月27日 上午9:15:46
	 * @param comment
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer addComment(@Param("comt") CommentVO comt);
	/**
	 *
	 * @Title: deleteComment
	 * @Description:评论删除
	 * @author: GuGuangting
	 * @date 2018年8月27日 上午9:15:46
	 * @param id
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	void deleteComment(Integer id);
}
