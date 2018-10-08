package com.huayu.irla.mobile.loginout;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;
import com.huayu.irla.privilege.manage.dao.ISysMetaDataDao;
import com.huayu.irla.privilege.manage.vo.SysUrlPersistentVO;

public class CustomePrivilegeConfig implements ApplicationContextAware, InitializingBean{
	
    List<SysUrlPersistentVO> sysPerVOList = new ArrayList<SysUrlPersistentVO>();
    
    @Autowired  
    private ISysMetaDataDao sysMetoDataDao;  
    
    @Autowired
    
    private ApplicationContext applicationContext;
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	   this.applicationContext = applicationContext;	
	}
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//vo初始化两个序列
		try {
			parseElement();
		} catch(Exception ex) {
			Logger.getLogger(getClass()).error(ex);
		} 
		
		//加载权限资源
		try {
			List<String> types = new ArrayList<String>();
			//手机端权限
			types.add("mobile");
		} catch(Exception ex) {
			Logger.getLogger(getClass()).error(ex);
		}
	}
	
	/**
	 * 
	  * 解析注解参数
	  * @Title: parseElement
	  * @Description: TODO
	  * @param 
	  * @return void
	  * @throws
	 */
	private void parseElement() {
		//得到所有被注释的类
			Map<String, Object> customerValidationRules  = applicationContext.getBeansWithAnnotation(PrivilegePointor.class);
			
			//存放数据的Map,用来表示一对多的关系
			Map<String, List<SysUrlPersistentVO>> dataMap = new HashMap<String, List<SysUrlPersistentVO>>();
			
			for(Entry<String, Object> tmpObj:customerValidationRules.entrySet()) {
				Class<?> tmpClass = AopUtils.getTargetClass(tmpObj.getValue());
				PrivilegePointor pointorObj = (PrivilegePointor)tmpClass.getDeclaredAnnotation(PrivilegePointor.class);
				
				//资源名称
				String resourceName = pointorObj.value();
				
				Method[] tmpMethod = tmpClass.getMethods();
				//得到对应的路径
				RequestMapping classPath = (RequestMapping)tmpClass.getAnnotation(RequestMapping.class);
				
				if(tmpMethod!=null && tmpMethod.length>0) {
					for(Method oneMethod:tmpMethod) {
						//得到权限方法注解
						PrivilegePoint tmpPoint = oneMethod.getAnnotation(PrivilegePoint.class);
						if(tmpPoint != null) {
							
							//类的路径
							String[] parentPath = classPath.value();
							String classURL = "";
							if(parentPath.length > 0) {
								classURL = parentPath[0];
								if(classURL.charAt(0)!='/') {
									classURL = '/'+classURL;
								}
							}
							//方法的路径
							RequestMapping methodPath =(RequestMapping)oneMethod.getAnnotation(RequestMapping.class);
							String[] methodPathStr = methodPath.value();
							String methodURL = "";
							if(methodPathStr.length > 0) {
								methodURL = methodPathStr[0];
								if(methodURL.charAt(0)!='/') {
									methodURL = '/'+methodURL;
								}
							}
							//去掉路径请求
							int daChar = methodURL.indexOf("{");
							if(daChar > -1) {
								methodURL = methodURL.substring(0, daChar-1);
							}
							
							//权限模式
							int privilegeMode = tmpPoint.privilegeMode();
						    //权限名称
							String privilegeName= tmpPoint.privilegeName();
							//URL
							String url = classURL+methodURL;
							//去掉多余的斜杠
							url = url.replace("//", "/");
							
							//key:resource name+mode
							String key = resourceName + ';' + privilegeMode;
							//得到持久化的集合VO
							List<SysUrlPersistentVO> urlVosList = dataMap.get(key);
							long resSeq = sysMetoDataDao.getResSeq();
							//URL持久化javabean
							SysUrlPersistentVO sys = new SysUrlPersistentVO();
							sys.setResourceID(resSeq);
							sys.setUrl(url);
							sys.setResourceName(privilegeName);
							
							if(null == urlVosList) {
								List<SysUrlPersistentVO> urlsList = new ArrayList<SysUrlPersistentVO>();
								urlsList.add(sys);	
								dataMap.put(key, urlsList);
							} else {
								urlVosList.add(sys);
							}
						}
					}
				}
			}
			//初始化Map
			initPriHandle(dataMap);
	}
	
	
	//封装权限初始化网络
	private void initPriHandle(Map<String,List<SysUrlPersistentVO>> priMap) {
		if(null!=priMap && !priMap.isEmpty()) {
			Set<String> priviSet = priMap.keySet();
			Iterator<String> it = priviSet.iterator();
			
			//批量序列
			long batchID = sysMetoDataDao.getPriSeq();
			while(it.hasNext()) {
				//得到资源名称+权限模式
				String priviVal = it.next();
				String[] priArr = priviVal.split("[;]");
				//得到资源、资源模式
				String resName = priArr[0];
				String privilegeMode = priArr[1];
				
				//得到权限序列
				long authSeq = sysMetoDataDao.getAuthoritiySeq();
				
				List<SysUrlPersistentVO> sysPerVOs = priMap.get(priviVal);
				for(SysUrlPersistentVO tmpVO:sysPerVOs) {
					tmpVO.setBatchID(batchID);
					tmpVO.setAuthorityID(authSeq);
					tmpVO.setPrivilegeMode(Integer.parseInt(privilegeMode));
					tmpVO.setPrivilegeName(resName);
					sysMetoDataDao.addPrivilegeTmp(tmpVO);
				}
			}
			Map<String,Object> tmpMap = new HashMap<String, Object>();
			tmpMap.put("batchNum", batchID);
			tmpMap.put("dataType", "mobile");
			sysMetoDataDao.initalPrivilege(tmpMap);
		}
	}
}
