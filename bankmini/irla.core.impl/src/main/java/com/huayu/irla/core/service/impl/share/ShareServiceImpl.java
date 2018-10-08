package com.huayu.irla.core.service.impl.share;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.dao.share.IShareDao;
import com.huayu.irla.core.service.share.IShareService;
import com.huayu.irla.core.share.vo.ShareVO;
/**
 * @Description 分享统计service实现层
 * @author GuGuangting
 * @date 2018年8月28日 pm4:49:21
 */
@Service
public class ShareServiceImpl implements IShareService {
	
	@Autowired
	private IShareDao shareDao;
	
	@Override
	public void addShare(ShareVO share) {
		shareDao.addShare(share);
	}

	@Override
	public void updateShare(ShareVO share) {
		shareDao.updateShare(share);
	}

	@Override
	public Integer getShareCount(ShareVO share) {
		return shareDao.getShareCount(share);
	}

}
