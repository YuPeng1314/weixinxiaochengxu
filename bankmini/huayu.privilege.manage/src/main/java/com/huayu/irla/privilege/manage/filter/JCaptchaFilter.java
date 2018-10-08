package com.huayu.irla.privilege.manage.filter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Writer;

import javax.imageio.ImageIO;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huayu.irla.privilege.manage.service.DefaultAccessDeniedHandler;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**  
 * 针对 JCaptcha 专门的过滤器(Filter)  
 *   
 */
public class JCaptchaFilter implements Filter {

    // web.xml中的参数名定义   
    public static final String PARAM_CAPTCHA_PARAMTER_NAME = "captchaParamterName";
    public static final String PARAM_CAPTCHA_SERVICE_ID = "captchaServiceId";
    public static final String PARAM_FILTER_PROCESSES_URL = "filterProcessesUrl";
    public static final String PARAM_FAILURE_URL = "failureUrl";
    public static final String PARAM_AUTO_PASS_VALUE = "autoPassValue";

    // 默认值定义   
    public static final String DEFAULT_FILTER_PROCESSES_URL = "/login/validate";
    public static final String DEFAULT_CAPTCHA_SERVICE_ID = "captchaService";
    public static final String DEFAULT_CAPTCHA_PARAMTER_NAME = "j_captcha";

    protected static Logger logger = Logger.getLogger("service");

    private String failureUrl;
    private String filterProcessesUrl = DEFAULT_FILTER_PROCESSES_URL;
    private String captchaServiceId = DEFAULT_CAPTCHA_SERVICE_ID;
    private String captchaParamterName = DEFAULT_CAPTCHA_PARAMTER_NAME;
    private String autoPassValue;

    private CaptchaService captchaService;

    /**  
     * Filter回调初始化函数.  
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub   

        initParameters(filterConfig);
        initCaptchaService(filterConfig);
    }

    public void doFilter(ServletRequest theRequest, ServletResponse theResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) theRequest;
        HttpServletResponse response = (HttpServletResponse) theResponse;
        String servletPath = request.getServletPath();

        // 符合filterProcessesUrl为验证处理请求,其余为生成验证图片请求.   
        if (StringUtils.startsWith(servletPath, filterProcessesUrl)) {
            boolean validated = validateCaptchaChallenge(request);
            if (validated) {
                chain.doFilter(request, response);
            } else {
                redirectFailureUrl(request, response);
            }
        } else {
            genernateCaptchaImage(request, response);
        }
    }

    /**  
     * Filter回调退出函数.  
     */
    public void destroy() {
        // TODO Auto-generated method stub   
    }

    /**  
     * 初始化web.xml中定义的filter init-param.  
     */

    protected void initParameters(final FilterConfig fConfig) {
        failureUrl = fConfig.getInitParameter(PARAM_FAILURE_URL);

        if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_FILTER_PROCESSES_URL))) {
            filterProcessesUrl = fConfig.getInitParameter(PARAM_FILTER_PROCESSES_URL);
        }

        if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_CAPTCHA_SERVICE_ID))) {
            captchaServiceId = fConfig.getInitParameter(PARAM_CAPTCHA_SERVICE_ID);
        }

        if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_CAPTCHA_PARAMTER_NAME))) {
            captchaParamterName = fConfig.getInitParameter(PARAM_CAPTCHA_PARAMTER_NAME);
        }

        if (StringUtils.isNotBlank(fConfig.getInitParameter(PARAM_AUTO_PASS_VALUE))) {
            autoPassValue = fConfig.getInitParameter(PARAM_AUTO_PASS_VALUE);
        }
    }

    /**  
     * 从ApplicatonContext获取CaptchaService实例.  
     */
    protected void initCaptchaService(final FilterConfig fConfig) {

        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(fConfig.getServletContext());
        captchaService = (CaptchaService) context.getBean(captchaServiceId);
    }

    /**  
     * 生成验证码图片.  
     */
    protected void genernateCaptchaImage(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {

        setDisableCacheHeader(response);
        response.setContentType("image/jpeg");

        ServletOutputStream out = response.getOutputStream();
        try {
            String captchaId = request.getSession(true).getId();
            BufferedImage challenge = (BufferedImage) captchaService.getChallengeForID(captchaId, request.getLocale());
            ImageIO.write(challenge, "jpg", out);
            out.flush();
        } catch (CaptchaServiceException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**  
     * 验证验证码.  
     */
    protected boolean validateCaptchaChallenge(final HttpServletRequest request) {

        try {

            String captchaID = request.getSession().getId();
            String challengeResponse = request.getParameter(captchaParamterName);
            //不区分大小写
            if(StringUtils.isNotBlank(challengeResponse)) {
            	challengeResponse = challengeResponse.toLowerCase();
            }
            // 自动通过值存在时,检验输入值是否等于自动通过值   
            if (StringUtils.isNotBlank(autoPassValue) && autoPassValue.equals(challengeResponse)) {

                return true;
            }

            return captchaService.validateResponseForID(captchaID, challengeResponse);
        } catch (CaptchaServiceException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**  
     * 跳转到失败页面.  
     *   
     * 可在子类进行扩展, 比如在session中放入SpringSecurity的Exception.  
     */
    protected void redirectFailureUrl(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        //错误信息
        String errorMsg = "validateError";
        //ajax请求处理的逻辑
        if (DefaultAccessDeniedHandler.isAjaxRequest(request)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            Writer write = null;
            try {
                write = response.getWriter();
                if (null != write) {
                    write.write("{\"errorMsg\":\"" + errorMsg + "\"}");
                }
            } catch (IOException ex) {
                Logger.getLogger(getClass()).error(ex);
            } finally {
                if (null != write) {
                    try {
                        write.close();
                    } catch (IOException ex) {

                    }
                }
            }
        } else {
            request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", errorMsg);
            response.sendRedirect(request.getContextPath() + failureUrl);
        }
    }

    /**  
     * 设置禁止客户端缓存的Header.  
     */
    public static void setDisableCacheHeader(HttpServletResponse response) {
        // Http 1.0 header   
        response.setDateHeader("Expires", 1L);
        response.addHeader("Pragma", "no-cache");
        // Http 1.1 header   
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
    }
}
