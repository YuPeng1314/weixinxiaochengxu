package com.huayu.irla.manage.service.courseware;
import java.io.IOException;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import com.huayu.irla.core.courseware.vo.MultipartFileParamVO;


/**
 * 方法
 * @author Administrator
 *
 */
public interface IStorageService {

    /**
     * 删除全部数据
     */
    void deleteAll();

    /**
     * 初始化方法
     */
    void init();

    /**
     * 处理分块操作
     * @param param
     * @throws IOException
     */
	void uploadFileRandomAccessFile(Attachment file, MultipartFileParamVO param) throws IOException;

}
