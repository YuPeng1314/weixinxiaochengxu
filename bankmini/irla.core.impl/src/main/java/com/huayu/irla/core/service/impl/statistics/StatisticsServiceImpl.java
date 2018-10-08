package com.huayu.irla.core.service.impl.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.dao.statistics.IStatisticsDao;
import com.huayu.irla.core.service.statistics.IStatisticsService;
import com.huayu.irla.core.statistics.vo.StatisticsVO;
/**
 * @Description 小程序后台统计service层接口实现类
 * @author 顾广婷
 * @date 2018-08-30
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
	
	@Autowired
	private IStatisticsDao statisticsDao;

	@Override
	public List<StatisticsVO> getResStatisticsList(StatisticsVO statistics) {
		return statisticsDao.getResStatisticsList(statistics);
	}

	@Override
	public List<StatisticsVO> getCourseStatisticsList(StatisticsVO statistics) {
		return statisticsDao.getCourseStatisticsList(statistics);
	}

	@Override
	public Integer getResStatisticsCount(StatisticsVO statistics) {
		return statisticsDao.getResStatisticsCount(statistics);
	}

	@Override
	public Integer getCourseStatisticsCount(StatisticsVO statistics) {
		return statisticsDao.getCourseStatisticsCount(statistics);
	}
	
	

}
