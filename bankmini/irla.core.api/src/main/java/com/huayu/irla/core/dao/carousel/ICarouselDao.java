package com.huayu.irla.core.dao.carousel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.carousel.vo.CarouselVO;

/**
 * 
  * @ClassName: ICarouselDao
  * @Description: 轮播dao层接口类
  * @author liuwei
  * @date 2018年6月27日 上午9:12:43
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

public interface ICarouselDao {

	/**
	 * 
	  * @Title: findCarousel
	  * @Description: 查询轮播信息
	  * @return List<CarouselVO>
	  * @author liuwei
	  * @date 2018年6月27日 上午9:14:55
	 */
	
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CarouselVO> findCarousel(@Param("crsl") CarouselVO crsl);

	/**
	 * 
	  * @Title: getCarouselCount
	  * @Description: 获取轮播数量
	  * @return Integer
	  * @author liuwei
	  * @date 2018年6月27日 上午9:15:12
	 */
	
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getCarouselCount(@Param("crsl") CarouselVO crsl);

	/**
	 * 
	  * @Title: addCarousel
	  * @Description: 添加轮播信息
	  * @return void
	  * @author liuwei
	  * @date 2018年6月27日 上午9:15:24
	 */
	
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addCarousel(CarouselVO crsl);

	/**
	 * 
	  * @Title: updateCarousel
	  * @Description: 修改轮播信息
	  * @return void
	  * @author liuwei
	  * @date 2018年6月27日 上午9:15:34
	 */
	
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateCarousel(CarouselVO crsl);

	/**
	 * 
	  * @Title: deleteCarousel
	  * @Description: 删除轮播信息
	  * @return void
	  * @author liuwei
	  * @date 2018年6月27日 上午9:15:46
	 */
	
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteCarousel(Integer id);
	
	/**
	 * 
	  * @Title: getCarouselValid
	  * @Description: 获取有效的轮播图
	  * @return List<CarouselVO>
	  * @author liuwei
	  * @date 2018年6月27日 上午10:56:10
	 */
	
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	List<CarouselVO> getCarouselValid();
	
}
