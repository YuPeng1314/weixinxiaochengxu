package com.huayu.irla.manage.service.operationlog.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huayu.irla.core.operationlog.vo.OperationLogVO;
import com.huayu.irla.manage.dao.operationlog.IOperationLogDao;
import com.huayu.irla.manage.service.operationlog.IOperationLogService;
import com.huayu.irla.privilege.manage.vo.SysLoginoutVO;


/**
 * 操作日志具体实现类
 * @author lanjiangqi
 * @date 2016/10/19
 * @version 1.0
 * */
@Service
@Transactional
public class OperationLogServiceImpl implements IOperationLogService{

	   private static final Logger logger = Logger.getLogger(OperationLogServiceImpl.class);
	@Autowired
	private IOperationLogDao operationLogDao;
	
	@Override
	public List<OperationLogVO> findlogList(OperationLogVO operationLogVO) throws Exception {
		try {
			return operationLogDao.findlogList(operationLogVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询操作日志失败");
		}
		return null;
	}


	@Override
	public Integer getCount(OperationLogVO operationLogVO) throws Exception {
		try {
			return operationLogDao.getCount(operationLogVO);
		} catch (Exception e) {
			logger.error("查询操作日志失败");
		}
		return 0;
	}

	@Override
	public List<SysLoginoutVO> findLoginList(SysLoginoutVO loginVO) {
		return operationLogDao.findLoginList(loginVO);
	}

	@Override
	public Integer getLoginCount(SysLoginoutVO loginVO) {
		return operationLogDao.getLoginCount(loginVO);
	}

	@Override
	public void addOperaLog(OperationLogVO operationLogVO) {
		operationLogDao.addOperaLog(operationLogVO);
		
	}
}
