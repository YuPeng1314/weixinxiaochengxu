package com.huayu.irla.manage.dao.operationlog;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.operationlog.vo.OperationLogVO;
import com.huayu.irla.privilege.manage.vo.SysLoginoutVO;


/**
 * 记录操作日志实现接口定义
 * @author lanjiagnqi
 * @date 2016/10/19
 * @version 1.0
 * */
public interface IOperationLogDao {
	/**
	 * 查询全部或根据条件操作日志信息
	 * @param operationLogVO 操作日志VO
	 * @return list
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	public List<OperationLogVO> findlogList(@Param("operationLog")OperationLogVO operationLogVO)throws Exception;
	
	/**
	 * 
	  * @Title: addOperaLog
	  * @Description: 添加操作日志
	  * @author liuwei
	  * @date 2017年1月19日 下午4:13:08
	  * @param operationLogVO
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addOperaLog(OperationLogVO operationLogVO) ;

	
    /**
      * 取得数据总数
      * @throws Exception
     */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
    public Integer getCount(@Param("operationLog") OperationLogVO operationLogVO) throws Exception;
    
    /**
     * 
      * @Title: findLoginList
      * @Description: 登录日志的展示
      * @author liuwei
      * @date 2017年1月11日 上午11:35:19
      * @param loginVO
      * @return
     */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
    public List<SysLoginoutVO> findLoginList(@Param("loginVO")SysLoginoutVO loginVO);
    
    /**
     * 
      * @Title: getLoginCount
      * @Description: 取得登录日志的总数
      * @author liuwei
      * @date 2017年1月11日 上午11:35:38
      * @param loginVO
      * @return
     */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
    public Integer getLoginCount(@Param("loginVO") SysLoginoutVO loginVO);
    
    
    
}
