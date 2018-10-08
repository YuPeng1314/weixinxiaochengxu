package com.huayu.irla.manage.application.activeManage.impl;

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
import com.huayu.irla.core.active.vo.ActiveVO;
import com.huayu.irla.core.service.active.IActiveService;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.manage.application.activeManage.IActiveManage;
import com.huayu.irla.manage.util.UserUtils;
/**
 * 小程序活动接口实现类
 * @author 顾广婷
 *
 */
@Component("activeManage")
public class ActiveManageImpl implements IActiveManage {
	
	private static final Logger logger = Logger.getLogger(ActiveManageImpl.class);
	
	@Autowired
	private IActiveService activeService;
	
	@Value("${phizImg.file.path}")
	private String imgFilePath;
	
	@Override
	public JSONObject findActive(ActiveVO active) {
		if(active==null) {
			active=new ActiveVO();
		}
		List<ActiveVO> activeList = activeService.getActiveList(active);
		for(ActiveVO activeVO : activeList) {
			activeVO.setActiveImg(DataDricCommon.getValueByKey("img.http.ip") +DataDricCommon.getValueByKey("phiz.file")+ activeVO.getActiveImg());
	    }
        Integer count = activeService.getActiveCount(active);
        active = activeList.get(0);
        JSONObject result = new JSONObject();
        result.put("active", active);
        result.put("rows", JSONArray.toJSON(activeList));
        result.put("total", count);
        return result;
	}

	@Override
	public boolean addActive(ActiveVO active) {
		try {
			String userName = UserUtils.getLoginName();
			active.setCreatedBy(userName);
			active.setLastUpdatedBy(userName);
			activeService.addActive(active);
		} catch (Exception e) {
			logger.error("新增活动失败!", e);
			return false;
		}
        return true;
	}

	@Override
	public Integer deleteActive(Integer id) throws Exception {
		if (id == null) {
			return 1;
        }
		ActiveVO active = new ActiveVO();
		active.setId(id);
		List<ActiveVO> activeList = activeService.getActiveList(active);
		active = activeList.get(0);
		String imgName = active.getActiveImg();
		if(imgName != null) {
			deleteImg(imgFilePath, imgName);
		}
		activeService.deleteActive(id);
		return 0;
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

	@Override
	public boolean updateActive(ActiveVO active) {
		String userName = UserUtils.getLoginName();
		active.setCreatedBy(userName);
		active.setLastUpdatedBy(userName);
		active.setIsValid("1");
		activeService.updateActive(active);
        return true;
	}

	@Override
	public String validActive(Integer id, String state) {
		if (id != null && StringUtils.isNotBlank(state)) {
			ActiveVO active=new ActiveVO();
			active.setId(id);
			active = activeService.getActiveList(active).get(0);
			if (active != null) {
				String isValid = null;
				// 判断state状态，若为false则存入0，若为true则存入1
				if ("false".equals(state)) {
					isValid = "0";
				} else {
					isValid = "1";
				}
				active.setIsValid(isValid);
				activeService.updateActive(active);
				// 操作成功
				return "1";
			}
		}
		// 参数为空值,删除失败
		return "0";
	}

	@Override
	public boolean saveActiveImg(List<Attachment> attachments, ActiveVO active) {
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
					if (active != null) {
						// 取得数据
						List<ActiveVO> daptlist = activeService.getActiveList(active);
						if (CollectionUtils.isNotEmpty(daptlist)) {
							ActiveVO tempVO = daptlist.get(0);
							if (tempVO != null) {
								if (StringUtils.isNotBlank(tempVO.getActiveImg())) {
									imgName = tempVO.getActiveImg();
									boolean sucess = deleteImg(imgFilePath, imgName);
									if (sucess) {
										tempVO.setLastUpdatedBy(userName);
										tempVO.setActiveImg(fileUrl);
										activeService.updateActive(tempVO);
										return true;
									}
								} else {
									tempVO.setLastUpdatedBy(userName);
									tempVO.setActiveImg(fileUrl);
									activeService.updateActive(tempVO);
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
	public String getCacheValue(String sysKey) {
        return DataDricCommon.getValueByKey(sysKey, "");
    }
}
