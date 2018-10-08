package com.huayu.irla.core.base;

import java.io.Serializable;
import java.util.Map;

/**
 * 手机端标准返回值
 * @author Administrator
 *
 */
public class ResultVO implements Serializable {

    /**
     * @Fields serialVersionUID
     */

    private static final long serialVersionUID = 4992188503551104097L;

    /**
     * 成功
     */
    public static final Integer SUCCESS = 1;

    /**
     * 失败
     */
    public static final Integer FAIL = 0;

    /**
     * 结果码
     *//*
    private Integer code;*/

    /**
     * 网络code
     */
    private Integer netCode;
    /**
     * 错误信息
     */
    private String errorMessage = "";
    /**
     * 提交参数
     */
    private String subMessage = "";
    /**
     * 手机号码
     */
   /* private Long telephoneNum;*/

    /**
     * 结果
     */
    private Object data;

    /**
     * 备用参数（提供给APP调用的预留参数）
     */
    private Map<String, Object> standbyParams;

    public Integer getNetCode() {
        return netCode;
    }

    public void setNetCode(Integer netCode) {
        this.netCode = netCode;
    }

    /**
     * getter method
     * 
     * @return the code
     */

 

    /**
     * getter method
     * 
     * @return the errorMessage
     */

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * setter method
     * 
     * @param errorMessage
     *            the errorMessage to set
     */

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * getter method
     * 
     * @return the subMessage
     */

    public String getSubMessage() {
        return subMessage;
    }

    /**
     * setter method
     * 
     * @param subMessage
     *            the subMessage to set
     */

    public void setSubMessage(String subMessage) {
        this.subMessage = subMessage;
    }

    /**
     * getter method
     * 
     * @return the data
     */

    public Object getData() {
        return data;
    }

    /**
     * setter method
     * 
     * @param data
     *            the data to set
     */

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getStandbyParams() {
        return standbyParams;
    }

    public void setStandbyParams(Map<String, Object> standbyParams) {
        this.standbyParams = standbyParams;
    }



}
