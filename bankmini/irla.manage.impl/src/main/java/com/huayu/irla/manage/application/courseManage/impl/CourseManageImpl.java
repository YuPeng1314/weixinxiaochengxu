package com.huayu.irla.manage.application.courseManage.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import javax.activation.DataHandler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
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
import com.huayu.irla.core.course.vo.CourseVO;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.core.service.courseware.ICoursewareService;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.manage.application.courseManage.ICourseManage;
import com.huayu.irla.manage.service.courseManage.ICourseManageService;
import com.huayu.irla.manage.util.UserUtils;
@Component("coursemanage")
public class CourseManageImpl implements ICourseManage {
	
	private static final Logger logger = Logger.getLogger(CourseManageImpl.class);
	
	@Autowired
	private ICourseManageService courseManageService;
	
	@Autowired
	private ICoursewareService coursewareService;
	
	@Value("${courseImg.file.path}")
	private String imgFilePath;
	
	@Override
	public JSONObject findCourse(CourseVO course) {
		if(course==null) {
			course=new CourseVO();
		}
		List<CourseVO> courseList = courseManageService.findCourse(course);
        Integer count = courseManageService.getCourseCount(course);
        JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(courseList));
        result.put("total", count);
        result.put("resUrl", DataDricCommon.getValueByKey("res.file"));
		result.put("ip", getCacheValue(EnumConstants.IMG_SERVICE_IP));
        return result;
	}
	//有图片课程修改
	private boolean updateCourse(List<Attachment> attachments, CourseVO course, String userName) {
		try {
			//获取旧课程图片
			CourseVO courseNew = new CourseVO();
			courseNew.setCourse_code(course.getCourse_code());
			courseNew=courseManageService.findCourse(courseNew).get(0);
			String courseImg = courseNew.getCourse_img();
			// 删除图片
			String rootUrl = imgFilePath;
			boolean sucess = true;
			if (!StringUtils.isEmpty(courseImg)) {
				sucess = deleteImg(rootUrl, courseImg);
			}
			if (sucess) {
				// 重新上传图片
				if (!attachments.isEmpty()) {
					//附件名称
					Attachment attachment = attachments.get(0);
					DataHandler dh = attachment.getDataHandler();
					
					// 获取文件中文名，转码的
					String imgName = new String(dh.getName().getBytes(EnumConstants.IETL_CODE_ISO),	EnumConstants.IETL_CODE_UTF8);
					if (StringUtils.isNotBlank(imgName)) {
						//文件名称
						String serialName = Calendar.getInstance().getTimeInMillis() + ".jpg" ;
						//文件全路径
						String fileUrl = getCacheValue(EnumConstants.HOMEPAGE_UPLOAD_PATH) + serialName.replace(" ", "");
						
						//创建存放的文件夹
						File file = new File(imgFilePath+getCacheValue(EnumConstants.HOMEPAGE_UPLOAD_PATH));
						if(!file.exists()) {
							file.mkdirs();
						}
						
						//如有附件，上传图片，写入数据库
						if (dh.getDataSource() != null) {
							FileOutputStream outputStream = null;
							InputStream stream = null;
						    try {
						    	InputStream is = dh.getDataSource().getInputStream();
								stream = ImageFormatConvert.convertJPEGInputStream(is);
							
								outputStream = new FileOutputStream(imgFilePath + fileUrl);
								
								//输入流写入输出流中
								IOUtils.copy(stream, outputStream);
								
								//文件上传成功，加入数据库
								course.setCourse_img(fileUrl);
								course.setIsValid("1");
								course.setLastUpdatedBy(userName);
								courseManageService.updateCourse(course);
						    } finally {
						    	IOUtils.closeQuietly(stream);
						    	IOUtils.closeQuietly(outputStream);
						    }
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("操作失败!", e);
			return false;
		}
		return true;
	}
	//有图片课程新增
	private boolean addCourse(List<Attachment> attachments, CourseVO course, String userName) {
		try {
			if (!attachments.isEmpty()) {
				//附件名称
				Attachment attachment = attachments.get(0);
				DataHandler dh = attachment.getDataHandler();
				
				// 获取文件中文名，转码的
				String imgName = new String(dh.getName().getBytes(EnumConstants.IETL_CODE_ISO),	EnumConstants.IETL_CODE_UTF8);
				if (StringUtils.isNotBlank(imgName)) {
					//文件名称
					String serialName = Calendar.getInstance().getTimeInMillis() + ".jpg" ;
					//文件全路径
					String fileUrl = getCacheValue(EnumConstants.HOMEPAGE_UPLOAD_PATH) + serialName.replace(" ", "");
					
					//创建存放的文件夹
					File file = new File(imgFilePath+getCacheValue(EnumConstants.HOMEPAGE_UPLOAD_PATH));
					if(!file.exists()) {
						file.mkdirs();
					}
					
					//如有附件，上传图片，写入数据库
					if (dh.getDataSource() != null) {
						FileOutputStream outputStream = null;
						InputStream stream = null;
					    try {
					    	InputStream is = dh.getDataSource().getInputStream();
							stream = ImageFormatConvert.convertJPEGInputStream(is);
						
							outputStream = new FileOutputStream(imgFilePath + fileUrl);
							
							//输入流写入输出流中
							IOUtils.copy(stream, outputStream);
							
							//文件上传成功，加入数据库
							course.setCourse_img(fileUrl);
							course.setCreatedBy(userName);
							course.setLastUpdatedBy(userName);
							courseManageService.addCourse(course);
					    } finally {
					    	IOUtils.closeQuietly(stream);
					    	IOUtils.closeQuietly(outputStream);
					    }
					}
				}
			}
		} catch (Exception e) {
			logger.error("操作失败!", e);
			return false;
		}
		return true;
	}
	
	private String getCacheValue(String sysKey) {
		return DataDricCommon.getValueByKey(sysKey, "");
	}
	
	private boolean deleteImg(String rootUrl, String delimgName) {
		if (StringUtils.isBlank(delimgName)) {
			return false;
		}
		String delUrl = rootUrl + delimgName;
		try {
			File delFile = new File(delUrl);
			delFile.delete();
		} catch (Exception e) {
			logger.error("删除原始文件异常", e);
			return false;
		}
		return true;
	}
	//有图片课程操作
	@Override
	@Transactional
	public boolean optCourse(List<Attachment> attachments, CourseVO course) {
		String code = course.getCourse_code();
		String userName = UserUtils.getLoginName();
		if (StringUtils.isBlank(code)) {
			return addCourse(attachments, course, userName);
		} else {
			return updateCourse(attachments, course, userName);
		}
	}
	@Override
	@Transactional
	public boolean deleteCourse(CourseVO course) throws Exception {
		if (course.getId() == null) {
			throw new Exception("参数id传递错误！");
        }
		CoursewareVO resourse = new CoursewareVO();
		course = courseManageService.findCourse(course).get(0);
		resourse.setCourseCode(course.getCourse_code());
		List<CoursewareVO> resourseList = coursewareService.findCourseware(resourse);
		if (!CollectionUtils.isEmpty(resourseList)||resourseList.size()>0) {
            return false;
        }
    	String courseImg = course.getCourse_img();
		String rootUrl = imgFilePath;
		deleteImg(rootUrl, courseImg);
		courseManageService.deleteCategory(course.getId());
		return true;
	}


	@Override
	public String validCourse(Integer id, String state) {
		if (id != null && StringUtils.isNotBlank(state)) {
			CourseVO course=new CourseVO();
			course.setId(id);
			course = courseManageService.findCourse(course).get(0);
			CoursewareVO resourse = new CoursewareVO();
			resourse.setCourseCode(course.getCourse_code());
			List<CoursewareVO> list = coursewareService.findCourseware(resourse);
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getSequenceNumber()==null) {
					return "3";
				}
			}
			if (course != null) {
				String isValid = null;
				// 判断state状态，若为false则存入0，若为true则存入1
				if ("false".equals(state)) {
					isValid = "0";
				} else if(list.size() > 0){
					isValid = "1";
				} else {
					isValid = "0";
					return "2";
				}
				course.setIsValid(isValid);
				courseManageService.updateCourse(course);
				// 操作成功
				return "1";
			}
		}
		// 参数为空值,删除失败
		return "0";
	}
	@Override
	public boolean updateCourse(CourseVO course) {
		try {
			String userName = UserUtils.getLoginName();		
			course.setLastUpdatedBy(userName);
			courseManageService.updateCourse(course);
			return true;
		} catch (Exception e) {
			logger.error("操作失败!", e);
			return false;
		}
	}
}
