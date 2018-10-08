package com.huayu.irla.manage.application.carouselManage.impl;

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
import com.huayu.irla.core.carousel.vo.CarouselVO;
import com.huayu.irla.core.service.carousel.ICarouselService;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.manage.application.carouselManage.IcarouselManage;
import com.huayu.irla.manage.util.UserUtils;
/**
 * @Description:权限接口实现类
 * @author GuGuangTing
 *
 */
@Component("carouselmanage")
public class CarouselManageImpl implements IcarouselManage {
	private static final Logger logger = Logger.getLogger(CarouselManageImpl.class);
	
	@Autowired
	private ICarouselService carouselService;
	@Value("${courseImg.file.path}")
	private String imgFilePath;
	@Override
	public JSONObject findCarouse(CarouselVO carousel) {
		if(carousel==null) {
			carousel=new CarouselVO();
		}
		List<CarouselVO> carouselList=carouselService.findCarousel(carousel);
		Integer count=carouselService.getCarouselCount(carousel);
		JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(carouselList));
        result.put("total", count);
        result.put("resUrl", DataDricCommon.getValueByKey("res.file"));
		result.put("ip", getCacheValue(EnumConstants.IMG_SERVICE_IP));
        return result;
	}

	private String getCacheValue(String sysKey) {
		return DataDricCommon.getValueByKey(sysKey, "");
	}
	
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean updateCarousel(CarouselVO carousel) {
		 String userName = UserUtils.getLoginName();
	        boolean flag = true;
	        try {
	        	carousel.setLastUpdatedBy(userName);
	        	carousel.setIsValid("0");
	        	carouselService.updateCarousel(carousel);
	        } catch (Exception e) {
	            logger.error("更新失败", e);
	            flag = false;
	        }
	        return flag;
	}
	
	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean deletePagebyId(CarouselVO carousel) {
		if (StringUtils.isBlank(carousel.getId())) {
            return false;
        }
		try {
			carousel=carouselService.findCarousel(carousel).get(0);
			deleteImg(carousel.getCarouselImgUrl(),imgFilePath);
			carouselService.deleteCarousel(Integer.parseInt(carousel.getId()));
		} catch (Exception e) {
	        logger.error("删除失败!", e);
	        return false;
	    }
	    return true;
	}
	/**
     * 删除指定的文件
     * @param delimgName 原始旧的图片路径名称
     */
    private boolean deleteImg(String delimgName, String rootUrl) {
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
	@Transactional(rollbackFor = Exception.class)
	public boolean addCarousel(List<Attachment> attachments, CarouselVO carousel) {
		try {
			if (attachments.isEmpty()) {
	            return false;
	        }
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
							if (StringUtils.isBlank(fileUrl)) {
				                return false;
				            }
							String userName = UserUtils.getLoginName();
					        // 上传服务器成功后将信息插入数据库
					        carousel.setCarouselTitle(serialName);
					        carousel.setCarouselImgUrl(fileUrl);
					        carousel.setCreatedBy(userName);
					        carousel.setLastUpdatedBy(userName);
					    	carouselService.addCarousel(carousel);
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
	@Override
	public CarouselVO getCarouselInfo(String homepage_id) {
		if (StringUtils.isBlank(homepage_id)) {
            return null;
        }
		CarouselVO carousel = new CarouselVO();
		carousel.setId(homepage_id);
        try {
            //取得数据
            List<CarouselVO> carouselList = carouselService.findCarousel(carousel);
            if (CollectionUtils.isEmpty(carouselList)) {
                logger.error("空指针异常");
                return null;
            }
            carousel = carouselList.get(0);
            return carousel;
        } catch (Exception e) {
            logger.error("修改失败", e);
        }
        return null;
	}

	@Override
	public boolean checkName(CarouselVO carousel) {
		boolean flag = true;
        if (StringUtils.isBlank(carousel.getCarouselTitle())) {
            flag = false;
        }
        try {
            List<CarouselVO> carouselList = carouselService.findCarousel(null);
            for (CarouselVO carouselTempVO : carouselList) {
                if (StringUtils.equalsIgnoreCase(carousel.getCarouselTitle(), carouselTempVO.getCarouselTitle())) {
                    flag = false;
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("校验失败", e);
            flag = false;
        }
        return flag;
	}

	@Override
	public boolean validPublish(Integer id, String state) {
		if (id == null) {
            return false;
        }
		CarouselVO carousel = new CarouselVO();
		carousel.setId(String.valueOf(id));
        try {
            //取得数据
            List<CarouselVO> carouselList = carouselService.findCarousel(carousel);
            CarouselVO carouseln = null;
            if (CollectionUtils.isEmpty(carouselList)) {
                logger.error("空指针异常");
                return false;
            }
            carouseln = carouselList.get(0);
            //判断state状态，若为false，则存入0，若为true,则存入1
            if ("false".equals(state)) {
            	carouseln.setIsValid("0");
            } else {
            	carouseln.setIsValid("1");
            }
            carouselService.updateCarousel(carouseln);
        } catch (Exception e) {
            logger.error("发布失败", e);
            return false;
        }
        return true;
	}

}
