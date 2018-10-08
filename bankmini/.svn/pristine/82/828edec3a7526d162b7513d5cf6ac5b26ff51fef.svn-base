package com.huayu.irla.core.courseware.vo;


import java.io.Serializable;



public class MultipartFileParamVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5346124134309158330L;

	/**
     * 用户id
     */
    private String uid;
    
    /**
     * 任务ID
     */
    private String id;
    
    /**
     * 总分片数量
     */
    private int chunks;
    
    /**
     * 当前为第几块分片
     */
    private int chunk;
   
    /**
     * 当前分片大小
     */
    private long size = 0L;
    
    /**
     * 文件名
     */
    private String name;
    
    
    /**
     * MD5值
     */
    private String md5;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "MultipartFileParam{" +
                "uid='" + uid + '\'' +
                ", id='" + id + '\'' +
                ", chunks=" + chunks +
                ", chunk=" + chunk +
                ", size=" + size +
                ", name='" + name + '\'' +
//                ", file=" + file +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
