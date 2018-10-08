package com.huayu.core.util;


/******************************
 * 创建作者：fzh
 * 文件名称：EnumConstants.java
 * 版本：1.0
 * 功能：  长量 . 枚举
 *****************************************/
public class EnumConstants {
	
	// 是否为及时发布 1 是
	public static final String isTimely_yes = "1";
	
	// 是否为及时发布 1 是
	public static final String isTimely_no = "0";
	
	//  1 是强推
	public static final String isDirecType_yes = "1";
	
	// 0 否不是强推
	public static final String isDirecType_no = "0";
	
	//  1 是
	public static final String isMessae_yes = "1";
	//0 否
	public static final String isMessae_no = "0";
	
	 //1 是
    public static final String isJmsType_yes = "1";
	// 0 否
	public static final String isJmsType_no = "0";
	
	public static final String IETL_CODE_UTF8 ="UTF-8";
	
	public static final String IETL_CODE_ISO = "ISO-8859-1";
	
	/**
	 * RED5服务器路径
	 */
	public final static String RED5_PATH = "red5.path";
	
	public final static String IP = SysUploadUtils.getInstance().getValue("sync.ip", "127.0.0.1");
	public final static String USERNAME =  SysUploadUtils.getInstance().getValue("sync.username", "sync.username");
	public final static String PASSWORD =  SysUploadUtils.getInstance().getValue("sync.password", "sync.password");
	public final static String JAVAHOME =  SysUploadUtils.getInstance().getValue("sync.java.home", "sync.java.home");
	public final static String PORT =  SysUploadUtils.getInstance().getValue("sync.port", "sync.port");
	//取homepage图片路径
	public final static String UPIMGLOADPATH =SysUploadUtils.getInstance().getValue("pro.homepage.imgpath", "/image/homepage/");
	//取homepage图片路径
	public final static String UPFILELOADPATH =SysUploadUtils.getInstance().getValue("pro.homefile.filepath", "/homefile/");
	public final static String NGINXPATH =SysUploadUtils.getInstance().getValue("nginx.path", "/usr/local/nginx/html/");
	//读取路径
	public final static String SERVER_VIDEOS_URL =  SysUploadUtils.getInstance().getValue("sync.videos.url", "sync.videos.url");
	public final static String SERVER_IMG_URL =  SysUploadUtils.getInstance().getValue("sync.image.url", "sync.image.url");
	
	//删除路径
	public final static String SERVER_VIDEOS_URL_DEL =  SysUploadUtils.getInstance().getValue("sync.videos.url.del", "sync.videos.url.del");
	public final static String SERVER_IMG_URL_DEL =  SysUploadUtils.getInstance().getValue("sync.image.url.del", "sync.image.url.del");
	
	public final static String IMG_SERVICE_IP ="img.http.ip";
	public final static String SERVICE_IP ="homepage.service.ip";
	public final static String SERVICE_USERNAME ="homepage.service.username";
	public final static String SERVICE_PAS ="homepage.service.password";
	public final static String SERVICE_PORT ="homepage.service.port";
	public final static String NGINX_PATH ="nginx.service.path";
	public final static String HOMEPAGE_UPLOAD_PATH ="homepage.upload.path";
	public final static String EXAMINATION_UPLOAD_PATH ="examination.upload.path";
	public final static String HOMEFILE_UPLOAD_PATH ="homefile.upload.path";
	//文件嵌入图片的上传地址
	public final static String HOMEFILEIMG_UPLOAD_PATH ="homefileimg.upload.path";
	
	//培训资料管理的上传地址
	public final static String PERIOD_PATH ="period.path";
	
	//课件资源管理的上传地址
	public final static String COURSEWARE_PATH ="courseware.path";
	
	//密码发送状态
	public final static String SENDPASSWORD1 = "1";
	public final static String SENDPASSWORD2 = "2";
	
	
/*	//邮件服务
	public final static String MailServerHost = "smtp.163.com";
	public final static String MailServerPort = "25";
	public final static String MailUserName = "fzh19890719@163.com";
	public final static String MailPassword = "365120";
	public final static String MailFromAddress = "fzh19890719@163.com";
	public final static String MailSubject = "[干部网络教育]　密码提醒";*/
	

	
	
	
	
	
	//状态    1 未审核 2 待审核 3待处理 4 己审核  5己发布
	public static enum Audittype {
		audit1("1"),
		audit2("2"),
		audit3("3"),
		audit4("4"),
		audit5("5");
		private String value;

		private Audittype(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}
	
	//公开级别    1 一级公开 2 二级公开 3不公开
		public static enum PublicLevel {
			Level1("1"),
			Level2("2"),
			Level3("3");
			private String value;
			private PublicLevel(String value) {
				this.value = value;
			}

			public String getValue() {
				return this.value;
			}
		}
		
		    //通知类型1资源,2公告,3其它
				public static enum noticeType {
					type1("1"),
					type2("2"),
					type3("3");
					private String value;
					private noticeType(String value) {
						this.value = value;
					}

					public String getValue() {
						return this.value;
					}
				}
				
				
				//状态(0,就绪1,开始,2,失败3,成功)
				public static enum JjmsStatic {
					type0("0"),
					type1("1"),
					type2("2"),
					type3("3");
					private String value;
					private JjmsStatic(String value) {
						this.value = value;
					}

					public String getValue() {
						return this.value;
					}
				}
	
				//状态  0审核中1通过2未通过
				public static enum AuditState {
					audit0("0"),
					audit1("1"),
					audit2("2");
					private String value;

					private AuditState(String value) {
						this.value = value;
					}

					public String getValue() {
						return this.value;
					}
				}
	
	
}
