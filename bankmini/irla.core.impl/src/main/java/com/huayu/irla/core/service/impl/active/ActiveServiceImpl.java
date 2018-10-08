package com.huayu.irla.core.service.impl.active;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.active.vo.ActiveVO;
import com.huayu.irla.core.dao.active.IActiveDao;
import com.huayu.irla.core.service.active.IActiveService;
/**
 * @Description 小程序活动service接口实现类
 * @author 顾广婷
 * @date 2018-08-29
 */
@Service
public class ActiveServiceImpl implements IActiveService {
	
	@Autowired
	private IActiveDao activeDao;
	
	@Override
	public List<ActiveVO> getActiveList(ActiveVO active) {
		return activeDao.getActiveList(active);
	}

	@Override
	public Integer getActiveCount(ActiveVO active) {
		return activeDao.getActiveCount(active);
	}

	@Override
	public void addActive(ActiveVO active) {
		activeDao.addActive(active);
	}

	@Override
	public void updateActive(ActiveVO active) {
		activeDao.updateActive(active);
	}

	@Override
	public void deleteActive(Integer id) {
		activeDao.deleteActive(id);
	}

}
