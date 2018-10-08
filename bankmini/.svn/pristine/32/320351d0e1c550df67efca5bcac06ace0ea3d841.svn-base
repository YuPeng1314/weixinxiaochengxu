package com.huayu.irla.mobile.interaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huayu.core.mongodb.MongodbConstant;
import com.huayu.irla.core.active.vo.ActiveVO;
import com.huayu.irla.core.base.ResultVO;
import com.huayu.irla.core.course.vo.CourseVO;
import com.huayu.irla.core.courseware.vo.VideoRecordInfoVO;
import com.huayu.irla.core.service.active.IActiveService;
import com.huayu.irla.core.service.course.ICourseService;
import com.huayu.irla.core.service.user.IWxUserInfoService;
import com.huayu.irla.core.user.vo.WxUserFriendResVO;
import com.huayu.irla.core.user.vo.WxUserInfoVO;
import com.huayu.irla.mobile.utils.CommonUtils;
import com.huayu.irla.privilege.manage.common.UserMesCommon;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

/**
 * 
  * @ClassName: InteractionAction
  * @Description: 互动版块
  * @author liuwei
  * Company:华煜网络教育有限公司
 */

@Controller
@RequestMapping("/interaction")
public class InteractionAction {

	/*private Logger logger = Logger.getLogger(InteractionAction.class);*/
	
	/**
	 * mongdb对象
	 * @return
	 */
	@Autowired
	private MongoClient mongoClient;
	
	@Autowired
	private ICourseService courseServiceImpl;
	
	@Autowired
	private IActiveService activeServiceImpl;
	
	@Autowired
	private IWxUserInfoService wxUserInfoServiceImpl;
	
	
	/**
	 * 
	  * @Title: mayKnow
	  * @Description: 可能认识的人
	  * @return ResultVO
	  * @author liuwei
	 */
	
	@ResponseBody
	@Transactional
	@RequestMapping(value = "/mayKnow", method = RequestMethod.GET)
	public ResultVO mayKnow(HttpServletRequest request) {
		ResultVO result = new ResultVO();
		//正式测试
		String userCode = UserMesCommon.getUserCode(request);
		String latitude = request.getParameter("latitude"); // 纬度
		String longitude = request.getParameter("longitude"); // 经度
		if(StringUtils.isNotBlank(userCode) && StringUtils.isNotBlank(latitude) 
				&& StringUtils.isNotBlank(longitude)) {
			WxUserInfoVO uservo = new WxUserInfoVO();
		    uservo.setUser_code(userCode);
			uservo.setIsQuery("2");//正常查询
        	List<WxUserInfoVO> userInfoList = wxUserInfoServiceImpl.getUserMayKnowInfo(uservo);
        	if(CollectionUtils.isEmpty(userInfoList)) {  //未授权
        		result.setData(userInfoList);
        		result.setNetCode(200);
        	}else {
        		double lat = new BigDecimal(latitude.toString()).doubleValue();
    		    double lng = new BigDecimal(longitude.toString()).doubleValue();
    		    List<WxUserInfoVO> userList = findNeighPosition(lng,lat,userCode);
    		    result.setData(userList);
    			Map<String, Object> standbyParams = new HashMap<String, Object>();
    			standbyParams.put("nginxPath", CommonUtils.getNginxPath());
    			standbyParams.put("phizUrl", CommonUtils.getPhizFile());
    			result.setStandbyParams(standbyParams);
    			result.setNetCode(200);
        	}
		}else {
			result.setNetCode(500);
			result.setErrorMessage("参数不能为空！");
		}
		//本地测试
		/*String userCode = "o2WMJ4_V0WuW1n1TNcqddBWCCOVU";
		double lat = 28.21024000000001;
		double lng = 112.88984000000002;
		List<WxUserInfoVO> userList = findNeighPosition(lng,lat,userCode);
		result.setData(userList);*/
		
		return result;
	}
	
	/**
	 * 
	  * @Title: findNeighPosition
	  * @Description: 找寻周围可能认识的人
	  * @return List<WxUserInfoVO>
	  * @author liuwei
	 */
	
	public List<WxUserInfoVO> findNeighPosition(double longitude,double latitude,String userCode){  
        double r = 6371; //地球半径千米  
        double dis = 5;  //附近5千米距离  
        double dlng =  2*Math.asin(Math.sin(dis/(2*r))/Math.cos(latitude*Math.PI/180));  
        dlng = dlng*180/Math.PI;//角度转为弧度  
        double dlat = dis/r;  
        dlat = dlat*180/Math.PI;          
        double minlat =latitude-dlat;  
        double maxlat = latitude+dlat;  
        double minlng = longitude -dlng;  
        double maxlng = longitude + dlng;  
        WxUserInfoVO uservo = new WxUserInfoVO();
        uservo.setUser_code(userCode);
        uservo.setIsQuery("0"); //可能认识的人
        uservo.setMinlat(minlat);
        uservo.setMaxlat(maxlat);
        uservo.setMinlng(minlng);
        uservo.setMaxlng(maxlng);
        List<WxUserInfoVO> userList = wxUserInfoServiceImpl.getUserMayKnowInfo(uservo);
        if(CollectionUtils.isEmpty(userList)) {
        	uservo.setIsQuery("1"); //系统推荐的人
        	List<WxUserInfoVO> commendList = wxUserInfoServiceImpl.getUserMayKnowInfo(uservo);
        	return commendList;
        }
		return userList; 
    }  
	
	
	/**
	 * 
	  * @Title: getCreAndAct
	  * @Description: 最新活动和最新课程
	  * @return ResultVO
	  * @author liuwei
	 */
	
	@ResponseBody
	@Transactional
	@RequestMapping(value = "/getCreAndAct", method = RequestMethod.GET)
	public ResultVO getCreAndAct(HttpServletRequest request) {
		ResultVO result = new ResultVO();
		List<CourseVO> courseList = courseServiceImpl.getNewCourse();  //取两条最新课程
		ActiveVO activevo = new ActiveVO();
		activevo.setIsValid("1");
		activevo.setKinds("0"); //官方
		List<ActiveVO> oList =  activeServiceImpl.getActiveList(activevo); 
		activevo.setKinds("1"); //社区
		List<ActiveVO> sList =  activeServiceImpl.getActiveList(activevo); 
		List<ActiveVO> activeList = new ArrayList<>();
		activeList.add(oList.get(0));
		activeList.add(sList.get(0));
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("courseList", courseList);
		data.put("activeList", activeList);
		result.setData(data);
		Map<String, Object> standbyParams = new HashMap<String, Object>();
		standbyParams.put("nginxPath", CommonUtils.getNginxPath());
		standbyParams.put("resUrl", CommonUtils.getResFile());
		standbyParams.put("phizUrl", CommonUtils.getPhizFile());
		result.setStandbyParams(standbyParams);
		result.setNetCode(200);
		return result;
		
	}
	
	/**
	 * 
	  * @Title: getVideoRecord
	  * @Description: 取得关注好友观看记录
	  * @return ResultVO
	  * @author liuwei
	 */
	
	@ResponseBody
	@Transactional
	@RequestMapping(value = "/getVideoRecord", method = RequestMethod.GET)
	public ResultVO getVideoRecord(HttpServletRequest request) {
		ResultVO result = new ResultVO();
		String userCode = UserMesCommon.getUserCode(request);
		/*String userCode = "ociVV41MbE0QU9zSnN3f-F2TKrho";*/
		String limit = request.getParameter("limit"); // 条数
		String offset = request.getParameter("offset"); // 页码
		if(StringUtils.isNotBlank(userCode) && StringUtils.isNotBlank(limit) 
				&& StringUtils.isNotBlank(offset)) {
			Integer tempInt = Integer.parseInt(offset);
			Integer limitInt = Integer.parseInt(limit);
			Integer realOffset = limitInt * (tempInt - 1);
			WxUserFriendResVO userFrivo = new WxUserFriendResVO();
			userFrivo.setUser_code(userCode);
			List<WxUserFriendResVO> userFriList = wxUserInfoServiceImpl.getUserFriendInfo(userFrivo);
			if(CollectionUtils.isNotEmpty(userFriList)) {
				MongoDatabase mongoDB = mongoClient.getDatabase(MongodbConstant.MONGODB_NAME);
				MongoCollection<Document> collectionOper = mongoDB.getCollection("irla_res_visit_log_t");
				BasicDBObject queryCondition = new BasicDBObject();        
		        BasicDBList values = new BasicDBList();
		        Map<String, List<WxUserInfoVO>> mapTemp = new HashMap<String, List<WxUserInfoVO>>();
		        for(WxUserFriendResVO tempvo : userFriList ) {
		        	values.add(tempvo.getUser_friend_code());
		        	WxUserInfoVO uservo = new WxUserInfoVO();
			        uservo.setUser_code(tempvo.getUser_friend_code());
			        uservo.setIsQuery("2"); //正常查询信息
			        List<WxUserInfoVO> userList = wxUserInfoServiceImpl.getUserMayKnowInfo(uservo);
			        mapTemp.put(tempvo.getUser_friend_code(), userList);
		        	
		        }
		        queryCondition.put("userCode", new BasicDBObject("$in", values));
				FindIterable<Document> dataList = collectionOper.find(queryCondition).sort(Sorts.descending("_id")).limit(limitInt).skip(realOffset);;
				long count = collectionOper.count(queryCondition);
				MongoCursor<Document> docCurList =  dataList.iterator();
				List<VideoRecordInfoVO> docHistList = new ArrayList<VideoRecordInfoVO>();
				//循环输出
				while(docCurList.hasNext()) {
					Document docTmp = docCurList.next();
					VideoRecordInfoVO viewVO = new VideoRecordInfoVO();
					viewVO.setId(docTmp.get("_id").toString());
					viewVO.setUserCode(docTmp.getString("userCode"));
					viewVO.setCourseCode(docTmp.getString("courseCode"));
					viewVO.setCurrentPlayTime(docTmp.getInteger("currentPlayTime"));
					viewVO.setImgName(docTmp.getString("imgName"));
					viewVO.setResourceId(docTmp.getString("resourceId"));
					viewVO.setResourceCode(docTmp.getString("resourceCode"));
					viewVO.setResourceName(docTmp.getString("resourceName"));
					viewVO.setRecordDate(docTmp.getLong("recordDate"));
			        viewVO.setUserList(mapTemp.get(docTmp.getString("userCode")));
					docHistList.add(viewVO);
				}
				Map<String, Object> standbyParams = new HashMap<String, Object>();
				standbyParams.put("phizUrl", CommonUtils.getPhizFile());
				standbyParams.put("resUrl", CommonUtils.getResFile());
				standbyParams.put("count", count);
				result.setStandbyParams(standbyParams);
				result.setData(docHistList);
			}else {
				result.setData(userFriList);
			}
			result.setNetCode(200);
		}else {
			result.setNetCode(500);
			result.setErrorMessage("参数不能为空！");
		}
		return result;
	}
	
}
