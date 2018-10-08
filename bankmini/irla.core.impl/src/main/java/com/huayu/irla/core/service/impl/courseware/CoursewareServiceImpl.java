package com.huayu.irla.core.service.impl.courseware;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huayu.irla.core.courseware.vo.CourseInfoVO;
import com.huayu.irla.core.courseware.vo.CoursewareVO;
import com.huayu.irla.core.courseware.vo.FileUploadFileRelationVO;
import com.huayu.irla.core.courseware.vo.FileUploadItemVO;
import com.huayu.irla.core.dao.courseware.ICoursewareDao;
import com.huayu.irla.core.service.courseware.ICoursewareService;

/**
 * 
 * @ClassName: CoursewareServiceImpl
 * @Description: 课件维护服务层的实现类
 * @author liuwei
 * @date 2017年5月25日 上午9:48:42
 *
 */

@Service
@Transactional
public class CoursewareServiceImpl implements ICoursewareService {

	@Autowired
	private ICoursewareDao coursewareDao;
	

	@Override
	public List<CoursewareVO> findCourseware(CoursewareVO course) {
		return coursewareDao.findCourseware(course);
	}

	@Override
	public List<CoursewareVO> findCoursewareMaintain(CoursewareVO course) {
		return coursewareDao.findCoursewareMaintain(course);
	}
	
	@Override
	public Integer findCoursewareMaintainCount(CoursewareVO course) {
		return coursewareDao.findCoursewareMaintainCount(course);
	}
	

	@Override
	public void deleteCourseFile(Integer id) {
		coursewareDao.deleteCourseFile(id);
	}



	@Override
	public void updateImgAddre(CoursewareVO course) {
		coursewareDao.updateImgAddre(course);
	}

	public List<FileUploadItemVO> getUploadFile(FileUploadItemVO uploadFile) {
		return getUploadFile(uploadFile, false);
	}
	
	@Override
	public List<FileUploadItemVO> getUploadFile(FileUploadItemVO uploadFile, boolean isSystemAdmin) {
		if (StringUtils.isBlank(uploadFile.getCreatedBy()) && !isSystemAdmin) {
			throw new RuntimeException("用户编码不能为空");
		}
		return coursewareDao.getUploadFile(uploadFile);
	}

	@Override
	public Integer getUploadFileCount(FileUploadItemVO uploadFile) {
		return coursewareDao.getUploadFileCount(uploadFile);
	}

	@Override
	public void addUploadFile(FileUploadItemVO uploadFile) {
		coursewareDao.addUploadFile(uploadFile);
	}

	@Override
	public void updateUploadFile(FileUploadItemVO uploadFile) {
		if (uploadFile.getId() == null && StringUtils.isBlank(uploadFile.getMd5())) {
			throw new RuntimeException("文件id和md5不能同时为空");
		}
		coursewareDao.updateUploadFile(uploadFile);

	}

	@Override
	public void deleteUploadFile(FileUploadItemVO uploadFile) {
		coursewareDao.deleteUploadFile(uploadFile);
	}

	/**
	 * 删除关系表数据
	 * 
	 * @param fileId
	 */
	@Override
	public void deleteUploadFileRelation(FileUploadItemVO fileItemVO) {
		coursewareDao.deleteUploadFileRelation(fileItemVO.getId());
	}

	/**
	 * 插入关系表
	 * 
	 * @param fileRelationVO
	 */
	@Override
	public void insertUploadFileRelation(FileUploadFileRelationVO fileRelationVO) {
		if (StringUtils.isBlank(fileRelationVO.getCreatedBy())) {
			throw new RuntimeException("用户编码不能为空");
		}
		coursewareDao.insertUploadFileRelation(fileRelationVO);
	}

	@Override
	public List<FileUploadFileRelationVO> getUploadFileRelation(Long uploadFileId) {
		return coursewareDao.getUploadFileRelation(uploadFileId);
	}

	@Override
	public void updateUploadFileRelationById(FileUploadFileRelationVO fileRelationVO) {
		if (StringUtils.isBlank(fileRelationVO.getLastUpdatedBy())) {
			throw new RuntimeException("用户编码不能为空");
		}
		coursewareDao.updateUploadFileRelationById(fileRelationVO);
	}

	@Override
	public void dittoUploadFileRelation(FileUploadFileRelationVO fileRelationVO) {
		if (StringUtils.isBlank(fileRelationVO.getCreatedBy())) {
			throw new RuntimeException("用户编码不能为空");
		}
		coursewareDao.dittoUploadFileRelation(fileRelationVO);
	}
	
	@Override
	public void deleteAllVideos(Integer resourceId) {
		coursewareDao.deleteAllVideos(resourceId);
		
	}

	@Override
	public List<CourseInfoVO> findCourseInfo(CourseInfoVO course) {
		return coursewareDao.findCourseInfo(course);
	}

	@Override
	public Integer findCourseInfoCount(CourseInfoVO course) {
		return coursewareDao.findCourseInfoCount(course);
	}
	
	@Override
	public Integer findCoursewareCount(CoursewareVO course) {
		return coursewareDao.findCoursewareCount(course);
	}

	@Override
	public void addVisitNum(CoursewareVO course) {
		coursewareDao.addVisitNum(course);
	}

	@Override
	public void updateVisitNum(CoursewareVO course) {
		coursewareDao.updateVisitNum(course);
	}

	@Override
	public List<CoursewareVO> getVisitNum(CoursewareVO course) {
		return coursewareDao.getVisitNum(course);
	}

	@Override
	public void addResCollect(CoursewareVO course) {
		coursewareDao.addResCollect(course);
	}

	@Override
	public void deleteResCollect(Integer id) {
		coursewareDao.deleteResCollect(id);
	}

	@Override
	public List<CoursewareVO> getResCollect(CoursewareVO course) {
		return coursewareDao.getResCollect(course);
	}

	@Override
	public List<CourseInfoVO> getOtherCourse(CourseInfoVO course) {
		return coursewareDao.getOtherCourse(course);
	}

	@Override
	public Integer getResCollectCount(CoursewareVO course) {
		return coursewareDao.getResCollectCount(course);
	}

	@Override
	public void updateSequenceNumber(CoursewareVO course) {
		coursewareDao.updateSequenceNumber(course);
	}

	
	
}
