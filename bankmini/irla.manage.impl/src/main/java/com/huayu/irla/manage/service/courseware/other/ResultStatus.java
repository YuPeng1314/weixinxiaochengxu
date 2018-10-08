package com.huayu.irla.manage.service.courseware.other;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 结果类型枚举
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultStatus {
    /**
     * 1 开头为判断文件在系统的状态
     */
    IS_HAVE(100, "文件已存在！"),

    NO_HAVE(101, "该文件没有上传过。"),

    ING_HAVE(102, "该文件上传了一部分。");

	/**
	 * 编码值
	 */
    private final int value;
    
    /**
     * 错误路由
     */
    private final String reasonPhrase;


    ResultStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}