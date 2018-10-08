package com.huayu.irla.core.service.impl.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.category.vo.CategoryVO;
import com.huayu.irla.core.dao.category.ICategoryDao;
import com.huayu.irla.core.service.category.ICategoryService;

/**
 * 
  * @ClassName: CategoryServiceImpl
  * @Description: 目录类别service层实现类
  * @author liuwei
  * @date 2018年6月27日 下午4:59:14
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

@Service
public class CategoryServiceImpl implements ICategoryService {
	
	@Autowired
	private ICategoryDao categoryDao;
	
	@Override
	public List<CategoryVO> findCategoryCore() {
		return categoryDao.findCategoryCore();
	}

}
