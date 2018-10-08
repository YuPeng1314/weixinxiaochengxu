package com.huayu.irla.manage.service.courseware.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import com.huayu.irla.core.courseware.vo.FileUploadItemVO;
import com.huayu.irla.core.courseware.vo.MultipartFileParamVO;
import com.huayu.irla.core.service.courseware.ICoursewareService;
import com.huayu.irla.manage.service.courseware.IStorageService;
import com.huayu.irla.manage.service.courseware.constant.Constants;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class StorageServiceImpl implements IStorageService {

    private final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
    
    // 保存文件的根目录
    private Path rootPaht;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
	  
	@Autowired
	private ICoursewareService coursewareServiceImpl;

    //这个必须与前端设定的值一致
    @Value("${breakpoint.upload.chunkSize}")
    private long CHUNK_SIZE;

    @Value("${breakpoint.upload.dir}")
    private String finalDirPath;

    @Autowired
    public StorageServiceImpl(@Value("${breakpoint.upload.dir}") String location) {
        this.rootPaht = Paths.get(location);
    }

    @Override
    public void deleteAll() {
        logger.info("开发初始化清理数据，start");
        FileSystemUtils.deleteRecursively(rootPaht.toFile());
        stringRedisTemplate.delete(Constants.FILE_UPLOAD_STATUS);
        stringRedisTemplate.delete(Constants.FILE_MD5_KEY);
        logger.info("开发初始化清理数据，end");
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootPaht);
        } catch (FileAlreadyExistsException e) {
            logger.error("文件夹已经存在了，不用再创建。");
        } catch (IOException e) {
            logger.error("初始化root文件夹失败。", e);
        }
    }

    @Override
    public void uploadFileRandomAccessFile(Attachment file, MultipartFileParamVO param) throws IOException {
    	RandomAccessFile accessTmpFile = null;
    	
    	try {
            //得到临时文件目录
            String tempDirPath = finalDirPath + param.getMd5();
            //根据md5值生成对应的文件
            String tempFileName = param.getMd5() + "_tmp";
            File tmpDir = new File(tempDirPath);
            //创建临时目录
            if (!tmpDir.exists()) {
                tmpDir.mkdirs();
            }
            
            File tmpFile = new File(tempDirPath, tempFileName);
            
            //读取文件，random,可通过指针位移读取
            accessTmpFile = new RandomAccessFile(tmpFile, "rw");
            long offset = CHUNK_SIZE * param.getChunk();
            //定位到该分片的偏移量
            accessTmpFile.seek(offset);
            //写入该分片数据
            accessTmpFile.write(IOUtils.readBytesFromStream(file.getDataHandler().getInputStream()));
            
            //检查上传状况,完成即存入数据库中
            boolean isComplete = checkAndSetUploadProgress(param, tempDirPath);
            if (isComplete) {
            	FileUploadItemVO fileUploadItemVO = new FileUploadItemVO();
            	//md5
            	fileUploadItemVO.setMd5(param.getMd5());
            	//上传成功,等待转码
            	fileUploadItemVO.setStatus("1");
            	//更新状态
            	coursewareServiceImpl.updateUploadFile(fileUploadItemVO);
            }
    	} catch(IOException ex) {
    		logger.error("读写不成功", ex);
    	} finally {
    		if(null != accessTmpFile) {
    			accessTmpFile.close();
    		}
    	}
    }
    

    /**
     * 检查并修改文件上传进度
     *
     * @param param
     * @param uploadDirPath
     * @return
     * @throws IOException
     */
    private boolean checkAndSetUploadProgress(MultipartFileParamVO param, String uploadDirPath) throws IOException {
    	//随机读取配置文件
    	RandomAccessFile accessConfFile = null;
    	byte isComplete = Byte.MAX_VALUE;
    	//文件名
    	String md5Name = param.getMd5();
    	try {
	    	//新建一个文件后缀为 .conf文件,取md5值
	        File confFile = new File(uploadDirPath, md5Name + ".conf");
	        accessConfFile = new RandomAccessFile(confFile, "rw");
	        
	        //预分配分片大小的文件空间 
	        accessConfFile.setLength(param.getChunks());
	        //定位到当前分片的位置
	        accessConfFile.seek(param.getChunk());
	        //标记下当前分片为byte类型的最大值
	        accessConfFile.write(Byte.MAX_VALUE);
	
	        //completeList 检查是否全部完成,如果数组里是否全部都是(全部分片都成功上传)
	        byte[] completeList = FileUtils.readFileToByteArray(confFile);
	        
	        //判断分片的状态,不等于标识的最大值退出循环
	        for (int i = 0; i<completeList.length&&isComplete==Byte.MAX_VALUE; i++) {
	            //与运算, 如果有部分没有完成则 isComplete 不是 Byte.MAX_VALUE
	            isComplete = (byte)(isComplete & completeList[i]);
	        }
	
    	} catch(IOException ex) {
    		logger.error("检查分片逻辑出错", ex);
    		throw ex;
    	} finally {
    		if(null != accessConfFile) {
    			try {
					accessConfFile.close();
				} catch (IOException ex) {
					logger.error("通道关闭不成功", ex);
				}
    		}
    	}
    	
    	//状态 md5值保存，当全部完成上传合并了，就状态标识为true
        if (isComplete == Byte.MAX_VALUE) {
            stringRedisTemplate.opsForHash().put(Constants.FILE_UPLOAD_STATUS, param.getMd5(), "true");
            stringRedisTemplate.opsForValue().set(Constants.FILE_MD5_KEY + param.getMd5(), uploadDirPath + "/" + md5Name + ".conf");
            return true;
        } else {
        	//当上传没有完成，设置为false
            if (!stringRedisTemplate.opsForHash().hasKey(Constants.FILE_UPLOAD_STATUS, param.getMd5())) {
                stringRedisTemplate.opsForHash().put(Constants.FILE_UPLOAD_STATUS, param.getMd5(), "false");
            }
            //配置文件重新更新
//            if (!stringRedisTemplate.hasKey(Constants.FILE_MD5_KEY + param.getMd5())) {
                stringRedisTemplate.opsForValue().set(Constants.FILE_MD5_KEY + param.getMd5(), uploadDirPath + "/" + md5Name + ".conf");
//            }
            return false;
        }
 
    }

    /**
     * 上传文件完成后，并进行文件重命名
     * @param toBeRenamed   将要修改名字的文件
     * @param toFileNewName 新的名字
     * @return
     */
    public boolean renameFile(File toBeRenamed, String toFileNewName) {
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            logger.info("File does not exist: " + toBeRenamed.getName());
            return false;
        }
        String p = toBeRenamed.getParent();
        File newFile = new File(p + File.separatorChar + toFileNewName);
        //修改文件名
        return toBeRenamed.renameTo(newFile);
    }

}
