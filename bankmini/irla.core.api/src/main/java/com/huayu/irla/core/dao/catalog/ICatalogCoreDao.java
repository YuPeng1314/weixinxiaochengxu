package com.huayu.irla.core.dao.catalog;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.catalog.vo.CatalogVO;

/**
 * 
 * @ClassName: ICatalogCoreDao
 * @Description: 目录层级关系公共端的Dao层接口类
 * @author liuwei
 * @date 2017年9月4日 下午3:59:40 Copyright: Copyright (c) 2017 Company:华煜网络教育有限公司
 */

public interface ICatalogCoreDao {

	/**
	 * 
	 * @Title: getAllCatalog
	 * @Description: 通过缓存取得所有目录
	 * @return List<CatalogVO>
	 * @author liuwei
	 * @date 2017年9月4日 下午4:06:22
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CatalogVO> getAllCatalog();
	
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CatalogVO> getAllCourseList(@Param("cit") CatalogVO cata);

	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CatalogVO> findCatalog(@Param("cata") CatalogVO cata);
	
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CatalogVO> findCatalogExb(@Param("cata") CatalogVO cata);

	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	CatalogVO findCatalogByCode(String catalogCode);

	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getCount(@Param("cata") CatalogVO cata);
	
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CatalogVO> findCatalogByUser(@Param("cata") CatalogVO cata);
	
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CatalogVO> findUserCodeCatacode(@Param("cata") CatalogVO cata);

	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CatalogVO> findProfessionCourse(@Param("cata") CatalogVO cata);
	
}
