package com.huayu.irla.core.carousel.vo;

import com.huayu.irla.core.base.BaseVO;

/**
 * 
  * @ClassName: CarouselVO
  * @Description: 轮播图实体类
  * @author liuwei
  * @date 2018年6月27日 上午9:03:49
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

public class CarouselVO extends BaseVO {

	/**
	  * @Fields serialVersionUID : 
	  */
	private static final long serialVersionUID = -7093212266496223330L;
	
	/**
	 * id
	 */
	private String id;

	/**
	 * 轮播标题
	 */
	private String carouselTitle;

	/**
	 * 轮播图片URL
	 */
	private String carouselImgUrl;
	
	/**
	 * 轮播图链接编码（链接的课程编码）
	 */
	private String carouselLinkCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarouselTitle() {
		return carouselTitle;
	}

	public void setCarouselTitle(String carouselTitle) {
		this.carouselTitle = carouselTitle;
	}

	public String getCarouselImgUrl() {
		return carouselImgUrl;
	}

	public void setCarouselImgUrl(String carouselImgUrl) {
		this.carouselImgUrl = carouselImgUrl;
	}

	public String getCarouselLinkCode() {
		return carouselLinkCode;
	}

	public void setCarouselLinkCode(String carouselLinkCode) {
		this.carouselLinkCode = carouselLinkCode;
	}
	
	

}
