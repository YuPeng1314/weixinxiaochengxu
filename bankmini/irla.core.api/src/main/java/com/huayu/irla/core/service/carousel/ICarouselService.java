package com.huayu.irla.core.service.carousel;

import java.util.List;

import com.huayu.irla.core.carousel.vo.CarouselVO;

/**
 * 
  * @ClassName: ICarouselService
  * @Description: 轮播service层接口类
  * @author liuwei
  * @date 2018年6月27日 上午9:20:31
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

public interface ICarouselService {

	/**
	 * 
	  * @Title: findCarousel
	  * @Description: 查询轮播信息
	  * @return List<CarouselVO>
	  * @author liuwei
	  * @date 2018年6月27日 上午9:14:55
	 */
	
	List<CarouselVO> findCarousel(CarouselVO crsl);

	/**
	 * 
	  * @Title: getCarouselCount
	  * @Description: 获取轮播数量
	  * @return Integer
	  * @author liuwei
	  * @date 2018年6月27日 上午9:15:12
	 */
	
	Integer getCarouselCount(CarouselVO crsl);

	/**
	 * 
	  * @Title: addCarousel
	  * @Description: 添加轮播信息
	  * @return void
	  * @author liuwei
	  * @date 2018年6月27日 上午9:15:24
	 */
	
	void addCarousel(CarouselVO crsl);

	/**
	 * 
	  * @Title: updateCarousel
	  * @Description: 修改轮播信息
	  * @return void
	  * @author liuwei
	  * @date 2018年6月27日 上午9:15:34
	 */
	
	void updateCarousel(CarouselVO crsl);

	/**
	 * 
	  * @Title: deleteCarousel
	  * @Description: 删除轮播信息
	  * @return void
	  * @author liuwei
	  * @date 2018年6月27日 上午9:15:46
	 */
	
	void deleteCarousel(Integer id);
	
	/**
	 * 
	  * @Title: getCarouselValid
	  * @Description: 获取有效的轮播图
	  * @return List<CarouselVO>
	  * @author liuwei
	  * @date 2018年6月27日 上午11:00:59
	 */
	
	List<CarouselVO> getCarouselValid();
	
}
