/**
 * @Title: DataDricCommon.java
 * @Package com.huayu.core.util
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年12月19日 上午11:27:27
 * @version V1.0
 */

package com.huayu.irla.core.service.impl.datadict;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huayu.irla.core.datadict.vo.DataDictVO;
import com.huayu.irla.core.service.datadict.IDataDictService;

/**
  * @ClassName: DataDricCommon
  * @Description: 数据字典工具类
  * @author luozehua
  * @date 2016年12月19日 上午11:27:27
  *
  */
@Component
public class DataDricCommon {

    @Autowired
    private IDataDictService dataDictServiceImpl;

    /**
     * 数据字典
     */
    private static DataDricCommon dataDirc;

    @PostConstruct
    public void init() {
        DataDricCommon.setDataDirc(this);
        dataDirc.dataDictServiceImpl = this.dataDictServiceImpl;
    }

    public static void setDataDirc(DataDricCommon dataDirc) {
        DataDricCommon.dataDirc = dataDirc;
    }

    /**
      * @Title: getValueByKey
      * @Description: 通过键找值
      * @author luozehua
      * @date 2016年12月19日 下午2:27:02
      * @param key 目标键
      * @param defaultValue 默认值
      * @return
     */
    public static String getValueByKey(String key, String defaultValue) {
        List<String> tmpList = getValueList(key);
        return CollectionUtils.isEmpty(tmpList) ? defaultValue : tmpList.get(0);
    }

    public static String getValueByKey(String key) {
        List<String> tmpList = getValueList(key);
        return CollectionUtils.isEmpty(tmpList) ? "" : tmpList.get(0);
    }

    public static List<String> getValueList(String key) {
        String value = dataDirc.dataDictServiceImpl.getValueByKey(key);
        if (!StringUtils.isBlank(value)) {
            String[] tmpList = value.trim().split("\\s*;\\s*");
            return Arrays.asList(tmpList);
        }
        return null;
    }

    /**
     * @Title: getValueByDesc
     * @Description:  根据描述取值
     * @author luozehua
     * @date 2016年10月11日 上午11:06:22
     * @param desc
     * @return
     * @
    */
    public static List<DataDictVO> getValueByDesc(String desc) {
        return dataDirc.dataDictServiceImpl.getValueByDesc(desc);
    }

    /**
     * @Title: getValue
     * @Description: 根据用户需要的条件取值
     * @author luozehua
     * @date 2016年10月11日 上午11:06:44
     * @param datadict
     * @return
     * @
    */
    public static List<DataDictVO> getValue(DataDictVO datadict) {
        return dataDirc.dataDictServiceImpl.getValue(datadict);
    }

}
