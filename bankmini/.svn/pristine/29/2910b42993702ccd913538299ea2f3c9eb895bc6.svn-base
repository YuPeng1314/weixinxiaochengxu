package com.huayu.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
	public static boolean executeCodecs(String ffmpegPath, String upFilePath, String codcFilePath,String parmCoding
			) {
		// 创建一个List集合来保存转换视频文件为flv格式的命令
		List<String> convert = new ArrayList<String>();
		convert.add(ffmpegPath); // 添加转换工具路径
		convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
		convert.add(upFilePath); // 添加要转换格式的视频文件的路径
		String [] stringArr= parmCoding.split(",");
		for (int i = 0; i < stringArr.length; i++) {
			convert.add(stringArr[i]);
		}
		
		convert.add(codcFilePath);

		boolean mark = true;
		ProcessBuilder builder = new ProcessBuilder();
		try {
			builder.command(convert);
			builder.redirectErrorStream(true);
			// builder.start();
			final Process p = builder.start(); // 得到进程实例
			// 处理InputStream的线程

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
						logger.error(e);
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							logger.error(e);
						}
					}
				}
			}.start();

			new Thread() {
				@Override
				public void run() {
					BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					String line = null;

					try {
						while ((line = err.readLine()) != null) {
							logger.error("err: " + line);
						}
					} catch (IOException e) {
						logger.error(e);
					} finally {
						try {
							err.close();
						} catch (IOException e) {
							logger.error(e);
						}
					}
				}
			}.start();
			// 等待进程执行完毕
			if (p.waitFor() != 0) {
				// 如果进程运行结果不为0,表示进程是错误退出的
				// 获得进程实例的错误输出
				//InputStream error = p.getErrorStream();
				// do something
			}
		    p.destroy();
			  p.getErrorStream().close();  
              p.getInputStream().close();  
              p.getOutputStream().close(); 
		} catch (Exception e) {
			logger.error(e);
			mark = false;
		}/*finally{
			  IOUtils.closeQuietly(p.getInputStream());
	            IOUtils.closeQuietly(p.getOutputStream());
	            IOUtils.closeQuietly(p.getErrorStream());
	            if (p!= null) {
	            	p.destroy();
	            }
	            
		}*/
		return mark;
	}
	

}
