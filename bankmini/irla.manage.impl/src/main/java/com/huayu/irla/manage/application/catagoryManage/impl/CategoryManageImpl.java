package com.huayu.irla.manage.application.catagoryManage.impl;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.core.util.EnumConstants;
import com.huayu.core.util.ImageFormatConvert;
import com.huayu.irla.core.category.vo.CategoryVO;
import com.huayu.irla.core.course.vo.CourseVO;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.manage.application.categoryManage.ICategoryManage;
import com.huayu.irla.manage.service.categoryManage.ICategoryManageService;
import com.huayu.irla.manage.service.courseManage.ICourseManageService;
import com.huayu.irla.manage.util.UserUtils;

/**
 * 
 * @author GuGuangting
 *
 */
@Component("categorymanage")
public class CategoryManageImpl implements ICategoryManage {
	
	private static final Logger logger = Logger.getLogger(CategoryManageImpl.class);
	
	@Autowired
	private ICategoryManageService categoryManageService;
	
	@Autowired
	private ICourseManageService courseManageService;
	
	@Value("${phizImg.file.path}")
	private String imgFilePath;
	
	@Override
	public JSONObject findCategory(CategoryVO category) {
		if(category==null) {
			category=new CategoryVO();
		}
		List<CategoryVO> categoryList = categoryManageService.findCategory(category);
		for(CategoryVO categoryVO : categoryList) {
			categoryVO.setCategory_img(DataDricCommon.getValueByKey("img.http.ip") +DataDricCommon.getValueByKey("phiz.file")+ categoryVO.getCategory_img());
	    }
        Integer count = categoryManageService.getCategoryCount(category);
        category = categoryList.get(0);
        JSONObject result = new JSONObject();
        result.put("category", category);
        result.put("rows", JSONArray.toJSON(categoryList));
        result.put("total", count);
        return result;
	}

	@Override
	public boolean addCategory(CategoryVO category) {
		String userName = UserUtils.getLoginName();
        category.setCreatedBy(userName);
        category.setLastUpdatedBy(userName);
        if(category.getSequence_number()==null) {
        	category.setSequence_number(999);
        }
        Integer num = categoryManageService.addCategory(category);
        if (num > 0) {
            return true;
        }
        return false;
	}

	@Override
	public Integer deleteCategory(CategoryVO category) throws Exception {
		if (category.getId() == null) {
			throw new Exception("参数id传递错误！");
        }
		CourseVO course = new CourseVO();
		List<CategoryVO> categoryList = categoryManageService.findCategory(category);
		if(categoryList.get(0).getCategory_level()==1) {
			CategoryVO category1 = new CategoryVO();
			category1.setParent_category_code(categoryList.get(0).getCategory_code());
			List<CategoryVO> categoryList1 = categoryManageService.findCategory(category1);
			if (!CollectionUtils.isEmpty(categoryList1)||categoryList1.size()>0) {
	            return 1;
	        }
		}else {
			course.setCategory_code(categoryList.get(0).getCategory_code());
			List<CourseVO> courseList = courseManageService.findCourse(course);
			if (!CollectionUtils.isEmpty(courseList)||courseList.size()>0) {
	            return 2;
	        }
		}
		String imgName = category.getCategory_img();
		if(imgName != null) {
			deleteImg(imgFilePath, imgName);
		}
		categoryManageService.deleteCategory(category.getId());
		return 0;
	}

	@Override
	public boolean updateCategory(CategoryVO category) {
		String userName = UserUtils.getLoginName();
        category.setCreatedBy(userName);
        category.setLastUpdatedBy(userName);
        category.setIsValid("1");
		categoryManageService.updateCategory(category);
        return true;
	}

	@Override
	public String validCategory(Integer id, String state) {
		if (id != null && StringUtils.isNotBlank(state)) {
			CategoryVO category=new CategoryVO();
			category.setId(id);
			category = categoryManageService.findCategory(category).get(0);
			if(!StringUtils.equals(category.getParent_category_code(), "-1")) {
				CourseVO course = new CourseVO();
				course.setCategory_code(category.getCategory_code());
				Integer num = courseManageService.getCourseCount(course);
				if (category != null) {
					String isValid = null;
					// 判断state状态，若为false则存入0，若为true则存入1
					if ("false".equals(state)) {
						isValid = "0";
					} else if(num > 0){
						isValid = "1";
					} else {
						isValid = "0";
						return "2";
					}
					category.setIsValid(isValid);
					categoryManageService.updateCategory(category);
					// 操作成功
					return "1";
				}
			}else {
				CategoryVO category1 = new CategoryVO();
				category1.setParent_category_code(category.getCategory_code());
				Integer num = categoryManageService.getCategoryCount(category1);
				String isValid = null;
				// 判断state状态，若为false则存入0，若为true则存入1
				if ("false".equals(state)) {
					isValid = "0";
				} else if(num > 0){
					isValid = "1";
				} else {
					isValid = "0";
					return "2";
				}
				category.setIsValid(isValid);
				categoryManageService.updateCategory(category);
				// 操作成功
				return "1";
			}
			
		}
		// 参数为空值,删除失败
		return "0";
	}
	public String getCacheValue(String sysKey) {
        return DataDricCommon.getValueByKey(sysKey, "");
    }
	@Override
	public boolean saveCategoryImg(List<Attachment> attachments, CategoryVO category) {
		String userName = UserUtils.getLoginName();
		try {
			if (!attachments.isEmpty()) {
				boolean flag = false;
				String fileUrl = null;
				//附件名称
				Attachment attachment = attachments.get(0);
				DataHandler dh = attachment.getDataHandler();
				// 获取文件中文名，转码的
				String imgName = new String(dh.getName().getBytes(EnumConstants.IETL_CODE_ISO),	EnumConstants.IETL_CODE_UTF8);
				if (StringUtils.isNotBlank(imgName)) {
					//文件名称
					String serialName = Calendar.getInstance().getTimeInMillis() + ".jpg" ;
					//文件全路径
					fileUrl = getCacheValue(EnumConstants.HOMEPAGE_UPLOAD_PATH) + serialName.replace(" ", "");
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
							flag = true;
					    } finally {
					    	IOUtils.closeQuietly(stream);
					    	IOUtils.closeQuietly(outputStream);
					    }
					}
				}
				if (flag) {
					if (category != null) {
						// 取得数据
						List<CategoryVO> daptlist = categoryManageService.findCategory(category);
						if (CollectionUtils.isNotEmpty(daptlist)) {
							CategoryVO tempVO = daptlist.get(0);
							if (tempVO != null) {
								if (StringUtils.isNotBlank(tempVO.getCategory_img())) {
									imgName = tempVO.getCategory_img();
									boolean sucess = deleteImg(imgFilePath, imgName);
									if (sucess) {
										tempVO.setLastUpdatedBy(userName);
										tempVO.setCategory_img(fileUrl);
										categoryManageService.updateCategory(tempVO);
										return true;
									}
								} else {
									tempVO.setLastUpdatedBy(userName);
									tempVO.setCategory_img(fileUrl);
									categoryManageService.updateCategory(tempVO);
									return true;
								}
							}
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
	

}
