/**
 * @Title: IDataDictDao.java
 * @Package com.huayu.irla.core.dao
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author luozehua
 * @date 2016年10月11日 上午10:29:35
 * @version V1.0
 */

package com.huayu.irla.core.dao.datadict;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.datadict.vo.DataDictVO;

/**
 * @ClassName: IDataDictDao
 * @Description: 数据字典操作
 * @author luozehua
 * @date 2016年10月11日 上午10:29:35
 */

public interface IDataDictDao {

	/**
	 * @Title: getValueByKey
	 * @Description: 根据键取值
	 * @author luozehua
	 * @date 2016年10月11日 上午11:06:05
	 * @param key
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	DataDictVO getValueByKey(String key);

	/**
	 * @Title: getValueByDesc
	 * @Description: 根据描述取值
	 * @author luozehua
	 * @date 2016年10月11日 上午11:06:22
	 * @param desc
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<DataDictVO> getValueByDesc(String desc);

	/**
	 * @Title: getValue
	 * @Description: 根据用户需要的条件取值
	 * @author luozehua
	 * @date 2016年10月11日 上午11:06:44
	 * @param datadict
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<DataDictVO> getValue(@Param("datadict") DataDictVO datadict);

	/**
	 * @Title: updateDataDict
	 * @Description: 更新数据字典
	 * @author luozehua
	 * @date 2016年10月11日 上午11:07:19
	 * @param datadict
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateDataDict(DataDictVO datadict);

	/**
	 * @Title: addDataDict
	 * @Description: 添加数据字典记录
	 * @author luozehua
	 * @date 2016年10月11日 上午11:36:33
	 * @param datadict
	 * @
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addDataDict(DataDictVO datadict);

	/**
	 * 
	 * @Title: getCount
	 * @Description: 数据分页
	 * @author liuwei
	 * @date 2016年10月11日 下午12:26:13
	 * @param datadict
	 * @return @
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getCount(@Param("datadict") DataDictVO datadict);

}
