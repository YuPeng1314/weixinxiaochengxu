package com.huayu.irla.core.dao.theme;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.theme.vo.ThemeVO;

/**
 * 
 * @ClassName: IThemeDao
 * @Description: 主题Dao层接口类
 * @author ggt
 * @date 2018-07-26 Copyright: Copyright (c) 2017 Company:华煜网络教育有限公司
 */
public interface IThemeDao {
	/**
	 * 查询主题记录
	 * @return 主题列表
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<ThemeVO> getThemeList(@Param("theme")ThemeVO theme);
	/**
	 * 获取主题数量
	 * @param theme 主题实体类
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getThemeCount(@Param("theme")ThemeVO theme);
	/**
	 * 主题新增
	 * @param theme 主题实体类
	 * @return 
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer addTheme(@Param("theme")ThemeVO theme);
	/**
	 * 主题修改
	 * @param theme 主题实体类
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer updateTheme(@Param("theme")ThemeVO theme);
	/**
	 * 主题删除
	 * @param id 主题记录id
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer delTheme(Integer id);
}
