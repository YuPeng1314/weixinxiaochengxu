package com.huayu.irla.manage.service.categoryManage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.irla.core.category.vo.CategoryVO;

/**
 * 类别接口服务类
 * @author GuGuangting
 */
public interface ICategoryManageService {
	/**
	 * 类别查询
	 * @param category
	 * @return
	 */
	List<CategoryVO> findCategory(CategoryVO category);
	/**
	 * 获取数量
	 * @param category
	 * @return
	 */
	Integer getCategoryCount(@Param("category") CategoryVO category);
	/**
	 * 类别新增
	 * @param category
	 */
	Integer addCategory(CategoryVO category);
	/**
	 * 类别修改
	 * @param category
	 */
	Integer updateCategory(CategoryVO category);
	/**
	 * 类别删除
	 * @param id
	 */
	Integer deleteCategory(Integer id);
}
