package com.huayu.irla.manage.service.courseware.jms.consumer;

import java.io.File;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.huayu.core.util.ReadVideoMessages;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.core.courseware.vo.FileUploadItemVO;
import com.huayu.irla.core.dao.courseware.ICoursewareDao;
import com.huayu.irla.core.dao.resource.IResourceDao;
import com.huayu.irla.core.service.courseware.ICoursewareService;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.manage.service.courseware.jms.adapter.Product;
import com.huayu.irla.manage.service.courseware.jms.transcoding.Transcoding;

public class MessageConsumer {

	@Value("${codc.file.path}")
	private String codcFilePath;

	@Value("${breakpoint.upload.dir}")
	private String saveFilePath;

	@Autowired
	private ICoursewareDao coursewareDao;

	@Autowired
	private ICoursewareService coursewareServiceImpl;

	@Autowired
	private IResourceDao directoryDao;

	@Autowired
	private DataSourceTransactionManager transactionManager;


	public void receive(Product product) throws Exception {
		// 得到文件id
		Long fileId = product.getFileId();
		// 得到用户code
		String userCode = product.getUserCode();
		// 得到平台标识
		String platefromFlag = product.getPlateformCode();

		// 得到事务对象
		TransactionStatus status = getTranstationObj();
		try {
			// 得到文件和对应关系信息
			FileUploadItemVO fileItemVO = coursewareDao.getUploadFileById(fileId);

			// ffmpeg的路径
			String ffmpegPath = DataDricCommon.getValueByKey("ietl.ffmpeg.runpath", "D:/ffmpeg/bin/ffmpeg.exe");

			// 合并路径得到文件名
			String md5 = fileItemVO.getMd5();
			String upFilePath = saveFilePath + md5 + File.separator + md5 + "_tmp";
			String filePath = null;
			String picPath = null;
			if(ReadVideoMessages.isVideo(upFilePath)) {
				// 转视频路径
				filePath = transcodingVideo(ffmpegPath, upFilePath, md5);
			
				// 获取截图路径
				picPath = getScreenSnapImage(ffmpegPath, upFilePath, md5, fileItemVO.getScreenshotPos());
			} else {
				filePath = transcodingAudio(ffmpegPath, upFilePath, md5);
				//标志为音频
				fileItemVO.setResourceType("1");
			}

			// 更新转码后的路径和图片进入正式表,插入资源信息
			insertResInfo(fileItemVO, userCode, filePath, picPath, platefromFlag);
			transactionManager.commit(status);

		} catch (Exception ex) {
			transactionManager.rollback(status);
			// 得到事务对象,转码失败
			status = getTranstationObj();
			// 更新状态
			FileUploadItemVO itemVO = new FileUploadItemVO();
			// 状态: 0上传中，1等待转码，2转码等待中，3转码失败，4转码成功
			itemVO.setStatus("3");
			itemVO.setId(fileId);
			coursewareServiceImpl.updateUploadFile(itemVO);
			transactionManager.commit(status);
			Logger.getLogger(this.getClass()).error("转码失败", ex);
			throw ex;
		}

		// 得到事务对象,转码成功
		status = getTranstationObj();
		// 更新状态
		FileUploadItemVO itemVO = new FileUploadItemVO();
		// 状态: 0上传中，1等待转码，2转码等待中，3转码失败，4转码成功
		itemVO.setStatus("4");
		itemVO.setId(fileId);
		coursewareServiceImpl.updateUploadFile(itemVO);
		transactionManager.commit(status);
	}

	private TransactionStatus getTranstationObj() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		return status;
	}

	/**
	 * 得到转码后的文件,视频转mp4
	 * 
	 * @param ffmpegPath
	 * @param upFilePath
	 * @throws Exception
	 */
	private String transcodingVideo(String ffmpegPath, String upFilePath, String md5) throws Exception {
		String parmCoding = DataDricCommon.getValueByKey("ietl.videotranscoding.param",
				"-ab,128,-acodec,copy,-ac,1,-ar,22050,-r,29.97,-b:v,512k");
		Calendar newDateYm = Calendar.getInstance();
		// 年份
		int year = newDateYm.get(Calendar.YEAR);
		// 月份
		int month = newDateYm.get(Calendar.MONTH) + 1;

		// 上传的文件路径
		String saveFile = year + File.separator + month + File.separator + md5 + ".mp4";
		File file = new File(codcFilePath + year + File.separator + month + File.separator);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		if (Transcoding.executeCodecs(ffmpegPath, upFilePath, parmCoding, codcFilePath + saveFile)) {
			return saveFile;
		}
		return null;
	}
	
	/**
	 * 得到转码后的文件,音频转mp3
	 * 
	 * @param ffmpegPath
	 * @param upFilePath
	 * @throws Exception
	 */
	private String transcodingAudio(String ffmpegPath, String upFilePath, String md5) throws Exception {
		String parmCoding = DataDricCommon.getValueByKey("ietl.audiotranscoding.param",
				"-b:a,192k,-acodec,mp3,-ar,44100,-ac,2");
		Calendar newDateYm = Calendar.getInstance();
		// 年份
		int year = newDateYm.get(Calendar.YEAR);
		// 月份
		int month = newDateYm.get(Calendar.MONTH) + 1;

		// 上传的文件路径
		String saveFile = year + File.separator + month + File.separator + md5 + ".mp3";
		File file = new File(codcFilePath + year + File.separator + month + File.separator);
		if (!file.exists()) {
			file.mkdirs();
		}

		if (Transcoding.executeCodecs(ffmpegPath, upFilePath, parmCoding, codcFilePath + saveFile)) {
			return saveFile;
		}
		return null;
	}

	/**
	 * 得到转码后的图片
	 * 
	 * @param ffmpegPath
	 * @param upFilePath
	 * @throws Exception
	 */
	private String getScreenSnapImage(String ffmpegPath, String upFilePath, String md5, String screenSnptTime)
			throws Exception {
		String parmCoding = DataDricCommon.getValueByKey("ietl.videosceenpos.param", ",-vframes,1,-q:v,2,-f,image2");
		parmCoding = "-ss," + screenSnptTime + parmCoding;
		Calendar newDateYm = Calendar.getInstance();
		// 年份
		int year = newDateYm.get(Calendar.YEAR);
		// 月份
		int month = newDateYm.get(Calendar.MONTH) + 1;
		// 上传的文件路径
		String saveFile = year + File.separator + month + File.separator + md5 + ".jpg";
		File file = new File(codcFilePath + year + File.separator + month + File.separator);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 转图片
		if (Transcoding.executeCodecs(ffmpegPath, upFilePath, parmCoding, codcFilePath + saveFile)) {
			return saveFile;
		}
		return null;
	}

	private void insertResInfo(FileUploadItemVO fileItemVO, String userCode, String filePath, String picPath,
			String platefromFlag) {
		// 取到视频文件长度和大小
		File tmpFile = new File(codcFilePath + filePath);
		CoursewareVO coursewareVO = new CoursewareVO();
		String courseCodes = fileItemVO.getCourseCode();
		if (StringUtils.isBlank(courseCodes)) {
			throw new RuntimeException("资源code不能为空");
		}

		if (tmpFile.exists()) {
			String fileSize = ReadVideoMessages.getFileSizeK(tmpFile);
			String fileLen = ReadVideoMessages.getVideoLength(tmpFile);
			coursewareVO.setResourceSize(fileSize);
			coursewareVO.setResourceLength(fileLen);
			// 指定的时间，为空，超过时间，取第一帧
			String posTime = fileItemVO.getScreenshotPos();
			try {
				if (posTime == null) {
					fileItemVO.setScreenshotPos("1");
				} else if (posTime != null) {
					int posTimeInt = Integer.valueOf(posTime);
					if (posTimeInt > Integer.valueOf(fileLen)) {
						fileItemVO.setScreenshotPos(fileLen);
					}
				}
			} catch (Exception ex) {
				fileItemVO.setScreenshotPos("1");
			}
		}

		coursewareVO.setAppId(platefromFlag == null ? "PL" : platefromFlag);
		coursewareVO.setCreatedBy(userCode);
		coursewareVO.setLastUpdatedBy(userCode);
		coursewareVO.setIsPublic(fileItemVO.getIsPublic());
		coursewareVO.setDescription(fileItemVO.getDescription());
		// 0未发布，1已发布
		coursewareVO.setIsValid("1");
		// 视频类型
		coursewareVO.setResourceType("0");

		coursewareVO.setResourceName(fileItemVO.getResourceName());
		coursewareVO.setLearnTime(fileItemVO.getLearnTime());
		coursewareVO.setResourceOwner(fileItemVO.getResourceOwner());
		coursewareVO.setResourceType(fileItemVO.getResourceType());

		coursewareVO.setAttachmentName(filePath);
		coursewareVO.setImgName(picPath);

		long resourceID = directoryDao.getResourceID();
		coursewareVO.setResourceId(Long.toString(resourceID));
		
		String[] courses = courseCodes.split("[,]");
		for (String tmpCata : courses) {
			// 得到序列
			long resUid = directoryDao.getResourceSeq();
			String resCode = "RES" + resUid;
			coursewareVO.setResourceCode(resCode);
			coursewareVO.setCourseCode(tmpCata);
			coursewareDao.insertFormualRes(coursewareVO);
		}
	}
}
