package com.huayu.core.util;

import java.io.File;
import java.util.Comparator;

/**        
 * 类名称：FileComparator    
 * 类描述：用来根据文件的文件名来对文件进行排序   
 * 创建人：fzh    
 * 创建时间：2016-10-10 
 * @version 1.0    
 *     
 */
public class FileComparator implements Comparator<File>{

    
   /**
    * 按名字排序
    */
    public int compare(File f1, File f2) {
        return f2.getName().compareTo(f1.getName());
        
    }  
}
