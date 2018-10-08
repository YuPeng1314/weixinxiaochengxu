/**
 * @Title: DataDictServiceImpl.java
 * @Package com.huayu.irla.core.service.impl
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年10月11日 上午10:43:41
 * @version V1.0
 */

package com.huayu.irla.core.service.impl.datadict;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.huayu.irla.core.dao.datadict.IDataDictDao;
import com.huayu.irla.core.datadict.vo.DataDictVO;
import com.huayu.irla.core.service.datadict.IDataDictService;

/**
  * @ClassName: DataDictServiceImpl
  * @Description: TODO
  * @author luozehua
  * @date 2016年10月11日 上午10:43:41
  *
  */
@Service("dataDictServiceImpl")
public class DataDictServiceImpl implements IDataDictService {

    @Autowired
    private IDataDictDao dataDictDao;

    @Override
    @Cacheable(value = "dataDictCache")
    public String getValueByKey(String key) {
        //改造：状态为失效的，则不予返回结果。@luozehua 2016-11-14 
        DataDictVO temp = dataDictDao.getValueByKey(key);
        if (temp == null) {
            return StringUtils.EMPTY;
        }
        if ("1".equals(temp.getIsValid())) {
            return temp.getDictionaryValue();
        }
        return StringUtils.EMPTY;
    }

    @Override
    @Cacheable(value = "dataDictCache")
    public List<DataDictVO> getValueByDesc(String desc) {
        return dataDictDao.getValueByDesc(desc);
    }

    @Override
    @Cacheable(value = "dataDictCache")
    public List<DataDictVO> getValue(DataDictVO datadict) {
        return dataDictDao.getValue(datadict);
    }

    @Override
    @CacheEvict(value = "dataDictCache", allEntries = true)
    public void updateDataDict(DataDictVO datadict) {
        dataDictDao.updateDataDict(datadict);
    }

    @Override
    @CacheEvict(value = "dataDictCache", allEntries = true)
    public void addDataDict(DataDictVO datadict) {
        dataDictDao.addDataDict(datadict);
    }

    @Override
    public Integer getCount(DataDictVO datadict) {
        return dataDictDao.getCount(datadict);
    }

}
