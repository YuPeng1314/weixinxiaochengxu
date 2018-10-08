package com.huayu.irla.core.service.impl.carousel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.carousel.vo.CarouselVO;
import com.huayu.irla.core.dao.carousel.ICarouselDao;
import com.huayu.irla.core.service.carousel.ICarouselService;

/**
 * 
  * @ClassName: CarouselServiceImpl
  * @Description: 轮播图Service层实现类
  * @author liuwei
  * @date 2018年6月27日 下午4:59:21
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

@Service
public class CarouselServiceImpl implements ICarouselService {
	
	@Autowired
	private ICarouselDao carouselDao;

	@Override
	public List<CarouselVO> findCarousel(CarouselVO crsl) {
		return carouselDao.findCarousel(crsl);
	}

	@Override
	public Integer getCarouselCount(CarouselVO crsl) {
		return carouselDao.getCarouselCount(crsl);
	}

	@Override
	public void addCarousel(CarouselVO crsl) {
		carouselDao.addCarousel(crsl);
	}

	@Override
	public void updateCarousel(CarouselVO crsl) {
		carouselDao.updateCarousel(crsl);
	}

	@Override
	public void deleteCarousel(Integer id) {
		carouselDao.deleteCarousel(id);
	}

	@Override
	public List<CarouselVO> getCarouselValid() {
		return carouselDao.getCarouselValid();
	}

}
