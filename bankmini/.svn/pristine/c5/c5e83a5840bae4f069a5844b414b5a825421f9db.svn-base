package com.huayu.irla.core.courseware.vo;

import java.io.Serializable;
import java.util.List;

import com.huayu.irla.core.user.vo.WxUserInfoVO;

/**
 * 
 * @ClassName: CoursewareVO
 * @Description: 课件维护的实体类
 * @author liuwei
 * @date 2017年5月24日 下午5:05:54
 *
 */

public class VideoRecordInfoVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2855698119627681865L;

	/**
	 * 每行数据的id
	 */
    private String id;
	
	/**
	 * 用户编码
	 */
	private String userCode;

	/**
	 * 资源id
	 */
	private String resourceId;
	
	
	/**
	 * 记录日期
	 */
	private long recordDate;
	
	/**
	 * 当前播放时间
	 */
	private int currentPlayTime;
	
	/**
	 * 资源名称
	 */
	private String resourceName;
	
	/**
	 * 资源编码
	 */
	private String resourceCode;
	
	/**
	 * 资源编码
	 */
	private String courseCode;
	
	/**
	 * 图片路径
	 * @return
	 */
	private String imgName;
	
	/**
	 * 微信用户表信息
	 */
	private List<WxUserInfoVO> userList;
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public long getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(long recordDate) {
		this.recordDate = recordDate;
	}

	public int getCurrentPlayTime() {
		return currentPlayTime;
	}

	public void setCurrentPlayTime(int currentPlayTime) {
		this.currentPlayTime = currentPlayTime;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	@Override
	public String toString() {
		return this.getResourceId()+this.getUserCode();
	}
	
	@Override
	public int hashCode() {
		return this.resourceId.hashCode()+this.getUserCode().hashCode();
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<WxUserInfoVO> getUserList() {
		return userList;
	}

	public void setUserList(List<WxUserInfoVO> userList) {
		this.userList = userList;
	}
}
