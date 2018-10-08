package com.huayu.irla.core.service.theme;

import java.util.List;
import com.huayu.irla.core.theme.vo.ThemeVO;

/**
 * 主题Service接口
 * @author ggt
 *
 */
public interface IThemeService {
	/**
	 * 查询主题记录
	 * @return 主题列表
	 */
	List<ThemeVO> getThemeList(ThemeVO theme);
	/**
	 * 获取主题数量
	 * @param theme 主题实体类
	 * @return
	 */
	Integer getThemeCount(ThemeVO theme);
	/**
	 * 主题新增
	 * @param theme 主题实体类
	 * @return 
	 */
	Integer addTheme(ThemeVO theme);
	/**
	 * 主题修改
	 * @param theme 主题实体类
	 */
	Integer updateTheme(ThemeVO theme);
	/**
	 * 主题删除
	 * @param id 主题记录id
	 */
	Integer delTheme(Integer id);
}
