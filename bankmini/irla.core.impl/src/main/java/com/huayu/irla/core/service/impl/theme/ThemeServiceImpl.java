package com.huayu.irla.core.service.impl.theme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.dao.theme.IThemeDao;
import com.huayu.irla.core.service.theme.IThemeService;
import com.huayu.irla.core.theme.vo.ThemeVO;
/**
 * 主题service接口实现类
 * @author ggt
 *
 */
@Service
public class ThemeServiceImpl implements IThemeService {
	
	@Autowired
	private IThemeDao themeDao;
	@Override
	public List<ThemeVO> getThemeList(ThemeVO theme) {
		return themeDao.getThemeList(theme);
	}

	@Override
	public Integer getThemeCount(ThemeVO theme) {
		return themeDao.getThemeCount(theme);
	}

	@Override
	public Integer addTheme(ThemeVO theme) {
		return themeDao.addTheme(theme);
	}

	@Override
	public Integer updateTheme(ThemeVO theme) {
		return themeDao.updateTheme(theme);
	}

	@Override
	public Integer delTheme(Integer id) {
		return themeDao.delTheme(id);
	}

}
