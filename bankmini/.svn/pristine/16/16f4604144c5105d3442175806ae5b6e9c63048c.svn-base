package com.huayu.core.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

public final class ImageFormatConvert {
	
	 
	/** 
     * 图片格式转换
     * PNG->JPG
     * @param sourceFile 
     * @param orginFile 
     * @author:
     */  
	 public static void convertJPEG(String sourceFile,  String orginFile) throws Exception  { 
		 FileInputStream fis = new FileInputStream(sourceFile);
		 convertJPEG(fis, orginFile);
	 }
	 
	 /** 
     * 图片格式转换
     * PNG->JPG
     * @param inputStream 
     * @param orginFile 
     * @author:
     */  
    public static void convertJPEG(InputStream inputStream, String orginFile) throws Exception  {
    	convertJPEG(inputStream, orginFile, null, null);
    }
    
	 
	/** 
     * 图片格式转换
     * PNG->JPG
     * @param inputStream 
     * @param orginFile 
     * @author:
     */  
	 public static void convertJPEG(InputStream inputStream, String orginFile, Integer width, Integer height ) throws Exception  {
		try {
			BufferedImage bi = ImageIO.read(inputStream);    
				
	        BufferedImage newBufferedImage = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB); 
	        if(width==null || height==null) {
	        	newBufferedImage.createGraphics().drawImage(bi, 0, 0, Color.WHITE, null);  
	        } else {
	        	newBufferedImage.createGraphics().drawImage(bi, 0, 0, width, height, Color.WHITE, null); 
	        }
	        
	    	ImageIO.write(newBufferedImage,  "JPEG", new File(orginFile));  
	    	bi.flush();
		} finally {
			if(null != inputStream) {
				try {
					inputStream.close();
				} catch(IOException ex) {
					
				}
			}
		}
	 }
	
	/** 
     * 图片格式转换
     * PNG->JPG
     * @param sourceFile 
     * @author:
     */  
    public static ByteArrayInputStream convertJPEGInputStream(String sourceFile) throws Exception  {  
    	FileInputStream fis = new FileInputStream(sourceFile);
    	return convertJPEGInputStream(fis);
    }
    
    
    /** 
     * 图片格式转换
     * PNG->JPG
     * @param inputStream 
     * @author:
     */  
    public static ByteArrayInputStream convertJPEGInputStream(InputStream inputStream, byte[] hadRead) throws Exception  { 
    	try {
	    	byte[] srcArr = IOUtils.toByteArray(inputStream);
	    	byte[] desArr = new byte[hadRead.length+srcArr.length];
	    	
	    	
	    	System.arraycopy(hadRead, 0, desArr, 0, hadRead.length);
	    	System.arraycopy(srcArr, 0, desArr, hadRead.length, srcArr.length);
	    	
	    	ByteArrayInputStream ots = new ByteArrayInputStream(desArr);
	
	    	return convertJPEGInputStream(ots);
    	} finally {
    		IOUtils.closeQuietly(inputStream);
    	}
    }
    
    
	/** 
     * 图片格式转换
     * PNG->JPG
     * @param inputStream 
     * @author:
     */  
    public static ByteArrayInputStream convertJPEGInputStream(InputStream inputStream) throws Exception  {  
    	ByteArrayInputStream swapStream = null;
    	ByteArrayOutputStream ots = new ByteArrayOutputStream();
    	try {
			BufferedImage bi = ImageIO.read(inputStream);    
			
	        BufferedImage newBufferedImage = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);  
	        newBufferedImage.createGraphics().drawImage(bi, 0, 0, Color.WHITE, null);  
	        
	        
	    	ImageIO.write(newBufferedImage,  "JPEG", ots);  
	    	swapStream = new ByteArrayInputStream(ots.toByteArray());
	        bi.flush();
        } finally {
        	try {
    			ots.close();
    		}catch(IOException ex) {
    			
    		}
        	
        	if(null != inputStream) {
        		try {
        			inputStream.close();
        		}catch(IOException ex) {
        			
        		}
        	}
        }
    	return swapStream;
    }  
    
    
    public static void main(String[] args) throws Exception {
    	//ImageFormatConvert.convertJPEG("d:\\logo-red.png", "d:\\logo-red-new67.png");
    	byte[] byt = new byte[1024];
    	ByteArrayInputStream ips = convertJPEGInputStream("E:/formerly/1510107547631.jpg");
    	int i = -1;
    	FileOutputStream fos = new FileOutputStream("E:\\66666.png");
    	while((i=ips.read(byt)) > -1) {
    		fos.write(byt, 0, i);
    	}
    	fos.close();
	}
}
