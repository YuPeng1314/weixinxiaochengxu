package com.huayu.irla.core.dao.courseware;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.courseware.vo.CourseInfoVO;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.core.courseware.vo.FileUploadFileRelationVO;
import com.huayu.irla.core.courseware.vo.FileUploadItemVO;

/**
 * 
 * @ClassName: ICoursewareDao
 * @Description: 课件维护Dao层的接口类
 * @author liuwei
 * @date 2017年5月24日 下午7:22:38
 *
 */

public interface ICoursewareDao {
	
	/**
	 * 增加浏览次数记录
	 * @param course
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addVisitNum(@Param("course") CoursewareVO course);
	
	/**
	 * 更新浏览次数记录
	 * @param course
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateVisitNum(@Param("course") CoursewareVO course);
	
	/**
	 * 获取浏览次数记录
	 * @param course
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CoursewareVO> getVisitNum(@Param("course") CoursewareVO course);
	
	/**
	 * 增加收藏记录
	 * @param course
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addResCollect(@Param("course") CoursewareVO course);
	
	/**
	 * 删除收藏记录
	 * @param course
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteResCollect(Integer id);
	/**
	 * 更新资源序列
	 * @param id
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateSequenceNumber(@Param("course")CoursewareVO course);
	
	/**
	 * 获取收藏记录
	 * @param course
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CoursewareVO> getResCollect(@Param("course") CoursewareVO course);
	
	/**
	 * 课程信息，支持查询
	 * @param course
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CourseInfoVO> findCourseInfo(@Param("course") CourseInfoVO course);
	
	/**
	 * 课程信息,查询统计
	 * @param course
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer findCourseInfoCount(@Param("course") CourseInfoVO course);
	
	/**
	 * 课程查询
	 * @param course
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CoursewareVO> findCourseware(@Param("course") CoursewareVO course);
	
	/**
	 * 课程数量查询
	 * @param course
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer findCoursewareCount(@Param("course") CoursewareVO course);
	
	
	/**
	 * 课程维护查询
	 * @param course
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CoursewareVO> findCoursewareMaintain(@Param("course") CoursewareVO course);
	
	/**
	 * 课程维护查询
	 * @param course
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer findCoursewareMaintainCount(@Param("course") CoursewareVO course);
	
	
	/**
	 * 
	 * @Title: updateImgAddre
	 * @Description:填充（或者清空）图片的路径
	 * @author liuwei
	 * @date 2017年6月16日 上午9:35:40
	 * @param course
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateImgAddre(CoursewareVO course);

	/**
	 * 
	 * @Title: deleteCourseFile
	 * @Description: 删除课件信息
	 * @author liuwei
	 * @date 2017年5月24日 下午7:30:11
	 * @param id
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteCourseFile(Integer id);

	
	/**
	 * 得到上传的文件
	 * 
	 * @param uploadFile
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<FileUploadItemVO> getUploadFile(@Param("uploadFile") FileUploadItemVO uploadFile);

	/**
	 * 得到上传的文件总数
	 * 
	 * @param uploadFile
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	Integer getUploadFileCount(@Param("uploadFile") FileUploadItemVO uploadFile);

	/**
	 * 增加上传的文件
	 * 
	 * @param uploadFile
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void addUploadFile(@Param("uploadFile") FileUploadItemVO uploadFile);

	/**
	 * 更新上传的文件
	 * 
	 * @param uploadFile
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateUploadFile(@Param("uploadFile") FileUploadItemVO uploadFile);

	/**
	 * 删除上传文件
	 * 
	 * @param uploadFile
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteUploadFile(@Param("uploadFile") FileUploadItemVO uploadFile);

	/**
	 * 得到对应文件和资源的关系数据
	 * 
	 * @param uploadFileId
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<FileUploadFileRelationVO> getUploadFileRelation(@Param("uploadFileId") Long uploadFileId);

	/**
	 * 根据文件id得到上传文件信息
	 * 
	 * @param id
	 * @return
	 */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	FileUploadItemVO getUploadFileById(@Param("id") Long id);

	/**
	 * 插入正式表
	 * 
	 * @param courseObj
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void insertFormualRes(@Param("courseObj") CoursewareVO courseObj);

	/**
	 * 更新正式表
	 * 
	 * @param courseObj
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateFormualRes(@Param("courseObj") CoursewareVO courseObj);

	/**
	 * 插入上传文件更新表
	 * 
	 * @param uploadFileRel
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void insertUploadFileRelation(@Param("uploadFileRel") FileUploadFileRelationVO uploadFileRel);

	/**
	 * 根据文件id删除文件的对应关系表
	 * 
	 * @param fileId
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteUploadFileRelation(@Param("fileId") Long fileId);

	/**
	 * 更新文件
	 * 
	 * @param uploadFileRel
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void updateUploadFileRelationById(@Param("uploadFileRel") FileUploadFileRelationVO uploadFileRel);

	/**
	 * 同上资源关系
	 * 
	 * @param uploadFileRel
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void dittoUploadFileRelation(@Param("uploadFileRel") FileUploadFileRelationVO uploadFileRel);


	/**
	 * 
	 * @Title: deleteAllVideos
	 * @Description: 删除同一视频的数据
	 * @return void
	 * @author liuwei
	 * @date 2017年11月22日 下午4:23:12
	 */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	void deleteAllVideos(Integer resourceId);
	
	/**
	 * 
	  * @Title: getOtherCourse
	  * @Description: 视频页面其他人也在看
	  * @return List<CourseInfoVO>
	  * @author liuwei
	  * @date 2018年7月2日 下午12:20:21
	 */
	
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	List<CourseInfoVO> getOtherCourse(@Param("course") CourseInfoVO course);
	
	/**
	 * 
	  * @Title: getResCollectCount
	  * @Description: 取得资源收藏的总数
	  * @return Integer
	  * @author liuwei
	  * @date 2018年7月2日 下午3:09:16
	 */
	
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	Integer getResCollectCount(@Param("course") CoursewareVO course);
}
