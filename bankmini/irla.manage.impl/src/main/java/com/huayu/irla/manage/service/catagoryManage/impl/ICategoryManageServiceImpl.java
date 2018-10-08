package com.huayu.irla.manage.service.catagoryManage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huayu.irla.core.category.vo.CategoryVO;
import com.huayu.irla.manage.dao.categoryManage.ICategoryManageDao;
import com.huayu.irla.manage.service.categoryManage.ICategoryManageService;


@Service
@Transactional
public class ICategoryManageServiceImpl implements ICategoryManageService {
	@Autowired
	private ICategoryManageDao categoryManageDao;

	@Override
	public List<CategoryVO> findCategory(CategoryVO category) {
		return categoryManageDao.findCategory(category);
	}

	@Override
	public Integer getCategoryCount(CategoryVO category) {
		return categoryManageDao.getCategoryCount(category);
	}

	@Override
	public Integer addCategory(CategoryVO category) {
		return categoryManageDao.addCategory(category);
	}

	@Override
	public Integer updateCategory(CategoryVO category) {
		return categoryManageDao.updateCategory(category);
	}

	@Override
	public Integer deleteCategory(Integer id) {
		return categoryManageDao.deleteCategory(id);
	}

	
	

}
