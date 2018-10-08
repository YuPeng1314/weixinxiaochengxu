package com.huayu.irla.core.dao.phiz;
/**
 * 
 * @ClassName: IPhizDao
 * @Description: 表情包Dao层接口类
 * @author ggt
 * @date 2018-07-24 Copyright: Copyright (c) 2017 Company:华煜网络教育有限公司
 */

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.phiz.vo.PhizVO;

public interface IPhizDao {
	/**
	 * 查询表情记录
	 * @return 表情列表
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<PhizVO> getPhizList(@Param("phiz")PhizVO phiz);
	/**
	 * 获取表情数量
	 * @param phiz 表情实体类
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getPhizCount(@Param("phiz")PhizVO phiz);
	/**
	 * 表情新增
	 * @param phiz 表情实体类
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer addPhiz(@Param("phiz")PhizVO phiz);
	/**
	 * 表情修改
	 * @param phiz 表情实体类
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer updatePhiz(@Param("phiz")PhizVO phiz);
	/**
	 * 表情删除
	 * @param id 表情记录id
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer delPhiz(Integer id);
	
}
