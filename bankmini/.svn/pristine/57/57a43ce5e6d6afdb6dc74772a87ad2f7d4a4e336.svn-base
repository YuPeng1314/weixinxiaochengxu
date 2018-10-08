package com.huayu.irla.manage.application.courseware.impl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.courseware.vo.FileUploadFileRelationVO;
import com.huayu.irla.core.courseware.vo.FileUploadItemVO;
import com.huayu.irla.core.courseware.vo.MultipartFileParamVO;
import com.huayu.irla.core.service.catalog.ICatalogCodeService;
import com.huayu.irla.core.service.courseware.ICoursewareService;
import com.huayu.irla.manage.application.courseware.ICoursewarenew;
import com.huayu.irla.manage.service.courseware.IStorageService;
import com.huayu.irla.manage.service.courseware.constant.Constants;
import com.huayu.irla.manage.service.courseware.jms.adapter.Product;
import com.huayu.irla.manage.service.courseware.jms.producer.MessageProducer;
import com.huayu.irla.manage.service.courseware.other.ResultStatus;
import com.huayu.irla.manage.service.courseware.other.ResultVo;
import com.huayu.irla.manage.util.UserUtils;

/**
 * 
  * @ClassName: CoursewareImpl
  * @Description: 课件维护Action层的实现类
  * @author liuwei
  * @date 2017年5月25日 上午10:02:24
  *
 */
@Component("coursewarenew")
@Transactional
public class CoursewarenewImpl implements ICoursewarenew {
	
	  @Value("${breakpoint.upload.dir}")
	  private String finalDirPath;
		
	  @Autowired
	  private StringRedisTemplate stringRedisTemplate;
	  
	  private Logger logger = LoggerFactory.getLogger(CoursewarenewImpl.class);
	  
	  @Autowired
	  private IStorageService storageService;

	  @Autowired
	  private ICoursewareService coursewareServiceImpl;
	  
	  /**
	   * 转码消息
	   */
	  @Autowired
	  private MessageProducer messageProducer;
	  
	  
	  /**
	   * 目录树
	   */
	  @Autowired
	  private ICatalogCodeService catalogCodeService;

	@Override
	 public Object checkFileMd5(String md5, String fileName) throws IOException {
    	//取到这个md5的状态
        Object processingObj = stringRedisTemplate.opsForHash().get(Constants.FILE_UPLOAD_STATUS, md5);
        //为空该文件不存在
        if (processingObj == null) {
        	saveUploadFile(md5, fileName);
            return new ResultVo(ResultStatus.NO_HAVE);
        }
        //取到目前的文件状态，false为还没有上传完成，true表示已经上传完成
        String processingStr = processingObj.toString();
        boolean processing = Boolean.parseBoolean(processingStr);   
        
       //取到配置conf配置文件
        String value = stringRedisTemplate.opsForValue().get(Constants.FILE_MD5_KEY + md5);
        //为true并返回该值
        if (processing) {
            return new ResultVo(ResultStatus.IS_HAVE, value);
        } else {
        	
            //取到配置文件
            File confFile = new File(value);
            //读取文件到每个byte,其中一个不等于MAX_VALUE即为缺失的部分，并返回
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            List<String> missChunkList = new LinkedList<>();
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    missChunkList.add(i + "");
                }
            }
            //记录并返回缺失的部分
            return new ResultVo<>(ResultStatus.ING_HAVE, missChunkList);
        }
    }
    
	  /**
     * 第一次上传,判断不存在就存入
     * @param param
     * @param tempDirPath
     */
    private void saveUploadFile(String md5, String fileName) {
    	//判断md5值
    	FileUploadItemVO fileUploadItemVO = new FileUploadItemVO();
    	//得到用户编码
    	String userCode = UserUtils.getLoginName();
    	fileUploadItemVO.setMd5(md5);
    	fileUploadItemVO.setCreatedBy(userCode);
    	fileUploadItemVO.setLastUpdatedBy(userCode);
    	
    	 String tempDirPath =  finalDirPath + md5;
    	
    	//判断是否存在对应的文件
    	List<FileUploadItemVO> fileUploadItem = coursewareServiceImpl.getUploadFile(fileUploadItemVO);
    	if(null!= fileUploadItem && !fileUploadItem.isEmpty()) {
    		return;
    	}
    	//文件项存入项
    	FileUploadItemVO fileUploadVO = new FileUploadItemVO();
    	String plateformFlag = UserUtils.getplateCode();
    	fileUploadVO.setAppId(plateformFlag==null?"PL":plateformFlag);
    	fileUploadVO.setCreatedBy(userCode);
    	fileUploadVO.setLastUpdatedBy(userCode);
    	int fileSubfix = fileName.lastIndexOf(".");
    	if(fileSubfix > -1) {
    		fileName = fileName.substring(0, fileSubfix);
    	}
    	fileUploadVO.setResourceName(fileName);
    	fileUploadVO.setResourcePath(tempDirPath);
    	//0默认为视频，转码后视情况为视频或音频
    	fileUploadVO.setResourceType("0");
    	fileUploadVO.setMd5(md5);
    	coursewareServiceImpl.addUploadFile(fileUploadVO);
    }

    /**
     * 上传文件
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Response fileUpload(Attachment file,MultipartFileParamVO param) {
       Message message = PhaseInterceptorChain.getCurrentMessage();
       HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
    	//判断enctype属性是否为multipart/form-data
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            logger.info("上传文件start。");
            try {
                //这个更快点
                storageService.uploadFileRandomAccessFile(file, param);
            } catch (IOException e) {
                logger.error("文件上传失败。{}", param.toString());
                Response.ok().entity("上传失败").build();
            }
            logger.info("上传文件end。");
        }
        return Response.ok().entity("上传成功。").build();
    }
    
    
    

	@Override
	public void sendFileMessTransCoding(FileUploadItemVO itemVO) {
		//状态: 0上传中，1等待转码，2转码等待中，3转码失败，4转码成功
		//得到用户编码
		String userCode = UserUtils.getLoginName();
		itemVO.setStatus("2");
		coursewareServiceImpl.updateUploadFile(itemVO);
		
		//得到平台标识
		String plateformFlag = UserUtils.getplateCode();
		//组装并发送消息
		Product tmpPro = new Product();
		tmpPro.setFileId(itemVO.getId());
		tmpPro.setUserCode(userCode);
		tmpPro.setPlateformCode(plateformFlag);
	    messageProducer.sendMessage(tmpPro);
		
	}

	
	@Override
	public JSONArray getCatalogTree() {
		
		return catalogCodeService.getCatalogTree();
	}
	

	@Override
	public JSONObject getUploadFileList(FileUploadItemVO fileItemVO) {
		//得到用户编码
		String userCode = UserUtils.getLoginName();
		//是否系统管理员
		boolean isSystemAdmin = UserUtils.isSystemAdmin();
		if(!isSystemAdmin) {
			fileItemVO.setCreatedBy(userCode);
		}
		fileItemVO.setLastUpdatedBy(userCode);
		
		
		List<FileUploadItemVO> coursewareList = coursewareServiceImpl.getUploadFile(fileItemVO, isSystemAdmin);
		
		//得到总条数
		Integer coursewareNum = coursewareServiceImpl.getUploadFileCount(fileItemVO);
		
		JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(coursewareList));
        result.put("total", coursewareNum==null? 0:coursewareNum.intValue());
        
        return result;
		
	}
	
	

	/**
	 * 插入关系表
	 * @param fileRelationVO
	 */
	@Override
	public void insertUploadFileRelation(FileUploadFileRelationVO fileRelationVO) {
		//得到用户编码
		String userCode = UserUtils.getLoginName();
		fileRelationVO.setCreatedBy(userCode);
		fileRelationVO.setLastUpdatedBy(userCode);
		List<FileUploadFileRelationVO> fileRelationVOTmp = coursewareServiceImpl.getUploadFileRelation(fileRelationVO.getUploadFileId());
		if(null==fileRelationVOTmp || fileRelationVOTmp.isEmpty()) {
			coursewareServiceImpl.insertUploadFileRelation(fileRelationVO);
		} else {
			coursewareServiceImpl.updateUploadFileRelationById(fileRelationVO);
		}
	}

	@Override
	public void deleteUploadFile(FileUploadItemVO fileItemVO) {
		//先去取到对应的值
		String userCode = UserUtils.getLoginName();
		fileItemVO.setCreatedBy(userCode);
		List<FileUploadItemVO> itemVOs = coursewareServiceImpl.getUploadFile(fileItemVO);
		
		coursewareServiceImpl.deleteUploadFile(fileItemVO);
		coursewareServiceImpl.deleteUploadFileRelation(fileItemVO);
		//删除对应的文件和redis里面的记录
		try {
			
			if(null!=itemVOs && itemVOs.size()>0) {
				FileUploadItemVO tmpItem = itemVOs.get(0);
				String md5 = tmpItem.getMd5();
				if(StringUtils.isNotBlank(md5)) {
					stringRedisTemplate.opsForHash().delete(Constants.FILE_UPLOAD_STATUS, md5);
					String tempDirPath = finalDirPath + md5;
					File file = new File(tempDirPath);
					//删除目录
					deleteFile(file);
				}
			}
			
		} catch(Exception ex) {
			logger.error("redis和文件数据删除失败", ex);
		}
		
	}
	
	//删除文件
	private void deleteFile(File file) {  
			//判断文件是否存在 
			if (file.exists()) { 
				 //判断是否是文件  
			     if (file.isFile()) {
			    	 file.delete();//删除文件   
			     } else if (file.isDirectory()) {//否则如果它是一个目录  
				      File[] files = file.listFiles();//声明目录下所有的文件 files[];  
				      for (int i=0;i<files.length; i++) {//遍历目录下所有的文件  
				    	  this.deleteFile(files[i]);//把每个文件用这个方法进行迭代  
				      }  
				      file.delete();//删除文件夹  
			     }  
			}
		}  

	@Override
	public List<FileUploadFileRelationVO> getUploadFileRelation(Long uploadFileId) {
		return coursewareServiceImpl.getUploadFileRelation(uploadFileId);
	}

	@Override
	public void dittoUploadFileRelation(FileUploadFileRelationVO fileRelationVO) {
		//得到用户编码
		String userCode = UserUtils.getLoginName();
		fileRelationVO.setCreatedBy(userCode);
		fileRelationVO.setLastUpdatedBy(userCode);
		coursewareServiceImpl.dittoUploadFileRelation(fileRelationVO);
	}
}
