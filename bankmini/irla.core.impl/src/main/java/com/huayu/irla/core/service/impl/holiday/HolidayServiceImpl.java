package com.huayu.irla.core.service.impl.holiday;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.dao.holiday.IHolidayDao;
import com.huayu.irla.core.holiday.vo.HolidayVO;
import com.huayu.irla.core.service.holiday.IHolidayService;
/**
 * 节日服务层接口实现类
 * @author ggt
 *
 */
@Service
public class HolidayServiceImpl implements IHolidayService {
	@Autowired
	private IHolidayDao holidayDao;
	@Override
	public List<HolidayVO> getHolidayList(HolidayVO holiday) {
		return holidayDao.getHolidayList(holiday);
	}

	@Override
	public Integer getHolidayCount(HolidayVO holiday) {
		return holidayDao.getHolidayCount(holiday);
	}

	@Override
	public Integer addHoliday(HolidayVO holiday) {
		return holidayDao.addHoliday(holiday);
	}

	@Override
	public Integer updateHoliday(HolidayVO holiday) {
		return holidayDao.updateHoliday(holiday);
	}

	@Override
	public Integer delHoliday(Integer id) {
		return holidayDao.delHoliday(id);
	}

}
