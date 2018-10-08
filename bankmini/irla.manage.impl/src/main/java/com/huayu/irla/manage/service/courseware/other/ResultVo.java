package com.huayu.irla.manage.service.courseware.other;

import java.io.Serializable;

/**
 * 统一结果返回
 * @author Administrator
 *
 * @param <T>
 */
public class ResultVo<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8216442221241387853L;

	/**
	 * 枚举型，结果状态
	 */
    private ResultStatus status;

    /**
     * 信息
     */
    private String msg;

    /**
     * T代表任意的类型
     */
    private T data;

    public ResultVo(ResultStatus status) {
        this(status, status.getReasonPhrase(), null);
    }

    public ResultVo(ResultStatus status, T data) {
        this(status, status.getReasonPhrase(), data);
    }

    public ResultVo(ResultStatus status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
