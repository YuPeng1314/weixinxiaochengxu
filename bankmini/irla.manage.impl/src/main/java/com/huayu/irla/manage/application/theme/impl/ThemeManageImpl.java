package com.huayu.irla.manage.application.theme.impl;

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
import com.huayu.irla.core.phiz.vo.PhizVO;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.core.service.phiz.IPhizService;
import com.huayu.irla.core.service.theme.IThemeService;
import com.huayu.irla.core.theme.vo.ThemeVO;
import com.huayu.irla.manage.application.theme.IThemeManage;
import com.huayu.irla.manage.util.UserUtils;
/**
 * 主题权限接口实现类
 * @author ggt
 *
 */
@Component("thememanage")
public class ThemeManageImpl implements IThemeManage {

	private static final Logger logger = Logger.getLogger(ThemeManageImpl.class);
	
	@Autowired
	private IThemeService themeService;
	
	@Autowired
	private IPhizService phizService;
	
	@Value("${phizImg.file.path}")
	private String imgFilePath;
	
	private String getCacheValue(String sysKey) {
		return DataDricCommon.getValueByKey(sysKey, "");
	}
	
	@Override
	public JSONObject getTheme(ThemeVO theme) {
		if(theme==null) {
			theme=new ThemeVO();
		}
		List<ThemeVO> themeList = themeService.getThemeList(theme);
        Integer count = themeService.getThemeCount(theme);
        JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(themeList));
        result.put("total", count);
        return result;
	}

	@Override
	public boolean addTheme(List<Attachment> attachments,ThemeVO theme) {
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
							theme.setThemeImg(fileUrl);
							String userName = UserUtils.getLoginName();
							theme.setCreatedBy(userName);
							theme.setLastUpdatedBy(userName);
					        Integer num = themeService.addTheme(theme);
					        if (num > 0) {
					            return true;
					        }
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
        return false;
	}

	@Override
	public boolean updateTheme(List<Attachment> attachments,ThemeVO theme) {
		String userName = UserUtils.getLoginName();
		try {
			//获取主题旧图片
			ThemeVO themeNew = new ThemeVO();
			themeNew.setId(theme.getId());
			themeNew = themeService.getThemeList(themeNew).get(0);
			// 删除图片
			String delUrl = imgFilePath + themeNew.getThemeImg();
			File delFile = new File(delUrl);
			delFile.delete();
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
							theme.setThemeImg(fileUrl);
					    } finally {
					    	IOUtils.closeQuietly(stream);
					    	IOUtils.closeQuietly(outputStream);
					    }
					}
				}
			}
		} catch (Exception e) {
			logger.error("操作失败!", e);
		}
		theme.setLastUpdatedBy(userName);
		Integer num=themeService.updateTheme(theme);
		if (num > 0) {
            return true;
        }
        return false;
	}

	@Override
	public Integer delTheme(Integer id) throws Exception {
		if(id==null) {
			return 1;
		}
		ThemeVO theme = new ThemeVO();
		theme.setId(id);
		theme = themeService.getThemeList(theme).get(0);
		PhizVO phiz = new PhizVO();
		phiz.setHolidayCode(theme.getThemeCode());
		List<PhizVO> phizList = phizService.getPhizList(phiz);
		if(!CollectionUtils.isEmpty(phizList)||phizList.size()>0) {
			return 2;
		}
		// 删除图片
		if (!StringUtils.isBlank(theme.getThemeImg())) {
			String delUrl = imgFilePath + theme.getThemeImg();
			File delFile = new File(delUrl);
			delFile.delete();
		}
		themeService.delTheme(id);
		return 0;
	}

	@Override
	public boolean updateThemeNoImg(ThemeVO theme) {
		String userName = UserUtils.getLoginName();
		theme.setLastUpdatedBy(userName);
		Integer num=themeService.updateTheme(theme);
		if (num > 0) {
            return true;
        }
        return false;
	}
	

}
