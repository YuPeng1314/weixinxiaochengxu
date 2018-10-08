package com.huayu.core.util;
import java.io.File;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import it.sauronsoftware.jave.MultimediaInfo;
import it.sauronsoftware.jave.VideoInfo;
import it.sauronsoftware.jave.Encoder;
public class ReadVideoMessages {

	private static final Logger logger = Logger.getLogger(ReadVideoMessages.class);
	/**
	 * 得到视频的大小
	 * 
	 * @param f
	 *            文件
	 * @return 视频的大小
	 */
	public static String getFileSize(File f) {
		// 保留两位小数
		DecimalFormat df = new DecimalFormat(".##");
		// 得到视频的长度
		Long long1 = f.length();
		String size = "";
		long G = 1024 * 1024 * 1024;
		long M = 1024 * 1024;
		long K = 1024;
		// 视频大小超过G、超过M不超过G、超过K不超过M
		if (long1 / G >= 1) {
			size = df.format((double) long1 / G) + "G";
		} else if (long1 / M >= 1) {
			size = df.format((double) long1 / M) + "M";
		} else if (long1 / K >= 1) {
			size = df.format((double) long1 / K) + "K";
		} else {
			size = long1 + "B";
		}
		return size;

	}
	
	/**
	 * 得到视频的大小,M为单位
	 * 
	 * @param f
	 *            文件
	 * @return 视频的大小
	 */
	public static String getFileSizeK(File f) {
		// 保留两位小数
		DecimalFormat df = new DecimalFormat(".##");
		// 得到视频的长度
		Long long1 = f.length();
		String size = "";
		long K = 1024;
		// 视频大小用KB标识
		size = df.format((double)long1/K);
		return size;
	}

	/**
	 * 得到视频的长度
	 * 
	 * @param f
	 *            文件
	 * @return 视频的长度
	 */
	public static String getVideoTime(File f) {
		String time = "";
		//新建编码器对象
		Encoder encoder = new Encoder();
		try {
			//得到多媒体视频的信息
			MultimediaInfo m = encoder.getInfo(f);
			//得到毫秒级别的多媒体是视频长度
			long ls = m.getDuration();
			//转换为分秒
			time = ls / 60000 + "分" + (ls - (ls / 60000 * 60000)) / 1000 + "秒";
		} catch (Exception e) {
			logger.error(e);
		}

		return time;

	}
	
	/**
	 * 
	  * @Title: getVideoTime
	  * @Description: 得到视频的毫秒
	  * @author liuwei
	  * @date 2017年5月26日 下午4:53:06
	  * @param f
	  * @return
	 */
	public static String getVideoLength(File f) {
		String time = "";
		//新建编码器对象
		Encoder encoder = new Encoder();
		try {
			//得到多媒体视频的信息
			MultimediaInfo m = encoder.getInfo(f);
			//得到毫秒级别的多媒体是视频长度
			long ls = m.getDuration();
			//转换为分秒
			time = String.valueOf(ls/1000);
		} catch (Exception e) {
			logger.error(e);
		}

		return time;

	}

	/**
	 * 判断是否为音频还是视频
	 * @param f
	 * @return
	 */
	public static boolean isVideo(String filePath) {
		File tmpFile = new File(filePath);
		return isVideo(tmpFile);
	}
	/**
	 * 判断是否为音频还是视频
	 * @param f
	 * @return
	 */
	public static boolean isVideo(File f) {
		//新建编码器对象
		Encoder encoder = new Encoder();
		try {
			//得到多媒体视频的信息
			MultimediaInfo m = encoder.getInfo(f);
			//得到毫秒级别的多媒体是视频长度
			VideoInfo vinfo = m.getVideo();
			if(vinfo != null) {
				return true;
			}
			
			//转换为分秒
		} catch (Exception e) {
			logger.error(e);
		}

		return false;

	}
	
	public static void main(String[] args) {
		System.err.println(ReadVideoMessages.isVideo(new File("d:/zhidao.mp3")));
		System.err.println(ReadVideoMessages.isVideo(new File("F:\\U党员相关资料\\study-master\\app\\vendors\\video-test.mp4")));
		
	}

}
