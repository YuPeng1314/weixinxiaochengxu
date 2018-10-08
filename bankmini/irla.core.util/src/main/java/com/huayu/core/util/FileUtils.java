package com.huayu.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * @author luozehua
 *
 * @time 2016年9月7日-下午6:47:45
 */
public class FileUtils {

	private static final Logger logger = Logger.getLogger(FileUtils.class);

	private FileUtils() {

	}

	/**
	 * 根据流写文件
	 * 
	 * @param in
	 * @param desFile
	 */

	public static boolean writeFile(InputStream in, File desFile) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(desFile);
			byte[] buffer = new byte[1024];
			int byteread = 0; // 读取的字节数
			while ((byteread = in.read(buffer)) != -1) {
				fos.write(buffer, 0, byteread);
			}
			fos.flush();
		} catch (Exception e) {
			logger.error("写文件失败", e);
			return false;
		} finally {
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(in);
		}
		return true;
	}
}
