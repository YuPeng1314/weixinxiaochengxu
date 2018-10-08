package com.huayu.irla.manage.application.datadict.impl;


import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.datadict.vo.DataDictVO;
import com.huayu.irla.core.service.datadict.IDataDictService;
import com.huayu.irla.manage.application.datadict.IDataDict;
import com.huayu.irla.manage.util.UserUtils;

/**
 * 
  * @ClassName: DataDictImpl
  * @Description: 数据字典
  * @author liuwei
  * @date 2016年10月11日 上午11:59:29
  *
 */
@Component("datadict")
public class DataDictImpl implements IDataDict {

    private static final Logger logger = Logger.getLogger(DataDictImpl.class);
    @Autowired
    private IDataDictService dataDictServiceImpl;

    @Override
    public JSONObject getValueList(DataDictVO datadict) {
        List<DataDictVO> datadictlist = Collections.emptyList();
        //取得数据
        datadictlist = dataDictServiceImpl.getValue(datadict);
        for(DataDictVO tempVO:datadictlist){
        	if(tempVO.getDataSwitch()!=1){
    			String value = "******";
    			tempVO.setDictionaryValue(value);
    		}
        }
        //取得数据的总数
        Integer count = dataDictServiceImpl.getCount(datadict);
        JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(datadictlist));
        result.put("total", count);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDataDict(DataDictVO datadict) {
    	String userName = UserUtils.getLoginName();
        if (datadict != null) {
            //如果ID为空，则添加
            if (StringUtils.isBlank(datadict.getId().trim())) {
            	if(checkKey(datadict)){
                    datadict.setCreatedBy(userName);
                    dataDictServiceImpl.addDataDict(datadict);
                    return true;
            	}else{
            		logger.error("属性名重复");
            		return false;
            	}
            }
            //如果ID不为空，则修改
            else {
            	DataDictVO tempVO =  new DataDictVO();
            	tempVO.setId(datadict.getId());
                 //取得数据
            	List<DataDictVO> datalist = dataDictServiceImpl.getValue(tempVO);
            	DataDictVO checkVO = null;
            	if (CollectionUtils.isEmpty(datalist)) {
            		logger.error("空指针异常");
            		return false;
            	}
            	checkVO  = datalist.get(0);
            	//判断此次是否有修改的key值
            	if(!StringUtils.equals(datadict.getDictionaryKey(), checkVO.getDictionaryKey())){
            		if(checkKey(datadict)){
            			datadict.setLastUpdatedBy(userName);
                        dataDictServiceImpl.updateDataDict(datadict);
                        return true;
                	}else{
                		logger.error("属性名重复");
                		return false;
                	}
            	}
            	datadict.setLastUpdatedBy(userName);
                dataDictServiceImpl.updateDataDict(datadict);
                return true;
            }
        }
        return false;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataDictVO updateDataDict(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            DataDictVO datadict = new DataDictVO();
            datadict.setId(id);
            //取得数据
        	List<DataDictVO> datalist = dataDictServiceImpl.getValue(datadict);
        	DataDictVO datadictVO = null;
        	if (CollectionUtils.isEmpty(datalist)) {
        		logger.error("空指针异常");
        		return null;
        	}
        	datadictVO  = datalist.get(0);
            if(datadictVO.getDataSwitch()!=1){
    			String value = "******";
    			datadictVO.setDictionaryValue(value);
    		}
            return datadictVO;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean validDataDict(String id, String state) {
        if (StringUtils.isBlank(id)) {
            return false;
        }
        DataDictVO datadict = new DataDictVO();
        datadict.setId(id);
        //取得数据
    	List<DataDictVO> datalist = dataDictServiceImpl.getValue(datadict);
    	DataDictVO datadictVO = null;
    	if (CollectionUtils.isEmpty(datalist)) {
    		logger.error("空指针异常");
    		return false;
    	}
		datadictVO  = datalist.get(0);
        if ("false".equals(state)) {
            //如果state的状态为false
            datadictVO.setIsValid("0");
        } else {
            //如果state的状态为true
            datadictVO.setIsValid("1");
        }
        dataDictServiceImpl.updateDataDict(datadictVO);
        return true;
    }

    @Override
    public boolean checkKey(DataDictVO datadict) {
        boolean flag = true;
        if (StringUtils.isBlank(datadict.getDictionaryKey())) {
            flag = false;
        }
        List<DataDictVO> datadictList = dataDictServiceImpl.getValue(new DataDictVO());
        for (DataDictVO datadictVO : datadictList) {
            //判断Key是否重复
            if (StringUtils.equalsIgnoreCase(datadict.getDictionaryKey(), datadictVO.getDictionaryKey())) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
