package com.huayu.irla.manage.application.courseware.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.activation.DataHandler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.core.util.EnumConstants;
import com.huayu.core.util.ImageFormatConvert;
import com.huayu.core.util.RemoteShellUtils;
import com.huayu.core.util.SSHSftp;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.core.dao.courseware.ICoursewareDao;
import com.huayu.irla.core.dao.resource.IResourceDao;
import com.huayu.irla.core.service.courseware.ICoursewareService;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.manage.application.courseware.ICoursewareedit;
import com.huayu.irla.manage.util.UserUtils;

/**
 * 
  * @ClassName: CoursewareEditImpl
  * @Description: 课件资源维护
  * @author liuwei
  * @date 2017年9月18日 下午12:28:18
  * Copyright: Copyright (c) 2017 
  * Company:华煜网络教育有限公司
 */
@Component("coursewareedit")
public class CoursewareeditImpl implements ICoursewareedit {
	
	
	private static final Logger logger = Logger.getLogger(CoursewareeditImpl.class);
	
	
	@Autowired
	private ICoursewareService coursewareServiceImpl;
	
	@Autowired
    private IResourceDao directoryDao;
	
	@Autowired
	private ICoursewareDao coursewareDao;
	
	@Value("${codc.file.path}")
	private String codcFilePath;
	
	/**
	 * 
	  * @Title: getCoursewarelist
	  * @Description: 取得课件资源
	  * @author liuwei
	  * @date 2017年9月18日 下午12:31:12
	 */
	@Override
	public JSONObject getCoursewarelist(CoursewareVO course) {
		JSONObject result = new JSONObject();
		if(course != null) {
			List<CoursewareVO> datalist = Collections.emptyList();
			//取得数据的总数
	        Integer count = coursewareServiceImpl.findCoursewareMaintainCount(course);
	        
			//取得数据
			datalist = coursewareServiceImpl.findCoursewareMaintain(course);
			
			if(datalist != null) {
				for(CoursewareVO resVO : datalist) {
					String tempCourseCodes = resVO.getCourseCode();
					String tempCourseName = resVO.getCourseName();
					if(StringUtils.isNotBlank(tempCourseCodes) && StringUtils.isNotBlank(tempCourseName)) {
						String[] tmpCourseCode = tempCourseCodes.split("#");
						String[] tmpCourseName = tempCourseName.split("#");
						Set<String> tmpCourseCodeList = new HashSet<String>();
						Set<String> tmpCourseNameList = new HashSet<String>();
						for(int i=0; i<tmpCourseCode.length; i++) {
							tmpCourseCodeList.add(tmpCourseCode[i]);
							tmpCourseNameList.add("《"+tmpCourseName[i]+"》");
						}
						
						//放入对应集合中
						resVO.setCourseCodes(new ArrayList<>(tmpCourseCodeList));
						resVO.setCourseNames(new ArrayList<>(tmpCourseNameList));
					}
				}
			}
			
			String ipAddress = getCacheValue(EnumConstants.IMG_SERVICE_IP);
	        
	        result.put("ip", ipAddress);
			result.put("videoUrl", DataDricCommon.getValueByKey("video.file"));
	        result.put("rows", JSONArray.toJSON(datalist));
	        result.put("total", count);
		}
        return result;
	}

	//调用数据字典
	private String getCacheValue(String sysKey) {
		 return DataDricCommon.getValueByKey(sysKey, "");
	}
	
	/**
	 * 
	  * @Title: validCourse
	  * @Description: 发布或者撤销视频
	  * @author liuwei
	  * @date 2017年9月19日 上午9:04:22
	 */
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean validCourse(String resourceId, String state) {
		if (StringUtils.isNotBlank(resourceId)  && StringUtils.isNotBlank(state)) {
			CoursewareVO temp = new CoursewareVO();
			temp.setResourceId(resourceId);
			List<CoursewareVO> datalist = Collections.emptyList();
			//取得数据
			datalist = coursewareServiceImpl.findCourseware(temp);
			if(CollectionUtils.isNotEmpty(datalist)) {
				String userName = UserUtils.getLoginName();
				for(CoursewareVO course : datalist) {
					// 判断state状态，若为false，则存入0，若为true,则存入1
	                if ("false".equals(state)) {
	                	course.setIsValid("0");
	                } else {
	                	course.setIsValid("1");
	                }
	                course.setLastUpdatedBy(userName);
	                coursewareServiceImpl.updateImgAddre(course);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	  * @Title: updateTime
	  * @Description: 修改视频指定学时
	  * @author liuwei
	  * @date 2017年9月18日 下午7:26:22
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateTime(CoursewareVO course) {
		if(course != null) {
			CoursewareVO temp = new CoursewareVO();
			temp.setResourceId(course.getResourceId());
			/*temp.setId(course.getId());*/
			List<CoursewareVO> datalist = Collections.emptyList();
			//取得数据
			datalist = coursewareServiceImpl.findCourseware(temp);
			if(CollectionUtils.isNotEmpty(datalist) && StringUtils.isNotBlank(course.getResourceId())) {
				CoursewareVO videoVO = datalist.get(0);
				if("0".equals(course.getImgFlag())) {
					deleteImg(codcFilePath, videoVO.getImgName());
					updateCatalogCode(course,datalist);
				} else {
					updateCatalogCode(course,datalist, videoVO.getImgName());
				}
				
				
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	  * @Title: uploadCourseImg
	  * @Description: 修改视频的封面图片
	  * @author liuwei
	  * @date 2017年9月18日 下午7:41:02
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean uploadCourseImg(List<Attachment> attachments, CoursewareVO course) {
		/*String userName = UserUtils.getLoginName();*/
		try {
			if (!attachments.isEmpty() && course != null) {
				CoursewareVO temp = new CoursewareVO();
				temp.setResourceId(course.getResourceId());
				/*temp.setId(course.getId());*/
				List<CoursewareVO> datalist = Collections.emptyList();
				// 取得数据
				datalist = coursewareServiceImpl.findCourseware(temp);
				if (CollectionUtils.isNotEmpty(datalist) && StringUtils.isNotBlank(course.getResourceId())) {
					CoursewareVO courseVO = datalist.get(0);
					boolean flag = true;
					InputStream is = null;
					InputStream stream = null;
					//先删除服务器上的图片
					boolean sucess = true;
					if(StringUtils.isNotBlank(courseVO.getImgName())) {
						sucess = deleteImg(codcFilePath, courseVO.getImgName());
					}
					if (sucess) {
						
						//获取文件的名称
						String tmpFileName = courseVO.getAttachmentName();
						String tmpNamePath = tmpFileName.substring(0, tmpFileName.lastIndexOf("."));
						
						String purPath = tmpFileName.substring(0, tmpFileName.lastIndexOf("/"));
						// 上传的文件路径
						String saveFile = tmpNamePath + ".jpg";
						// 获取上传文件的内容
						for (Attachment attach : attachments) {
							DataHandler dh = attach.getDataHandler();
							
							
							// 上传到服务器的路径
							String sfileUrl = codcFilePath + saveFile;
							File desFilea = new File(sfileUrl);
							
							String imgUrl = codcFilePath+purPath;
							// 远程上传服务器资源
							if (dh.getDataSource() != null) {
								is = dh.getDataSource().getInputStream();
								stream = ImageFormatConvert.convertJPEGInputStream(is);
								flag = SSHSftp.sshSftp(getCacheValue(EnumConstants.SERVICE_IP),
										getCacheValue(EnumConstants.SERVICE_USERNAME),
										getCacheValue(EnumConstants.SERVICE_PAS),
										new Integer(getCacheValue(EnumConstants.SERVICE_PORT)).intValue(), imgUrl,
										desFilea, stream);
							}
						}
						if (flag) {
							updateCatalogCode(course,datalist, saveFile);
							/*course.setLastUpdatedBy(userName);
							coursewareServiceImpl.updateImgAddre(course);*/
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("操作失败!", e);
			return false;
		}
		return false;
	}
	
	
	/**
	 * 
	  * @Title: updateCatalogCode
	  * @Description: 修改目录
	  * @return void
	  * @author liuwei
	  * @date 2017年11月22日 下午2:10:27
	 */
	
	public void updateCatalogCode(CoursewareVO course, List<CoursewareVO> datalist) {
		updateCatalogCode(course, datalist, null);
	}
	
	/**
	 * 
	  * @Title: updateCatalogCode
	  * @Description: 修改目录
	  * @return void
	  * @author liuwei
	  * @date 2017年11月22日 下午2:10:27
	 */
	
	public void updateCatalogCode(CoursewareVO course, List<CoursewareVO> datalist, String imgPath) {
		String userName = UserUtils.getLoginName();
		CoursewareVO videoVO = datalist.get(0);
		String courseCodes = course.getCourseCode();
		String code[] = courseCodes.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < code.length; i++) {
			list.add(code[i]);
		}
		
		Date tmpDate = Calendar.getInstance().getTime();
		for(CoursewareVO tempVO : datalist) {
			boolean flag = false;
			for(String addCode : list) {
				//原目录仍然存在没变
				if(StringUtils.equals(tempVO.getCourseCode(), addCode)) {
					tempVO.setLastUpdatedBy(userName);
					tempVO.setIsPublic(course.getIsPublic());
					tempVO.setLastUpdateDate(tmpDate);
					tempVO.setImgName(imgPath);
					coursewareServiceImpl.updateImgAddre(tempVO);
					list.remove(addCode);
					flag = true;
					break;
				}
			}
			//原目录不存在,则删除
			if(!flag){
				coursewareServiceImpl.deleteCourseFile(Integer.parseInt(tempVO.getId()));
			}
		}
		//若有新增加的目录，则循环添加至视频表中
		if(CollectionUtils.isNotEmpty(list)) {
			for(String addCode : list) {
				//得到序列
				long resUid = directoryDao.getResourceSeq();
				videoVO.setResourceCode("RES"+resUid);
				videoVO.setCourseCode(addCode);
				videoVO.setIsPublic(course.getIsPublic());
				videoVO.setIsValid("0");
				videoVO.setCreatedBy(userName);
				videoVO.setLastUpdateDate(tmpDate);
				videoVO.setLastUpdatedBy(userName);
				videoVO.setImgName(imgPath);
				coursewareDao.insertFormualRes(videoVO);
			}	
		}
	}
	
	/**
	 * 
	  * @Title: deleteImg
	  * @Description: 删除服务器上的图片
	  * @return boolean
	  * @author liuwei
	  * @date 2017年9月18日 下午7:59:45
	 */
	
    private boolean deleteImg(String rootUrl, String delimgName) {
        if (StringUtils.isBlank(delimgName)) {
            return false;
        }
        String delUrl = rootUrl + delimgName;
        try {
            // 删除服务器文件
            String result = RemoteShellUtils
                    .getInstance(getCacheValue(EnumConstants.SERVICE_IP), getCacheValue(EnumConstants.SERVICE_USERNAME),
                            getCacheValue(EnumConstants.SERVICE_PAS), EnumConstants.IETL_CODE_UTF8)
                    .exec("rm -rf " + delUrl);
            if (StringUtils.isNotBlank(result)) {
                return false;
            }
        } catch (Exception e) {
            logger.error("删除原始文件异常", e);
            return false;
        }
        return true;
    }

    /**
     * 
      * @Title: deleteVideo
      * @Description: 删除视频
      * @author liuwei
      * @date 2017年9月30日 上午10:27:37
     */
    
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteVideo(String resourceId) {
        if(StringUtils.isNotBlank(resourceId)) {
        	CoursewareVO temp = new CoursewareVO();
        	temp.setResourceId(resourceId);
			List<CoursewareVO> datalist = Collections.emptyList();
			// 取得数据
			datalist = coursewareServiceImpl.findCourseware(temp);
			if (CollectionUtils.isNotEmpty(datalist)){
				CoursewareVO courseVO = datalist.get(0);
				String cdUrl = getCacheValue(EnumConstants.COURSEWARE_PATH);
				//删除服务器上的图片
				boolean flagImg = deleteImg(cdUrl, courseVO.getImgName());
				//删除服务器上的视频
				boolean flagVideo = deleteImg(cdUrl, courseVO.getAttachmentName());
				if(flagImg && flagVideo) {
					coursewareServiceImpl.deleteAllVideos(Integer.parseInt(resourceId));
					return true;
				}
			}
        }
		return false;
	}

	/**
	 * 
	  * @Title: getCoursewareInfo
	  * @Description: 查询用一个视频所挂载目录的情况
	  * @author liuwei
	  * @date 2017年11月22日 下午2:13:02
	 */
	
	@Override
	public JSONObject getCoursewareInfo(CoursewareVO course) {
		JSONObject result = new JSONObject();
		if(StringUtils.isNotBlank(course.getResourceId())) {
			List<CoursewareVO> datalist = Collections.emptyList();
			//取得数据
			datalist = coursewareServiceImpl.findCourseware(course);
			
			
			if(CollectionUtils.isNotEmpty(datalist)) {
				CoursewareVO videoVO = datalist.get(0);
				List<String> courseNames = new ArrayList<String>(); //课程名称集合
				List<String> courseCodes = new ArrayList<String>(); //课程编码集合
				for(CoursewareVO tempVO : datalist){
					courseCodes.add(tempVO.getCourseCode());
					courseNames.add(tempVO.getCourseName());
				}
				videoVO.setCourseCodes(courseCodes);
				videoVO.setCourseNames(courseNames);
				
				String ipAddress = getCacheValue(EnumConstants.IMG_SERVICE_IP);
				result.put("ip", ipAddress);
				result.put("videoUrl", DataDricCommon.getValueByKey("video.file"));
		        result.put("rows", videoVO);
				return result;
			}
		}
		return result;
	}
	
	
}
