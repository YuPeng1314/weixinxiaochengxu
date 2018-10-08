package com.huayu.irla.core.service.impl.phiz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.dao.phiz.IPhizDao;
import com.huayu.irla.core.phiz.vo.PhizVO;
import com.huayu.irla.core.service.phiz.IPhizService;
/**
 * 表情包服务层实现类
 * @author ggt
 *
 */
@Service
public class PhizServiceImpl implements IPhizService {
	@Autowired
	private IPhizDao phizDao;
	@Override
	public List<PhizVO> getPhizList(PhizVO phiz) {
		return phizDao.getPhizList(phiz);
	}

	@Override
	public Integer getPhizCount(PhizVO phiz) {
		return phizDao.getPhizCount(phiz);
	}

	@Override
	public Integer addPhiz(PhizVO phiz) {
		return phizDao.addPhiz(phiz);
	}

	@Override
	public Integer updatePhiz(PhizVO phiz) {
		return phizDao.updatePhiz(phiz);
	}

	@Override
	public Integer delPhiz(Integer id) {
		return phizDao.delPhiz(id);
	}

}
