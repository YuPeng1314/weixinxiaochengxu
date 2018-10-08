/**
 * @Title: IDataDictService.java
 * @Package com.huayu.irla.core.service
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年10月11日 上午10:37:31
 * @version V1.0
 */

package com.huayu.irla.core.service.datadict;

import java.util.List;

import com.huayu.irla.core.datadict.vo.DataDictVO;

/**
  * @ClassName: IDataDictService
  * @Description: TODO
  * @author luozehua
  * @date 2016年10月11日 上午10:37:31
  *
  */

public interface IDataDictService {

    /**
     * @Title: getValueByKey
     * @Description: 根据键取值
     * @author luozehua
     * @date 2016年10月11日 上午11:06:05
     * @param key
     * @return
     * @
    */
    String getValueByKey(String key);

    /**
     * @Title: getValueByDesc
     * @Description:  根据描述取值
     * @author luozehua
     * @date 2016年10月11日 上午11:06:22
     * @param desc
     * @return
     * @
    */
    List<DataDictVO> getValueByDesc(String desc);

    /**
     * @Title: getValue
     * @Description: 根据用户需要的条件取值
     * @author luozehua
     * @date 2016年10月11日 上午11:06:44
     * @param datadict
     * @return
     * @
    */
    List<DataDictVO> getValue(DataDictVO datadict);

    /**
     * @Title: updateDataDict
     * @Description: 更新数据字典
     * @author luozehua
     * @date 2016年10月11日 上午11:07:19
     * @param datadict
     * @
    */
    void updateDataDict(DataDictVO datadict);

    /**
     * @Title: addDataDict
     * @Description: 添加数据字典记录
     * @author luozehua
     * @date 2016年10月11日 上午11:36:33
     * @param datadict
     * @
    */
    void addDataDict(DataDictVO datadict);

    /**
     * 
      * @Title: getCount
      * @Description: 数据分页
      * @author liuwei
      * @date 2016年10月11日 下午12:28:29
      * @param datadict
      * @return
      * @
     */
    Integer getCount(DataDictVO datadict);
}
