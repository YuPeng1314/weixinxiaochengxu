package com.huayu.irla.mobile.search;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huayu.irla.core.base.ResultVO;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.privilege.manage.common.UserMesCommon;





/**
 * 
  * @ClassName: CourseReseach
  * @Description: 小程序分类
  * @author GuGuangting
  * @date 2018年7月2日 am11:18:01
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */
@Controller
@RequestMapping("/courseSeach")
public class CourseSeach {
	private Logger logger = Logger.getLogger(CourseSeach.class);
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

    /**
     * 从搜索历史列表中获取匹配的列表
     * @param userId
     * @param pre
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/getAutoMatchs", method = RequestMethod.GET)
    public ResultVO getAutoMatchs(HttpServletRequest request){
		String userCode = UserMesCommon.getUserCode(request);
		ResultVO result = new ResultVO();
		Map<String, Object> map=new HashMap<String,Object>();;
		try {
			String key = "recent_search_"+userCode;
			//热门关键词
			String keyWords = DataDricCommon.getValueByKey("hot.search", "");
			String[] keyWord=keyWords.split("[,，]");
			map.put("hot", keyWord);
			//获取该用户对应的“搜索历史列表”
			Set<String> all=stringRedisTemplate.opsForZSet().reverseRange(key, 0, 9);
			map.put("search", all);
			result.setData(map);
			result.setNetCode(200);
		} catch (Exception e) {
			result.setNetCode(500);
			logger.error(e);
		}
        return result;
    }
	/**
	 * 清空搜索历史
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping(value = "/delList", method = RequestMethod.POST)
	public void deleteSearch(HttpServletRequest request) {
		String userCode = UserMesCommon.getUserCode(request);
		String key = "recent_search_"+userCode;
        stringRedisTemplate.delete(key);
	}
}
