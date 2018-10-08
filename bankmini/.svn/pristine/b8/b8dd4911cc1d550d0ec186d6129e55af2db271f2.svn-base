package com.huayu.irla.manage.section;

import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.huayu.irla.core.base.BaseVO;
import com.huayu.irla.core.operationlog.vo.OperationLogVO;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.manage.service.operationlog.IOperationLogService;
import com.huayu.irla.privilege.manage.common.UserMesCommon;
import com.huayu.irla.privilege.manage.vo.SysLoginoutVO;
import com.huayu.irla.privilege.manage.vo.SysUsersVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
  * @ClassName: sectionClass
  * @Description: 操作日志
  * @author liuwei
  * @date 2017年1月13日 下午8:53:20
  *
 */

@Aspect //该注解标示该类为切面类
@Component
public class SectionClass {
	
	@Autowired
	private IOperationLogService operationLogServiceImpl;

    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体     
    @AfterReturning("within(com.huayu.irla.manage.application.*.impl..*)")     
    public void addLogSuccess(JoinPoint jp){  
    	//获取section状态
    	String section = DataDricCommon.getValueByKey("Section_Switch","true");
    	//如果section为true，则打开操作日志功能
    	if(StringUtils.equals("true", section)){
    	try{
        Object[] parames = jp.getArgs();//获取目标方法体参数     
        String params = parseParames(parames); //解析目标方法体的参数     
        if(StringUtils.isBlank(params)){
        	params="无参数";
        }
        String className = jp.getTarget().getClass().toString();//获取目标类名     
        className = className.substring(className.indexOf("com")); 
       String signature = jp.getSignature().toString();//获取目标方法签名     
        String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));     
         String modelName = getModelName(className); //根据类名获取所属的模块    
         if(StringUtils.isBlank(modelName)){
        	 modelName="模块找寻错误";
         }
        OperationLogVO logvo = new OperationLogVO(); 
        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        SysUsersVO tempVO = UserMesCommon.getUserInfo(request);
        if(null != tempVO) {
	        logvo.setOpeartion_user(tempVO.getUsername());
	        logvo.setOperation_ip(tempVO.getLoginIp());
        }
        params = params.replaceAll("<img[^>]*/>", "");
        params = params.replaceAll("<[^>]+>", "");
        params = params.replaceAll("<script[^>]*?>[\\\\s\\\\S]*?<\\\\/script>", "");
        params = params.replaceAll("<style[^>]*?>[\\\\s\\\\S]*?<\\\\/style>", "");
		params = params.replaceAll("\\\\s*|\\t|\\r|\\n", "");
		params = params.replaceAll("\\\\&[a-zA-Z]{1,10};", "");
		params = params.replaceAll("&.{2,6}?;", "");
		params = params.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        logvo.setOperation_param(params);
        logvo.setOpeartion_method(methodName);
        logvo.setOperation_module(modelName);
        logvo.setOperation_flag("1");
        logvo.setOperation_err("无异常信息");
        operationLogServiceImpl.addOperaLog(logvo);
    } catch (Exception e) {     
   	 Logger.getLogger(this.getClass()).error(e);    
   } 
    	}else if(StringUtils.equals("false", section)){
    		return;
    	}
} 
    
    //标注该方法体为异常通知，当目标方法出现异常时，执行该方法体     
    @AfterThrowing(pointcut="within(com.huayu.irla.manage.application.*.impl..*)",throwing="ex")     
    public void addLog(JoinPoint jp, Exception ex){  
    	//记录日志
    	Logger.getLogger(this.getClass()).error(ex);  
    	
    	//获取section状态
    	String section = DataDricCommon.getValueByKey("Section_Switch","true");
    	//如果section为true，则打开操作日志功能
    	if(StringUtils.equals("true", section)){
    	try{
        Object[] parames = jp.getArgs();     
        String params = parseParames(parames);  
        if(StringUtils.isBlank(params)){
        	params="无参数";
        }
        String className = jp.getTarget().getClass().toString();     
        className = className.substring(className.indexOf("com"));     
        String signature = jp.getSignature().toString();     
        String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));     
        String modelName = getModelName(className);     
        if(StringUtils.isBlank(modelName)){
       	 modelName="模块找寻错误";
        }
        OperationLogVO logvo = new OperationLogVO();  
        
        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        SysUsersVO tempVO = UserMesCommon.getUserInfo(request);
        if(null != tempVO) {
	        logvo.setOpeartion_user(tempVO.getUsername());
	        logvo.setOperation_ip(tempVO.getLoginIp());
        }
        logvo.setOperation_param(params);
        logvo.setOpeartion_method(methodName);
        logvo.setOperation_module(modelName);
        logvo.setOperation_flag("0");
        logvo.setOperation_err(ex.getMessage());//记录异常信息
        operationLogServiceImpl.addOperaLog(logvo);
    } catch (Exception e) {     
      	 Logger.getLogger(this.getClass()).error(e);    
      }
    	}else if(StringUtils.equals("false", section)){
    		return;
    	}
    }     
         
    /**   
     * 根据包名查询检索其所属的模块   
     * @param packageName 包名   
     * @return 模块名称   
     */    
    private String getModelName(String packageName){     
        String modelName = "";     
        SAXReader reader = new SAXReader();     
        try {     
            //读取section.model.xml模块信息描述xml文档     
            File proj = ResourceUtils.getFile("classpath:config/section.model.xml"); 
            Document doc = reader.read(proj); 
            //获取文档根节点     
            Element root = doc.getRootElement();     
            //查询模块名称     
            modelName = searchModelName(root, packageName);     
        } catch (Exception e) {     
        	 Logger.getLogger(this.getClass()).error(e);    
        }     
        return modelName;     
    }     
         
    /**   
     * 采用递归方式根据包名逐级检索所属模块   
     * @param element 元素节点   
     * @param packageName 包名   
     * @return 模块名称   
     */    
    private String searchModelName(Element element, String packageName){  
		String modelName = "";
		 try { 
		List<Element> childElements = element.elements();//得到根节点的所有子节点
		//判断是否为空
		if (!childElements.isEmpty()) {
			for (Element child : childElements) {// 循环输出全部childElements的相关信息
				List<Element> classNames = child.elements();
				for (Element className : classNames) {// 循环输出全部classNames的相关信息
					//判断是否包名相同
					if (StringUtils.equals(className.getText(), packageName)) {
						modelName = child.attributeValue("name");
						return modelName;
					}
				}
			}
		}
		 } catch (Exception e) {     
        	 Logger.getLogger(this.getClass()).error(e);    
        } 
		return modelName;    
    }     
         
         
    /**   
     * 解析方法参数   
     * @param parames 方法参数   
     * @return 解析后的方法参数   
     */    
    private String parseParames(Object[] parames) {    
    	  StringBuffer sb = new StringBuffer();  
    	try {
        for(int i=0; i<parames.length; i++){   
        	if(parames[i] instanceof Attachment) {
        		continue;
        	}
            if(parames[i] instanceof Object[] || parames[i] instanceof Collection){     
                JSONArray json = JSONArray.fromObject(parames[i]);     
                if(i==parames.length-1){     
                    sb.append(json.toString());     
                }else{     
                    sb.append(json.toString() + ",");     
                }     
            }else{  
            	if(parames[i] instanceof BaseVO || parames[i] instanceof SysLoginoutVO) {
            		JSONObject json = JSONObject.fromObject(parames[i]); 
            		if(i==parames.length-1){     
                        sb.append(json.toString());     
                    }else{     
                        sb.append(json.toString() + ",");     
                    }   
            	} else {
            		sb.append(parames[i]+ ",");
            	}
                  
            }     
        }     
    	} catch(Exception ex) {
    		 Logger.getLogger(this.getClass()).error(ex);
    	}
        String params = sb.toString();    
        if(params.length()>1000){
        	params = params.substring(0, 1000)+"...";
        }
        return params;     
    }     
	
	
	
}
