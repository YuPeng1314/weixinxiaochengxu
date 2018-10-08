package com.huayu.irla.core.service.courseware;

import java.util.List;

import com.huayu.irla.core.courseware.vo.CourseInfoVO;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.core.courseware.vo.FileUploadFileRelationVO;
import com.huayu.irla.core.courseware.vo.FileUploadItemVO;

/**
 * 
 * @ClassName: ICoursewareService
 * @Description: 课件维护服务层的接口类
 * @author liuwei
 * @date 2017年5月24日 下午7:30:58
 *
 */

public interface ICoursewareService {

	/**
	 * 
	 * @Title: updateImgAddre
	 * @Description:填充（或者清空）图片的路径
	 * @author liuwei
	 * @date 2017年6月16日 上午9:35:40
	 * @param course
	 */

	void updateImgAddre(CoursewareVO course);

	/**
	 * 
	 * @Title: deleteCourseFile
	 * @Description: 删除课件信息
	 * @author liuwei
	 * @date 2017年5月24日 下午7:30:11
	 * @param id
	 */

	void deleteCourseFile(Integer id);


	/**
	 * 得到上传的文件
	 * 
	 * @param uploadFile
	 * @return
	 */
	List<FileUploadItemVO> getUploadFile(FileUploadItemVO uploadFile);


	/**
	 * 得到上传的文件
	 * 
	 * @param uploadFile
	 * @return
	 */
	List<FileUploadItemVO> getUploadFile(FileUploadItemVO uploadFile, boolean isSystemAdmin);

	/**
	 * 得到上传的文件总数
	 * 
	 * @param uploadFile
	 * @return
	 */
	Integer getUploadFileCount(FileUploadItemVO uploadFile);

	/**
	 * 增加上传的文件
	 * 
	 * @param uploadFile
	 */
	void addUploadFile(FileUploadItemVO uploadFile);

	/**
	 * 更新上传的文件
	 * 
	 * @param uploadFile
	 */
	void updateUploadFile(FileUploadItemVO uploadFile);

	/**
	 * 删除上传文件
	 * 
	 * @param uploadFile
	 */
	void deleteUploadFile(FileUploadItemVO uploadFile);

	/**
	 * 删除关系表数据
	 * 
	 * @param fileId
	 */
	public void deleteUploadFileRelation(FileUploadItemVO fileItemVO);

	/**
	 * 插入关系表
	 * 
	 * @param fileRelationVO
	 */
	public void insertUploadFileRelation(FileUploadFileRelationVO fileRelationVO);

	/**
	 * 得到上传文件关系
	 * 
	 * @param uploadFileId
	 * @return
	 */
	public List<FileUploadFileRelationVO> getUploadFileRelation(Long uploadFileId);

	/**
	 * 更新文件的关系
	 * 
	 * @param fileRelationVO
	 */
	public void updateUploadFileRelationById(FileUploadFileRelationVO fileRelationVO);

	/**
	 * 同步数据
	 * 
	 * @param fileRelationVO
	 */
	void dittoUploadFileRelation(FileUploadFileRelationVO fileRelationVO);

	
	
	/**
	 * 
	  * @Title: deleteAllVideos
	  * @Description: 删除同一视频的数据
	  * @return void
	  * @author liuwei
	  * @date 2017年11月22日 下午4:23:12
	 */
	
	void deleteAllVideos(Integer resourceId);

	/**
	 * 课程维护查询
	 * @param course
	 * @return
	 */
	List<CoursewareVO> findCourseware(CoursewareVO course);
	
	/**
	 * 课程维护查询
	 * @param course
	 * @return
	 */
	List<CoursewareVO> findCoursewareMaintain(CoursewareVO course);

	/**
	 * 课程维护数量统计
	 * @param course
	 * @return
	 */
	Integer findCoursewareMaintainCount(CoursewareVO course);
	
	/**
	 * 查询课程信息
	 * @param course
	 * @return
	 */
	List<CourseInfoVO> findCourseInfo(CourseInfoVO course);
	
	
	/**
	 * 查询课程信息
	 * @param course
	 * @return
	 */
	Integer findCourseInfoCount(CourseInfoVO course);
	
	
	/**
	 * 查询课程数量
	 * @param course
	 * @return
	 */
	Integer findCoursewareCount(CoursewareVO course);
	
	
	/**
	 * 增加浏览次数记录
	 * @param course
	 */
	void addVisitNum(CoursewareVO course);
	
	/**
	 * 更新浏览次数记录
	 * @param course
	 */
	void updateVisitNum(CoursewareVO course);
	/**
	 * 更新资源序列
	 * @param id
	 */
	void updateSequenceNumber(CoursewareVO course);
	/**
	 * 获取浏览次数记录
	 * @param course
	 */
	List<CoursewareVO> getVisitNum(CoursewareVO course);
	
	/**
	 * 增加收藏记录
	 * @param course
	 */
	void addResCollect(CoursewareVO course);
	
	/**
	 * 删除收藏记录
	 * @param course
	 */
	void deleteResCollect(Integer id);
	
	
	/**
	 * 获取收藏记录
	 * @param course
	 */
	List<CoursewareVO> getResCollect(CoursewareVO course);
	
	/**
	 * 
	  * @Title: getOtherCourse
	  * @Description: 视频页面其他人也在看
	  * @return List<CourseInfoVO>
	  * @author liuwei
	  * @date 2018年7月2日 下午12:21:21
	 */
	
	List<CourseInfoVO> getOtherCourse(CourseInfoVO course);
	
	/**
	 * 
	  * @Title: getResCollectCount
	  * @Description: 
	  * @return Integer
	  * @author liuwei
	  * @date 2018年7月2日 下午3:09:43
	 */
	
	Integer getResCollectCount(CoursewareVO course);


}
