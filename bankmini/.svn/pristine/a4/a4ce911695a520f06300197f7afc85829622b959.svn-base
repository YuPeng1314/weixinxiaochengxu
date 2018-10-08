/**
 * 
 */
package com.huayu.irla.core.service.impl.catalog;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.core.util.PageBean;
import com.huayu.irla.core.catalog.vo.CatalogVO;
import com.huayu.irla.core.dao.catalog.ICatalogCoreDao;
import com.huayu.irla.core.service.catalog.ICatalogCodeService;

/**
 * @author luozehua
 * @date 2017年9月5日 - 下午2:56:07
 * 
 */
@Service
public class CatalogCodeServiceImpl implements ICatalogCodeService {

	@Autowired
	private ICatalogCoreDao catalogCoreDao;

	@Override
	public List<CatalogVO> getAllCatalog() {
		return catalogCoreDao.getAllCatalog();
	}

	@Override
	public List<CatalogVO> findCatalog(CatalogVO cata) {
		return catalogCoreDao.findCatalog(cata);
	}

	@Override
	public List<CatalogVO> findCatalogExb(CatalogVO cata) {
		return catalogCoreDao.findCatalogExb(cata);
	}

	@Override
	public Integer getCount(CatalogVO cata) {
		return catalogCoreDao.getCount(cata);
	}

	@Override
	public PageBean<CatalogVO> findCatalogVOByPage(PageBean<CatalogVO> page, CatalogVO cata) {
		Integer count = catalogCoreDao.getCount(cata);
		page.setTotalCount(count);
		cata.setOffset(page.getFirstResult());
		cata.setLimit(page.getMaxResult());
		List<CatalogVO> result = catalogCoreDao.findCatalog(cata);
		page.setList(result);
		return page;
	}

	@Override
	public PageBean<CatalogVO> findCatalogVOByPageExb(PageBean<CatalogVO> page, CatalogVO cata) {
		Integer count = catalogCoreDao.getCount(cata);
		page.setTotalCount(count);
		cata.setOffset(page.getFirstResult());
		cata.setLimit(page.getMaxResult());
		List<CatalogVO> result = catalogCoreDao.findCatalogExb(cata);
		page.setList(result);
		return page;
	}

	@Override
	public PageBean<CatalogVO> findCatalogVOMobileByPage(PageBean<CatalogVO> page, CatalogVO cata) {
		Integer count = catalogCoreDao.getCount(cata);
		page.setTotalCount(count);
		List<CatalogVO> result = catalogCoreDao.findCatalog(cata);
		page.setList(result);
		return page;
	}

	@Override
	public CatalogVO findCatalogByCode(String catalogCode) {
		return catalogCoreDao.findCatalogByCode(catalogCode);
	}

	@Override
	public List<CatalogVO> findCatalogByUser(CatalogVO cata) {
		return catalogCoreDao.findCatalogByUser(cata);
	}

	/**
	 * 得到目录树
	 * 
	 * @return
	 */
	@Override
	public JSONArray getCatalogTree() {
		List<CatalogVO> catalogList = catalogCoreDao.getAllCatalog();
		if (CollectionUtils.isNotEmpty(catalogList)) {
			JSONObject root = new JSONObject();
			// 根目录为A
			initTree(catalogList, root, "-1");
			String tempResult = root.getString("nodes");
			tempResult = tempResult.replaceAll("catalogName", "text");
			return JSONArray.parseArray(tempResult);
		} else {
			return null;
		}
	}
	
	

	/**
	 * 递归算法，生成一颗树的josn串
	 * 
	 * @param params
	 * @param root
	 * @param parentCode
	 */
	private void initTree(List<CatalogVO> params, JSONObject root, String parentCode) {
		// 循环整个目录
		for (CatalogVO cata : params) {
			// 得到对应的目录，如果父目录都是等于传过来的父code,放入当前对应的root中
			String parentDepaCode = cata.getParentCatalogCode();
			if (StringUtils.equals(parentDepaCode, parentCode)) {
				// 放入目录的nodes中
				JSONArray children = root.getJSONArray("nodes");

				if (children == null) {
					children = new JSONArray();
				}
				// 得到当前目录的json值
				JSONObject json = (JSONObject) JSONObject.toJSON(cata);
				children.add(json);
				root.put("nodes", children);

				String depaCode = cata.getCatalogCode();
				String catalogLevel = cata.getCatalogLevel();
				if(!"3".equals(catalogLevel)) {
					initTree(params, json, depaCode);
				}
			}
		}
	}

	@Override
	public List<CatalogVO> findUserCodeCatacode(CatalogVO cata) {
		return catalogCoreDao.findUserCodeCatacode(cata);
	}

	@Override
	public List<CatalogVO> findProfessionCourse(CatalogVO cata) {
		return catalogCoreDao.findProfessionCourse(cata);
	}

}
