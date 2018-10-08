package com.huayu.core.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

public class ThreadIoUtil {
	private static final Logger logger = Logger.getLogger(ThreadIoUtil.class);
    
     //两个线程　读取流与错误流线程 
      public void pubThread(InputStream stdErr){
			new Thread() {
				@Override
				public void run() {
					BufferedReader err = new BufferedReader(new InputStreamReader(stdErr));
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
      }
    
}