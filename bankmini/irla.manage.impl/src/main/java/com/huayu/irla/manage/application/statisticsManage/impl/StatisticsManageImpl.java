package com.huayu.irla.manage.application.statisticsManage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.service.statistics.IStatisticsService;
import com.huayu.irla.core.statistics.vo.StatisticsVO;
import com.huayu.irla.manage.application.statistics.IStatisticsManage;
/**
 * @Description 资源、课程数据统计权限实现类
 * @author 顾广婷
 * @date 2018-08-30
 */
@Component("statisticsManage")
public class StatisticsManageImpl implements IStatisticsManage {
	
	@Autowired
	private IStatisticsService statisticsService;
	
	@Override
	public JSONObject getResStatistics(StatisticsVO statistics) {
		if(statistics==null) {
			statistics=new StatisticsVO();
		}
		List<StatisticsVO> statisticsList = statisticsService.getResStatisticsList(statistics);
		Integer count=statisticsService.getResStatisticsCount(statistics);
		JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(statisticsList));
        result.put("total", count);
        return result;
	}

	@Override
	public JSONObject getCourseStatistics(StatisticsVO statistics) {
		if(statistics==null) {
			statistics=new StatisticsVO();
		}
		List<StatisticsVO> statisticsList = statisticsService.getCourseStatisticsList(statistics);
		Integer count=statisticsService.getCourseStatisticsCount(statistics);
		JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(statisticsList));
        result.put("total", count);
        return result;
	}

}
