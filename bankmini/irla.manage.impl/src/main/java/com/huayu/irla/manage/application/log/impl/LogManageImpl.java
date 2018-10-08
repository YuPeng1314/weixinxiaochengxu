package com.huayu.irla.manage.application.log.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.service.impl.datadict.DataDricCommon;
import com.huayu.irla.manage.application.log.ILogManage;

/**
 * 
  * @ClassName: LogManageImpl
  * @Description: 日志管理
  * @author liuwei
  * @date 2017年1月3日 下午5:31:00
  *
 */

@Component("logManage")
public class LogManageImpl implements ILogManage {

	   private static final Logger logger = Logger.getLogger(LogManageImpl.class);
    /**
     * 日志展示
     */
    @Override
    public JSONObject findLogList() {
        // 日志路径
    	String path = DataDricCommon.getValueByKey("Log.file_manage","/hy/irla/logger/");
        File dir = new File(path);
        JSONObject result = getAllFiles(dir, 0);// 0表示最顶层
        return result;
    }

    public JSONObject getAllFiles(File dir, int level) {
        level++;
        File[] files = dir.listFiles();
        JSONObject result = new JSONObject();
        if(files == null){
        	result.put("record", 0);
        	return result;
        }
        List<String> logNames = new ArrayList<String>();
        List<String> logTime = new ArrayList<String>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                // 这里面用了递归的算法
                getAllFiles(files[i], level);
            } else {
            	//判断文件名开头
            	String begin = DataDricCommon.getValueByKey("log.matching","hdy");
            	
            	if(StringUtils.equals(files[i].getName().substring(0, 3), begin) ){
                long time = files[i].lastModified();// 返回文件最后修改时间，是以个long型毫秒数
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                Calendar now = Calendar.getInstance();
                Date nowDate = now.getTime();// 现在的时间
                now.add(Calendar.DATE, -10); // 现在时间的10天前
                Date usedDate = now.getTime();// 10天前的时间
                Date ctime = new Date(time);// 日志的时间
                //提取10天之内的错误日志
                if (ctime.before(nowDate) && ctime.after(usedDate)) {
                    String temTime = df.format(ctime);
                    logNames.add(files[i].getName());
                    logTime.add(temTime);
                }
            	}
            }
        }
       
        result.put("row", logNames);
        result.put("tal", logTime);
        result.put("record", 1);
        return result;
    }

    /**
     * 日志下载
     */

    @Override
    public void downLog(String fileName, HttpServletResponse response) {
        OutputStream out = null;
        FileInputStream in = null;
        
        String path = DataDricCommon.getValueByKey("Log.file_manage","/hy/irla/logger/");
        String filePath = path + fileName ;
        try {
            File file = new File(filePath);
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");//把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。  
            response.setContentType("application/octet-stream");//告诉浏览器输出内容为流  
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);//Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。  
            String len = String.valueOf(file.length());
            response.setHeader("Content-Length", len);//设置内容长度  
            out = response.getOutputStream();
            in = new FileInputStream(file);
            byte[] b = new byte[1024];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
        } catch (FileNotFoundException e) {
        	logger.error("异常日志下载失败");
        } catch (IOException e) {
        	logger.error("异常日志下载失败");
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

}
