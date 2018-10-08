package com.huayu.core.logger;

import java.net.InetAddress;

import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.ErrorCode;

public class IetlRollingFileAppender extends RollingFileAppender {
	
	  public IetlRollingFileAppender() {
	    super();
	  }

	  @Override
	  public void activateOptions() {
	    if(fileName != null) {
	      try {
	    	  //增加当前机器名
	    	  int lastPos = fileName.lastIndexOf(".");
	    	  if(lastPos > -1) {
	    		  String prefix = fileName.substring(0,lastPos);
	    		  String subfix = fileName.substring(lastPos, fileName.length());
	    		  fileName = prefix + '-' + InetAddress.getLocalHost().getHostName()+subfix;
	    	  }
	    	  setFile(fileName, fileAppend, bufferedIO, bufferSize);
	      }
	      catch(java.io.IOException e) {
		  errorHandler.error("setFile("+fileName+","+fileAppend+") call failed.",
				   e, ErrorCode.FILE_OPEN_FAILURE);
	      }
	    } else {
	      //LogLog.error("File option not set for appender ["+name+"].");
	      LogLog.warn("File option not set for appender ["+name+"].");
	      LogLog.warn("Are you using FileAppender instead of ConsoleAppender?");
	    }
	  }
}
