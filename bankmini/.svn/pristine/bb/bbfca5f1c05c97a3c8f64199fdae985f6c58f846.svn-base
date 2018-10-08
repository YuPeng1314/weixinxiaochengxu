package com.huayu.irla.privilege.manage.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrivilegePoint {
	/**
	 * 添加权限
	 */
	public static final int ROLE_ADD = 2;
	
	/**
	 * 更新权限
	 */
	public static final int ROLE_UPDATE = 4;
	
	/**
	 * 删除权限
	 */
	public static final int ROLE_DELETE = 3;
	
	/**
	 * 读权限
	 */
	public static final int ROLE_READ = 1;
	
	/**
	 * 上传权限
	 */
	public static final int ROLE_UPLOAD = 5;
	
	/**
	 * 下载权限
	 */
	public static final int ROLE_DOWNLOAD = 6;
	
	/**
	 * 其它权限
	 */
	public static final int ROLE_OTHER = 7;
	
	/**
	 * 默认赋读权限
	 * @return
	 */
	int privilegeMode() default PrivilegePoint.ROLE_READ;
	
	/**
	 * 权限名称
	 * @return
	 */
	String privilegeName();
}
