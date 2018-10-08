package com.huayu.irla.core.dao.category;

import java.util.List;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.category.vo.CategoryVO;

/**
 * 
  * @ClassName: ICategoryDao
  * @Description: 目录类别课程前端dao层接口类
  * @author liuwei
  * @date 2018年6月27日 下午4:37:35
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

public interface ICategoryDao {
	
	/**
	  * @Title: findCategoryCore
	  * @Description: 查询目录类别（公共端）
	  * @author liuwei
	  * @date 2018年6月27日 下午4:48:41
	 */
	
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CategoryVO> findCategoryCore();

}
