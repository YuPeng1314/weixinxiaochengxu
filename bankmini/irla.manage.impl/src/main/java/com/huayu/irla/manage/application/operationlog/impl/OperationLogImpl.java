package com.huayu.irla.manage.application.operationlog.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.core.util.EnumConstants;
import com.huayu.core.util.RemoteShellUtils;
import com.huayu.irla.core.operationlog.vo.OperationLogVO;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.manage.application.operationlog.IOperationLog;
import com.huayu.irla.manage.service.operationlog.IOperationLogService;
import com.huayu.irla.privilege.manage.vo.SysLoginoutVO;

@Component("operatinLogManage")
public class OperationLogImpl implements IOperationLog{
	private static final Logger logger = Logger.getLogger(OperationLogImpl.class);
	
	@Autowired
	private IOperationLogService operationLogServiceImpl;
	
	/**
	 * redis对象
	 */
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	
	@Override
	public JSONObject findlogList(OperationLogVO operationLogVO) {
		List<OperationLogVO> logList;
		try {
			if(operationLogVO==null){
				operationLogVO = new OperationLogVO();
			}
			//取得数据信息
			logList = operationLogServiceImpl.findlogList(operationLogVO);
			//取得所有信息记录总数
			 Integer count = operationLogServiceImpl.getCount(operationLogVO);
			//取得数据记录总数
			JSONObject result = new JSONObject();
			result.put("rows", JSONArray.toJSON(logList));
			result.put("total", count);
			return result;
		} catch (Exception e) {
			logger.error("查询失败", e);
		}
		return null;
	}
	



	@Override
	public JSONObject findLoginList(SysLoginoutVO loginVO) {
		List<SysLoginoutVO> loginList = Collections.emptyList();
		if(loginVO==null){
			loginVO = new SysLoginoutVO();
		}
		//取得所有数据信息
		loginList = operationLogServiceImpl.findLoginList(loginVO);
		//取得所有数据记录总数
		Integer count = operationLogServiceImpl.getLoginCount(loginVO);
		JSONObject result = new JSONObject();
		result.put("rows", JSONArray.toJSON(loginList));
		result.put("total", count);
		return result ;
	
     }


	@Override
	public void reStartAccessLog() throws Exception {
		// 删除服务器文件
		RemoteShellUtils remoteObj = RemoteShellUtils.getInstance(getCacheValue(EnumConstants.SERVICE_IP), getCacheValue(EnumConstants.SERVICE_USERNAME), getCacheValue(EnumConstants.SERVICE_PAS), EnumConstants.IETL_CODE_UTF8);
					
		//命令行
		List<String> commandList = new ArrayList<String>();
		//goaccess -a -d -f  /usr/local/nginx/logs/access.log -p /etc/goaccess.conf -o /fpb/goAccessLog/ngx_report.html  --real-time-html --daemonize
		//得到日志路径
		String logPath = DataDricCommon.getValueByKey("accesslog.path", "/usr/local/nginx/logs/access.log");
		commandList.add("goaccess -a -d -f");
		commandList.add(logPath);
		commandList.add("-p");
		commandList.add("/etc/goaccess.conf");
		commandList.add("-o");
		commandList.add("/fpb/goAccessLog/ngx_report.html");
		commandList.add("--real-time-html --daemonize");
		
		//kill掉原有id
		String goAccessId = stringRedisTemplate.opsForValue().get("goAccessPid");
		System.err.println("pid:"+goAccessId);
		if(StringUtils.isNotBlank(goAccessId)) {
			List<String> killPid = new ArrayList<String>();
			killPid.add("kill -9");
			killPid.add(goAccessId);
			
			String[] strings = new String[killPid.size()];
			killPid.toArray(strings);
			remoteObj.exec(strings);
			
			//删除id
			stringRedisTemplate.delete("goAccessPid");
		}
		 
		String[] strings = new String[commandList.size()];
		commandList.toArray(strings);
		String resultStr = remoteObj.exec(strings);
		//解析出pid 如 Daemonized GoAccess: 18764,保存下来
		String pid = resultStr.replaceAll("\\D", "");
		stringRedisTemplate.opsForValue().set("goAccessPid", pid);
	}

	@Override
	public String getRealLogURL(HttpServletResponse response) throws Exception {
		return DataDricCommon.getValueByKey("reallog.url", "http://119.23.228.148:8060/logStatic/ngx_report.html");
	}
	
	/**
	 * 得到缓存值
	 * @param sysKey
	 * @return
	 */
	private String getCacheValue(String sysKey) {
        return DataDricCommon.getValueByKey(sysKey, "");
    }
}
