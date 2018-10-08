package com.huayu.irla.core.service.active;

import java.util.List;

import com.huayu.irla.core.active.vo.ActiveVO;
/**
 * @Description 小程序活动Service层接口
 * @author 顾广婷
 * @data 2018-08-29
 */
public interface IActiveService {
	/**
	 * @Description 查询活动列表
	 * @param active
	 * @return
	 */
	List<ActiveVO> getActiveList(ActiveVO active);
	/**
	 * @Description 查询活动数量
	 * @param active
	 * @return
	 */
	Integer getActiveCount(ActiveVO active);
	/**
	 * @Description 新增活动
	 * @param active
	 */
	void addActive(ActiveVO active);
	/**
	 * @Description 修改活动
	 * @param active
	 */
	void updateActive(ActiveVO active);
	/**
	 * @Description 删除活动
	 * @param id
	 */
	void deleteActive(Integer id);
}
