package com.huayu.irla.manage.application.holiday.impl;

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
import com.huayu.irla.core.holiday.vo.HolidayVO;
import com.huayu.irla.core.phiz.vo.PhizVO;
import com.huayu.irla.core.service.holiday.IHolidayService;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.core.service.phiz.IPhizService;
import com.huayu.irla.manage.application.holiday.IHolidayManage;
import com.huayu.irla.manage.util.UserUtils;
/**
 * 节日权限接口实现类
 * @author ggt
 *
 */
@Component("holidaymanage")
public class HolidayManageImpl implements IHolidayManage {

	private static final Logger logger = Logger.getLogger(HolidayManageImpl.class);
	
	@Autowired
	private IHolidayService holidayService;
	
	@Autowired
	private IPhizService phizService;
	
	@Value("${phizImg.file.path}")
	private String imgFilePath;
	
	private String getCacheValue(String sysKey) {
		return DataDricCommon.getValueByKey(sysKey, "");
	}
	
	@Override
	public JSONObject findHoliday(HolidayVO holiday) {
		if(holiday==null) {
			holiday=new HolidayVO();
		}
		List<HolidayVO> holidayList = holidayService.getHolidayList(holiday);
        Integer count = holidayService.getHolidayCount(holiday);
        JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(holidayList));
        result.put("total", count);
        return result;
	}

	@Override
	public boolean addHoliday(List<Attachment> attachments,HolidayVO holiday) {
		String userName = UserUtils.getLoginName();
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
							holiday.setHolidayImg(fileUrl);
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
		holiday.setCreatedBy(userName);
		holiday.setLastUpdatedBy(userName);
        Integer num = holidayService.addHoliday(holiday);
        if (num > 0) {
            return true;
        }
        return false;
	}

	@Override
	public Integer delHoliday(Integer id) {
		if(id==null) {
			return 1;
		}
		HolidayVO holiday = new HolidayVO();
		holiday.setId(id);
		holiday = holidayService.getHolidayList(holiday).get(0);
		PhizVO phiz = new PhizVO();
		phiz.setHolidayCode(holiday.getHolidayCode());
		List<PhizVO> phizList = phizService.getPhizList(phiz);
		if(!CollectionUtils.isEmpty(phizList)||phizList.size()>0) {
			return 2;
		}
		// 删除图片
		if (!StringUtils.isBlank(holiday.getHolidayImg())) {
			String delUrl = imgFilePath + holiday.getHolidayImg();
			File delFile = new File(delUrl);
			delFile.delete();
		}
		holidayService.delHoliday(id);
		return 0;
	}

	@Override
	public boolean updateHoliday(List<Attachment> attachments,HolidayVO holiday) {
		String userName = UserUtils.getLoginName();
		try {
			//获取节日旧图片
			HolidayVO holidayNew = new HolidayVO();
			holidayNew.setId(holiday.getId());
			holidayNew = holidayService.getHolidayList(holidayNew).get(0);
			// 删除图片
			String delUrl = imgFilePath + holidayNew.getHolidayImg();
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
							holiday.setHolidayImg(fileUrl);
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
		holiday.setLastUpdatedBy(userName);
		Integer num=holidayService.updateHoliday(holiday);
		if (num > 0) {
            return true;
        }
        return false;
	}

	@Override
	public boolean updateHolidayNoImg(HolidayVO holiday) {
		String userName = UserUtils.getLoginName();
		holiday.setLastUpdatedBy(userName);
		Integer num=holidayService.updateHoliday(holiday);
		if (num > 0) {
            return true;
        }
        return false;
	}

}
