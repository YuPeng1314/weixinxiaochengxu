package com.huayu.irla.manage.service.operationlog;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.irla.core.operationlog.vo.OperationLogVO;
import com.huayu.irla.privilege.manage.vo.SysLoginoutVO;

/**
 * 操作日志具体逻辑实现类
 * @author lanjiangqi
 * @date 2016/10/19
 * @version 1.0
 * */
public interface IOperationLogService{

	/**
	 * 查询全部或根据条件操作日志信息
	 * @param operationLogVO 操作日志VO
	 * @return list
	 */
	public List<OperationLogVO> findlogList(OperationLogVO operationLogVO)throws Exception;
	
	/**
	 * 
	  * @Title: addOperaLog
	  * @Description: 添加操作日志
	  * @author liuwei
	  * @date 2017年1月19日 下午4:13:08
	  * @param operationLogVO
	 */
	void addOperaLog(OperationLogVO operationLogVO) ;

	
	/**
	 * 获取总记录数
	 * */
    public Integer getCount(OperationLogVO operationLogVO) throws Exception;
    
    
    /**
     * 
      * @Title: findLoginList
      * @Description: 登录日志的展示
      * @author liuwei
      * @date 2017年1月11日 上午11:35:19
      * @param loginVO
      * @return
     */
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
    public Integer getLoginCount(@Param("loginVO") SysLoginoutVO loginVO);
    

}
