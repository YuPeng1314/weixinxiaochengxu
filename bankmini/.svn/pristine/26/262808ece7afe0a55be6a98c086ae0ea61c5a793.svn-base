package com.huayu.irla.manage.service.courseware.jms.transcoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * @author 作者 fzh:
 * @version 创建时间：2017年1月3日 下午4:01:34 类说明 将视频转化为 flv格式码
 */
public class Transcoding {
	 private static final Logger logger = Logger.getLogger(Transcoding.class);
	/**
	 * 视频转码
	 * 
	 * @param ffmpegPath
	 *            转码工具的存放路径
	 * @param upFilePath
	 *            用于指定要转换格式的文件,要截图的视频源文件
	 * @param codcFilePath
	 *            格式转换后的的文件保存路径
	 * @param mediaPicPath String mediaPicPath
	 *            截图保存路径
	 * @return
	 * @throws Exception
	 */
	public static boolean executeCodecs(String ffmpegPath,String upFilePath, String parmCoding, String codcFilePath) throws Exception{
		// 创建一个List集合来保存转换视频文件为flv格式的命令
		List<String> convert = new ArrayList<String>();
		convert.add(ffmpegPath); // 添加转换工具路径
		convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
		convert.add(upFilePath); // 添加要转换格式的视频文件的路径
		String [] stringArr= parmCoding.split(",");
		for (int i = 0; i < stringArr.length; i++) {
			convert.add(stringArr[i]);
		}
		convert.add("-y");
		convert.add(codcFilePath);

		boolean mark = true;
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(convert);
		builder.redirectErrorStream(true);
		// builder.start();
		Process p = builder.start(); // 得到进程实例
		// 处理InputStream的线程
		try {
			
			new Thread() {
				@Override
				public void run() {
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = null;

					try {
						while ((line = in.readLine()) != null) {
							logger.info("output: " + line);
						}
					} catch (IOException e) {
						logger.error("trans inputStream:", e);
					} finally {
						IOUtils.closeQuietly(in);
					}
				}
			}.start();

			new Thread() {
				@Override
				public void run() {
					BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					String line = null;
					//错误并保存
					boolean isErrorFlag = false;
					StringBuffer sb = new StringBuffer();
					try {
						while ((line = err.readLine()) != null) {
							logger.error("err: " + line);
							sb.append(line+"\r\n");
							isErrorFlag = true;
						}
					} catch (IOException e) {
						logger.error("trans errorStream:", e);
					}  finally {
						IOUtils.closeQuietly(err);
					}
					
					if(isErrorFlag) {
						throw new RuntimeException("转换过程出错:" + sb.toString());
					}
				}
			}.start();	
			if(p.waitFor() != 0) {
				throw new RuntimeException("转换过程出错了");
			}
		
		} catch (Exception e) {
			logger.error("转换出错:", e);
			throw e;
		} finally {
			try {
				p.destroy();
			} catch(Exception ex) {
				logger.error("destroy失败:" , ex);
			}
			try {
				p.getErrorStream().close();  
			} catch(IOException ex) {
				logger.error("errorStream:" , ex);
			}
			try {
				p.getInputStream().close();  
			} catch(IOException ex) {
				logger.error("inputStream:" , ex);
			}
			try {
				p.getOutputStream().close(); 
			} catch(IOException ex) {
				logger.error("outputStream:", ex);
			}
		}
		return mark;
	}
	

}


