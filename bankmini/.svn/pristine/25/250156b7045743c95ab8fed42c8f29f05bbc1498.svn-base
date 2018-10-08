package com.huayu.irla.mobile.resource;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huayu.core.mongodb.MongodbConstant;
import com.huayu.irla.core.base.ResultVO;
import com.huayu.irla.core.courseware.vo.CourseInfoVO;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.core.courseware.vo.VideoRecordInfoVO;
import com.huayu.irla.core.service.courseware.ICoursewareService;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.core.user.vo.MobSysUsersVO;
import com.huayu.irla.mobile.utils.CommonUtils;
import com.huayu.irla.privilege.manage.common.UserMesCommon;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

/**
 * 
 * @ClassName: CourseAction
 * @Description: 课程目录展示
 * @author Administrator
 * @date 2018年6月26日 
 */
@Controller
@RequestMapping("/resInfo")
public class CourseAction {

	private Logger logger = Logger.getLogger(CourseAction.class);
	
	
	@Autowired
	private ICoursewareService coursewareServiceImpl;
	
	/**
	 * mongdb对象
	 * @return
	 */
	@Autowired
	private MongoClient mongoClient;
	

	/**
	 * 查询课程信息
	 * @param request
	 * @param response
	 * @param courseInfoVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCourseInfo", method = RequestMethod.POST)
	public ResultVO findCourseInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody CourseInfoVO courseInfoVO) {
		ResultVO result = new ResultVO();
		try {
			
			List<CourseInfoVO> courseInfoVOList = Collections.emptyList();
			//得到起始位置和每页条数
			Integer offset = courseInfoVO.getOffset(); //页码
			Integer limit = courseInfoVO.getLimit(); //条数
			if(offset!=null && limit!=null && offset!=0 && limit!=0) {
				Integer realOffset = limit * (offset - 1);
				courseInfoVO.setOffset(realOffset);
				courseInfoVO.setLimit(limit);
				
				courseInfoVOList = coursewareServiceImpl.findCourseInfo(courseInfoVO);
				
			}
			result.setData(courseInfoVOList);
			Integer courseInfoList = coursewareServiceImpl.findCourseInfoCount(courseInfoVO);
			
			Map<String, Object> standbyParams = new HashMap<String, Object>();
			standbyParams.put("count", courseInfoList);
			result.setStandbyParams(standbyParams);
			result.setNetCode(200);
		} catch (Exception e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}

	/**
	 * 
	  * @Title: resourceCollect
	  * @Description: 资源的收藏与取消
	  * @return ResultVO
	  * @author liuwei
	  * @date 2018年6月28日 下午2:52:54
	 */
	
	@ResponseBody
	@Transactional
	@RequestMapping(value = "/resourceCollect", method = RequestMethod.POST)
	public ResultVO resourceCollect(HttpServletRequest request, HttpServletResponse response, @RequestBody CoursewareVO courseVO) {
		ResultVO result = new ResultVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			String userCode = UserMesCommon.getUserCode(request); //用户编码
			String resCode = courseVO.getResourceCode();   //资源编码
			String courseCode = courseVO.getCourseCode(); //课程编码
			if(StringUtils.isNotBlank(userCode) && StringUtils.isNotBlank(resCode) 
					&& StringUtils.isNotBlank(courseCode)) {
				courseVO.setCreatedBy(userCode);
				List<CoursewareVO> collectList = coursewareServiceImpl.getResCollect(courseVO);
				//如果用户已收藏，那么就取消收藏
				if(CollectionUtils.isNotEmpty(collectList)) {
					coursewareServiceImpl.deleteResCollect(Integer.parseInt(collectList.get(0).getId()));
					result.setData(0);
					result.setSubMessage("取消收藏成功！");
				}else {
					coursewareServiceImpl.addResCollect(courseVO);
					result.setData(1);
					result.setSubMessage("收藏成功！");
				}
				result.setNetCode(200);
			}else {
				result.setNetCode(500);
				result.setErrorMessage("参数不能为空！");
			}
		} catch (UnsupportedEncodingException e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
	
	/**
	 * 
	  * @Title: videoInfoShow
	  * @Description: 视频详情页面
	  * @return ResultVO
	  * @author liuwei
	  * @date 2018年7月2日 上午9:15:45
	 */
	
	
	@ResponseBody
	@Transactional
	@RequestMapping(value = "/videoInfoShow", method = RequestMethod.GET)
	public ResultVO videoInfoShow(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			String videoType = request.getParameter("videoType"); // 进入视频页面的跳转类型
			String courseCode = request.getParameter("courseCode"); // 课程编码
			String userCode = UserMesCommon.getUserCode(request); // 用户编码
			if (StringUtils.isNotBlank(courseCode) 	&& StringUtils.isNotBlank(videoType)) {
				CoursewareVO courseVO = new CoursewareVO();
				courseVO.setCourseCode(courseCode);
				courseVO.setIsValid("1");
				courseVO.setCreatedBy(userCode);
				List<CoursewareVO> courseList = coursewareServiceImpl.findCourseware(courseVO);   //当前课程下， 有效的全部视频信息
				Integer count = coursewareServiceImpl.findCoursewareCount(courseVO);              //当前课程下， 有效的视频总数
				Map<String, Object> standbyParams = new HashMap<String, Object>();
				Map<String, Object> data = new HashMap<String, Object>();
				standbyParams.put("nginxPath", CommonUtils.getNginxPath());            //资源访问IP
				standbyParams.put("resUrl", CommonUtils.getResFile());               //课程图片访问配置路径
				standbyParams.put("videoUrl", CommonUtils.getVideoFile());           //视频访问配置路径
				standbyParams.put("count", count);
				CoursewareVO tempVO = null;
				if (StringUtils.equals("0", videoType) && CollectionUtils.isNotEmpty(courseList)) { // videoType为0，则是课程进入视频页面
					tempVO = courseList.get(0);
				} else if (StringUtils.equals("1", videoType)) {                                    // videoType为1，则是视频进入指定视频页面
					String resourceCode = request.getParameter("resourceCode");      // 视频编码
					if (StringUtils.isNotBlank(resourceCode)) {
						courseVO.setResourceCode(resourceCode);
						List<CoursewareVO> tempList = coursewareServiceImpl.findCourseware(courseVO);
						if (CollectionUtils.isNotEmpty(tempList)) {
							tempVO = tempList.get(0);
						}
					} else {
						result.setNetCode(500);
						result.setErrorMessage("参数不能为空！");
					}
				}
				
				if(null!=tempVO && null!=userCode) {
					//增加视频的浏览量
					int visitNum = tempVO.getVisitNum();
					if (visitNum==0) {
						tempVO.setLastUpdatedBy(userCode);
						coursewareServiceImpl.addVisitNum(tempVO);
					} else {
						coursewareServiceImpl.updateVisitNum(tempVO);
					}
					standbyParams.put("sequenceNumber", tempVO.getSequenceNumber());
				}
				
				
				
				result.setNetCode(200);
				result.setStandbyParams(standbyParams);
				data.put("courseList", courseList);
				//该课程的同级课程
				CourseInfoVO categoryVO = new CourseInfoVO();
				categoryVO.setCourseCode(courseCode);
				List<CourseInfoVO> categoryList = coursewareServiceImpl.getOtherCourse(categoryVO);
				CourseInfoVO courseInfo = new CourseInfoVO();
				List<CourseInfoVO> otherCategoryList = Collections.emptyList();
				if(CollectionUtils.isNotEmpty(categoryList)) {
					courseInfo = categoryList.get(0);
					String categoryCode = courseInfo.getCategoryCode();
					categoryVO.setCategoryCode(categoryCode);
					categoryVO.setOtherCourse("0");   //otherCourse有值则查询其他的课程
					otherCategoryList = coursewareServiceImpl.getOtherCourse(categoryVO);
				}
				data.put("courseInfo", courseInfo);
				data.put("categoryList", otherCategoryList);
				result.setData(data);
			} else {
				result.setNetCode(500);
				result.setErrorMessage("参数不能为空！");
			}
		} catch (UnsupportedEncodingException e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}
	
	
	@ResponseBody
	@Transactional
	@RequestMapping(value = "/recordResInfoTime", method = RequestMethod.POST)
	public ResultVO recordResInfoTime(HttpServletRequest request, @RequestBody VideoRecordInfoVO videoRecordInfoVO) {
		ResultVO result = new ResultVO();
		try {
			String userCode = UserMesCommon.getUserCode(request);
			if(null==videoRecordInfoVO ||null==videoRecordInfoVO.getResourceId()) {
				result.setErrorMessage("参数有为空的情况");
				return result;
			}
			
			if(null != videoRecordInfoVO && null!=userCode) {
				
				//resource id存入redis中
				MongoDatabase mongoDB = mongoClient.getDatabase(MongodbConstant.MONGODB_NAME);
				MongoCollection<Document> collectionOper = mongoDB.getCollection("irla_res_visit_log_t");
				
				//删除原有的
				collectionOper.deleteOne(Filters.and(Filters.eq("resourceId", videoRecordInfoVO.getResourceId()),Filters.eq("userCode", userCode)));
				
				Document courseObj = new Document();
				courseObj.append("courseCode", videoRecordInfoVO.getCourseCode());
				courseObj.append("userCode", userCode);
				courseObj.append("resourceCode", videoRecordInfoVO.getResourceCode());
				courseObj.append("resourceName", videoRecordInfoVO.getResourceName());
				courseObj.append("resourceId", videoRecordInfoVO.getResourceId());
				courseObj.append("recordDate", Calendar.getInstance().getTimeInMillis());
				courseObj.append("currentPlayTime", videoRecordInfoVO.getCurrentPlayTime());
				courseObj.append("imgName", videoRecordInfoVO.getImgName());
				collectionOper.insertOne(courseObj);
				
			}
			result.setNetCode(200);
		} catch(Exception ex) {
			result.setNetCode(500);
			logger.error(ex);
		}
		return result;
	}
	
	@ResponseBody
	@Transactional
	@RequestMapping(value = "/getResInfoTime", method = RequestMethod.GET)
	public ResultVO getResInfoTime(HttpServletRequest request) {
		
		ResultVO result = new ResultVO();
		try {
			String userCode = UserMesCommon.getUserCode(request);
			if(StringUtils.isBlank(userCode)) {
				result.setNetCode(403);
				return result;
			}
			
			MongoDatabase mongoDB = mongoClient.getDatabase(MongodbConstant.MONGODB_NAME);
			MongoCollection<Document> collectionOper = mongoDB.getCollection("irla_res_visit_log_t");
			FindIterable<Document> dataList = collectionOper.find(Filters.eq("userCode", userCode)).sort(Sorts.descending("_id"));
			MongoCursor<Document> docCurList =  dataList.iterator();
			
			//最多存放50个元素
			List<VideoRecordInfoVO> docHistList = new ArrayList<VideoRecordInfoVO>();
			Integer limitNum = 50;
			try {
				limitNum = Integer.valueOf(DataDricCommon.getValueByKey("irla.visitlog.num", "50"));
			}catch(NumberFormatException ex) {
				logger.error(ex);
			}
			
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
			
				if(!docHistList.contains(viewVO)) {
					docHistList.add(viewVO);
				} else {
					collectionOper.deleteOne(docTmp);
				}
				
				if(docHistList.size()==limitNum) {
					break;
				}
			}
			
			
			result.setData(docHistList);
			Map<String, Object> standbyParams = new HashMap<String, Object>();
			standbyParams.put("nginxPath", CommonUtils.getNginxPath());            //资源访问IP
			standbyParams.put("videoUrl", CommonUtils.getVideoFile()); 			//视频图片访问配置路径
			standbyParams.put("count", docHistList.size());
			result.setStandbyParams(standbyParams);
			result.setNetCode(200);
		} catch(Exception ex) {
			result.setNetCode(500);
			logger.error(ex);
		}
		return result;
	}

	
	@ResponseBody
	@Transactional
	@RequestMapping(value = "/deleteResInfoTime", method = RequestMethod.POST)
	public ResultVO deleteResInfoTime(HttpServletRequest request,@RequestParam String id) {
		ResultVO result = new ResultVO();
		
		MobSysUsersVO mobSysUsersVO = UserMesCommon.getAuthorizeInfo(request);
		if(null == mobSysUsersVO) {
			result.setNetCode(403);
			return result;
		}
	
		try {
			//resource id存入redis中
			MongoDatabase mongoDB = mongoClient.getDatabase(MongodbConstant.MONGODB_NAME);
			MongoCollection<Document> collectionOper = mongoDB.getCollection("irla_res_visit_log_t");
			collectionOper.deleteOne(Filters.eq("_id", new ObjectId(id)));
			result.setNetCode(200);
		} catch(Exception ex) {
			result.setNetCode(500);
			logger.error(ex);
		}
		return result;
	}
}
