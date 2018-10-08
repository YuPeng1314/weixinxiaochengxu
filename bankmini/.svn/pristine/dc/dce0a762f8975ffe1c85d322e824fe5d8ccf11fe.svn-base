package com.huayu.irla.manage.dao.categoryManage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.category.vo.CategoryVO;

public interface ICategoryManageDao {
	/**
	 * 
	 * @Title: findCatelog
	 * @Description:查询类别
	 * @return List<CategoryVO>
	 * @author ggt
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CategoryVO> findCategory(@Param("category") CategoryVO category);

	/**
	 * 
	 * @Title: getCategoryCount
	 * @Description: 取得类别数量
	 * @return Integer
	 * @author ggt
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getCategoryCount(@Param("category") CategoryVO category);

	/**
	 * 
	 * @Title: addCatelog
	 * @Description: 添加类别
	 * @return void
	 * @author ggt
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	Integer addCategory(@Param("category")CategoryVO category);

	/**
	 * 
	 * @Title: updateCategory
	 * @Description: 修改类别
	 * @return void
	 * @author ggt
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	Integer updateCategory(@Param("category")CategoryVO category);

	/**
	 * 
	 * @Title: deleteCategory
	 * @Description: 删除类别
	 * @return void
	 * @author ggt
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	Integer deleteCategory(Integer id);
}
