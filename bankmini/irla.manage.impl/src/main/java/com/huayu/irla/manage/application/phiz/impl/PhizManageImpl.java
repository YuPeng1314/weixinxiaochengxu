package com.huayu.irla.manage.application.phiz.impl;

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
import com.huayu.irla.manage.application.phiz.IPhizManage;
import com.huayu.irla.manage.util.UserUtils;
/**
 * 表情权限接口实现类
 * @author ggt
 *
 */
@Component("phizmanage")
public class PhizManageImpl implements IPhizManage {
	private static final Logger logger = Logger.getLogger(PhizManageImpl.class);
	@Autowired
	private IPhizService phizService;
	
	@Value("${phizImg.file.path}")
	private String imgFilePath;
	
	@Override
	public JSONObject findPhiz(PhizVO phiz) {
		if(phiz==null) {
			phiz=new PhizVO();
		}
		List<PhizVO> phizList =phizService.getPhizList(phiz);
        Integer count = phizService.getPhizCount(phiz);
        JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(phizList));
        result.put("total", count);
        return result;
	}

	@Override
	public boolean addPhiz(List<Attachment> attachments, PhizVO phiz) {
		String userName = UserUtils.getLoginName();
		try {
			if (!attachments.isEmpty()) {
				//附件名称
				Attachment attachment = attachments.get(0);
				DataHandler dh = attachment.getDataHandler();
				
				// 获取文件中文名，转码的
				String imgName = new String(dh.getName().getBytes(EnumConstants.IETL_CODE_ISO),	EnumConstants.IETL_CODE_UTF8);
				if (StringUtils.isNotBlank(imgName)) {
					
					//创建存放的文件夹
					File file = new File(imgFilePath+getCacheValue(EnumConstants.HOMEPAGE_UPLOAD_PATH));
					if(!file.exists()) {
						file.mkdirs();
					}
					
					//如有附件，上传图片，写入数据库
					if (dh.getDataSource() != null) {
						FileOutputStream outputStream = null;
						InputStream stream = null;
						InputStream is = null;
						boolean isGif = false;
					    try {
					    	is = dh.getDataSource().getInputStream();
					    	
					    	
					    	//文件名称
							String serialName = null;
							//文件全路径
							String fileUrl = null;
							
					    	//是否为gif文件
							byte[] hadRead = new byte[4];
							is.read(hadRead, 0, hadRead.length);
					    	isGif = getPicType(hadRead);
					    	if(!isGif) {
					    		serialName = Calendar.getInstance().getTimeInMillis() + ".jpg" ;
					    		stream = ImageFormatConvert.convertJPEGInputStream(is, hadRead);
					    		fileUrl = getCacheValue(EnumConstants.HOMEPAGE_UPLOAD_PATH) + serialName.replace(" ", "");
					    	} else {
					    		serialName = Calendar.getInstance().getTimeInMillis() + ".gif" ;
					    		fileUrl = getCacheValue(EnumConstants.HOMEPAGE_UPLOAD_PATH) + serialName.replace(" ", "");
					    	}
							outputStream = new FileOutputStream(imgFilePath + fileUrl);
							
							if(!isGif) {
								//输入流写入输出流中
								IOUtils.copy(stream, outputStream);
							} else {
								outputStream.write(hadRead);
								//输入流写入输出流中
								IOUtils.copy(is, outputStream);
							}
							
							//文件上传成功，加入数据库
							phiz.setImgName(serialName);
							phiz.setImgAddress(fileUrl);
							phiz.setCreatedBy(userName);
							phiz.setLastUpdatedBy(userName);
							phizService.addPhiz(phiz);
					    } finally {
					    	IOUtils.closeQuietly(stream);
					    	IOUtils.closeQuietly(outputStream);
					    	if(isGif) {
					    		IOUtils.closeQuietly(is);
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
	
	private String getCacheValue(String sysKey) {
		return DataDricCommon.getValueByKey(sysKey, "");
	}

	@Override
	public Integer delPhiz(Integer id) throws Exception {
		if(id==null) {
			return 1;
		}
		PhizVO phiz = new PhizVO();
		phiz.setPhizId(id);
		phiz = phizService.getPhizList(phiz).get(0);
		if (!StringUtils.isBlank(phiz.getImgAddress())) {
			String delUrl = imgFilePath + phiz.getImgAddress();
			File delFile = new File(delUrl);
			delFile.delete();
		}
		phizService.delPhiz(id);
		return 0;
	}

	@Override
	public boolean updatePhiz(PhizVO phiz) {
		String userName = UserUtils.getLoginName();
		phiz.setLastUpdatedBy(userName);
		Integer num=phizService.updatePhiz(phiz);
		if (num > 0) {
            return true;
        }
        return false;
	}

	@Override
	public boolean validPublish(Integer id, String state) {
		if (id == null) {
            return false;
        }
		PhizVO phiz = new PhizVO();
		phiz.setPhizId(id);
        try {
            //取得数据
            List<PhizVO> phizlList = phizService.getPhizList(phiz);
            if (CollectionUtils.isEmpty(phizlList)) {
                logger.error("空指针异常");
                return false;
            }
            PhizVO phizNew = phizlList.get(0);
            //判断state状态，若为false，则存入0，若为true,则存入1
            if ("false".equals(state)) {
            	phizNew.setIsValid("0");
            } else {
            	phizNew.setIsValid("1");
            }
            phizService.updatePhiz(phizNew);
        } catch (Exception e) {
            logger.error("发布失败", e);
            return false;
        }
        return true;
	}

	/**
     * byte数组转换成16进制字符串
     * @param src
     * @return
     */
    private String bytesToHexString(byte[] src){    
       StringBuilder stringBuilder = new StringBuilder();    
       if (src == null || src.length <= 0) {    
           return null;    
       }    
       for (int i = 0; i < src.length; i++) {    
           int v = src[i] & 0xFF;    
           String hv = Integer.toHexString(v);    
           if (hv.length() < 2) {    
               stringBuilder.append(0);    
           }    
           stringBuilder.append(hv);    
       }    
       return stringBuilder.toString();    
    }
    
    /**
     * 根据文件流判断图片类型
     * @param fis
     * @return jpg/png/gif/bmp
     */
    private boolean getPicType(byte[] temp) {
        //读取文件的前几个字节来判断图片格式
        String type = bytesToHexString(temp).toUpperCase();
        if (type.contains("47494638")) {
            return true;
        }
            
        return false;
    }
}
