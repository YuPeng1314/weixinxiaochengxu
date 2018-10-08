package com.huayu.irla.manage.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.fastjson.JSONArray;
import com.huayu.irla.manage.service.menu.IMenuService;
import com.huayu.irla.privilege.manage.common.UserMesCommon;
import com.huayu.irla.privilege.manage.vo.SysUsersVO;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 资源管理处理器
 * 
 * @author stef-shiqing
 *
 */
@Named
public class ResourceHandle {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private IMenuService menuServiceImpl;
    
	private static final Logger logger = Logger.getLogger(ResourceHandle.class);

    /**
     * 加载资源文件
     * 
     * @param request
     * @param response
     */
    public void loadResourceFile(String classFile, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // 类文件路径 tool/indexScan
        URL classURL = this.getClass().getClassLoader().getResource(classFile);
        if (null == classURL) {
            response.sendError(404);
            return;
        }
        InputStream ops = null;
        Reader reader = null;
        Writer write = null;
        try {
            ops = classURL.openStream();
            reader = new InputStreamReader(ops);
            String contentHTML = IOUtils.toString(reader);
            //获取登录用户信息
            SysUsersVO userInfo = UserMesCommon.getUserInfo(request);
            
           if(null == userInfo) {
        	   try {
				request.getRequestDispatcher("/html/privilege/login.jsp").forward(request, response);
				return;
			} catch (ServletException e) {
				logger.error(e);
			}
           }
            // 得到正文的内容，填充模板
            String htmlContext = getResponseText(contentHTML, classFile, userInfo, request);

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            write = response.getWriter();
            write.write(htmlContext);
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(write);
        }
    }

    /**
     * 添加模板内容
     * 
     * @param content
     * @return
     * @throws IOException
     */
    private String getResponseText(String content, String classFile, SysUsersVO userInfo, HttpServletRequest request)
            throws IOException {
        // 得到菜单
        JSONArray treeMenu = null;
        try {
            treeMenu = menuServiceImpl.getMenuList(userInfo, request);
        } catch (Exception e1) {
        	logger.error(e1);
        }
        String htmlText = "";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", content);
        map.put("treeMenu", treeMenu);
        map.put("htmlUrl", classFile);
        map.put("userName", userInfo.getName());
        Template tpl = null;
        try {
            tpl = freeMarkerConfigurer.getConfiguration().getTemplate("template.ftl");// 加载资源文件
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);// 加入map到模板中
        } catch (IOException e) {
        	logger.error(e);
        } catch (TemplateException e) {
        	logger.error(e);
        }
        return htmlText;
    }
}
