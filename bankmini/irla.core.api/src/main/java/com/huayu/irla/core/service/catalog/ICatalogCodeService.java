package com.huayu.irla.core.service.catalog;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.huayu.core.util.PageBean;
import com.huayu.irla.core.catalog.vo.CatalogVO;

/**
 * 
 * @ClassName: ICatalogCodeService
 * @Description: 目录层级关系公共端的服务层接口类
 * @author liuwei
 * @date 2017年9月4日 下午4:10:22 Copyright: Copyright (c) 2017 Company:华煜网络教育有限公司
 */

public interface ICatalogCodeService {

	/**
	 * 
	 * @Title: getAllCatalog
	 * @Description: 通过缓存取得所有目录
	 * @return List<CatalogVO>
	 * @author liuwei
	 * @date 2017年9月4日 下午4:06:22
	 */
	List<CatalogVO> getAllCatalog();

	List<CatalogVO> findCatalog(CatalogVO cata);

	// 前段专属目录结构查询（解决排序冲突问题）
	List<CatalogVO> findCatalogExb(CatalogVO cata);

	Integer getCount(CatalogVO cata);

	PageBean<CatalogVO> findCatalogVOByPage(PageBean<CatalogVO> page, CatalogVO cata);

	PageBean<CatalogVO> findCatalogVOByPageExb(PageBean<CatalogVO> page, CatalogVO cata);

	PageBean<CatalogVO> findCatalogVOMobileByPage(PageBean<CatalogVO> page, CatalogVO cata);

	CatalogVO findCatalogByCode(String catalogCode);

	List<CatalogVO> findCatalogByUser(CatalogVO cata);

	List<CatalogVO> findUserCodeCatacode(CatalogVO cata);

	List<CatalogVO> findProfessionCourse(CatalogVO cata);

	/**
	 * 得到目录树
	 * 
	 * @return
	 */
	JSONArray getCatalogTree();


}
