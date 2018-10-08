package com.huayu.irla.mobile.loginout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.huayu.core.send.HttpUtils;
import com.huayu.irla.core.base.ResultVO;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.privilege.manage.common.UserMesCommon;
import com.huayu.irla.privilege.manage.dao.IMiniProgramUserInfoDao;
import com.huayu.irla.privilege.manage.vo.MiniProUserInfoVO;

/**
 * 
  * @ClassName: PersonalAction
  * @Description: 我的
  * @author liuwei
  * @date 2018年7月2日 下午2:56:28
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

@Controller
@RequestMapping("/miniInfo")
public class LoginAction {
	
	private Logger logger = Logger.getLogger(LoginAction.class);
	
	
	@Autowired
	private IMiniProgramUserInfoDao miniProgramUserInfoDao;
	
	/**
	 * redis对象
	 */
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	

    @ResponseBody
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public ResultVO getLoginInfo(HttpServletRequest request, HttpServletResponse response){
		ResultVO result = new ResultVO();
		try {
	        //获取用户登录传过来的code
	        String code=request.getParameter("code");
	        if(StringUtils.isBlank(code)) {
	        	result.setErrorMessage("传入得code参数为空");
	        	result.setNetCode(500);
	        	return result;
	        }
	        
	        //得到APP id
	        //得到密钥 key
	        String appId = DataDricCommon.getValueByKey("miniProgram.appId");
	        String accessKey = DataDricCommon.getValueByKey("miniProgram.accesskey");
	        if(StringUtils.isBlank(appId) || StringUtils.isBlank(accessKey)) {
	        	result.setErrorMessage("小程序参数为空");
	        	result.setNetCode(500);
	        	return result;
	        }
	
	        
	        String grantType="authorization_code";//固定值
	        //使用登录凭证 code 获取 session_key 和 openid。 
	        Map<String,String> map=new HashMap<String,String>();
	        map.put("appid", appId);//小程序的appid
	        map.put("js_code",code);//传入得用户code
	        map.put("grant_type",grantType);
	        map.put("secret", accessKey);

	        //得到URL
	        String externalURL = DataDricCommon.getValueByKey("irla.miniprogram.login", "http://119.23.228.148:8060/miniJsCode");
	        String resultStr= HttpUtils.sendGetRequest(externalURL, map, null);
	        //String resultStr = "{\"session_key\":\"l4sdM3Wu1bdc\\/kGdpHMfsg==\",\"openid\":\"ociVV41MbE0QU9zSnN3f-F2TKrho\"}";
	        //解析返回的json数据，获得OPPID
	        if(StringUtils.isNotBlank(resultStr)) {
		        JSONObject mpObj = JSONObject.parseObject(resultStr);
		        String openid = mpObj.getString("openid");
		        String sessionKey =  mpObj.getString("session_key");
		        String unionid = mpObj.getString("unionid");
		        if(StringUtils.isNotBlank(openid) && StringUtils.isNotBlank(sessionKey)){
		            //在此处添加自己的逻辑代码，将openid保存在数据库，或是，使用session_key去微信服务器换取用户头像、昵称等信息。我在这里并没有用到，因此我只保存了用户的openid
		        	
		        	MiniProUserInfoVO miniProVO = new MiniProUserInfoVO();
		        	miniProVO.setOpenid(openid);
		        	miniProVO.setSessionKey(sessionKey);
		        	miniProVO.setUnionid(unionid);
		        	
		        	List<MiniProUserInfoVO> miniUserInfoVOList = miniProgramUserInfoDao.getMiniProgramUserInfo(miniProVO);
		        	if(miniUserInfoVOList!=null && !miniUserInfoVOList.isEmpty()) {
		        		miniProgramUserInfoDao.updateMiniProgramUserInfo(miniProVO);
		        	} else {
		        		miniProgramUserInfoDao.addMiniProgramUserInfo(miniProVO);
		        	}
		        	
		        	//生成session key
		        	String sessionId =  DigestUtils.sha256Hex((openid+sessionKey).toString().getBytes("UTF-8"));
		        	
		        	//注销先前的用户信息
		        	//清理原有redis里面的值
		        	String preSessionVal = (String)request.getAttribute("sessionId");
		        	if(StringUtils.isNotBlank(preSessionVal)) {
		        		stringRedisTemplate.delete(preSessionVal);
		        	}
		        	UserMesCommon.updateUserInfo(request);
		        	stringRedisTemplate.opsForValue().set(sessionId, openid);
		        	result.setData(sessionId);
		        	 result.setNetCode(200);
		        } else {
		        	result.setErrorMessage(resultStr);
		        	result.setNetCode(500);
		        }
		       
	        } else {
	        	result.setErrorMessage("获取参数为空");
	        	result.setNetCode(500);
	        }
	        
	        
		} catch(Exception ex) {
			result.setNetCode(500);
			logger.error(ex);
		}
        return result;
    }
}
