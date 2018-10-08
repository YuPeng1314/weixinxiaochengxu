package com.huayu.irla.manage.application.datadict;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.datadict.vo.DataDictVO;
import com.huayu.irla.privilege.manage.annotation.PrivilegePoint;
import com.huayu.irla.privilege.manage.annotation.PrivilegePointor;

/**
 * 
  * @ClassName: IDataDict
  * @Description: 数据字典
  * @author liuwei
  * @date 2016年10月11日 上午11:57:50
  *
 */
@Path("/")
//数据字典的权限配置
@PrivilegePointor("Data Dictionary")
public interface IDataDict {

    /**
     * 
      * @Title: getValueList
      * @Description: 数据字典更新
      * @param  datadict
      * @return JSONObject
     */
    @POST
    @Path("/getValue")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_READ ,privilegeName="find DataValue")
    JSONObject getValueList(DataDictVO datadict);

    /**
     * 
      * @Title: addDataDict
      * @Description: 添加数据字典
      * @param datadict
      * @return void
     */

    @POST
    @Path("/addDataDict")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_ADD ,privilegeName="add Data")
    boolean addDataDict(DataDictVO datadict);

    /**
     * 
      * @Title: updateDataDict
      * @Description: 修改数据字典
      * @param  id
      * @return DataDictVO
     */

    @POST
    @Path("/updateDataDict/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_UPDATE ,privilegeName="update Data")
    DataDictVO updateDataDict(@PathParam(value = "id") String id);

    /**
     * 
      * @Title: validDataDict
      * @Description: 有效无效切换
      * @author liuwei
      * @date 2016年10月11日 下午12:09:38
      * @param id
      * @param state
      * @return
     */
    @POST
    @Path("/validDataDict/{id}/{state}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_OTHER ,privilegeName="valid Data")
    boolean validDataDict(@PathParam(value = "id") String id, @PathParam(value = "state") String state);

    /**
     * 
      * @Title: checkKey
      * @Description: 检查Key是否重复
      * @author liuwei
      * @date 2016年10月11日 下午3:59:15
      * @param dictionaryKey
      * @return
     */
    @POST
    @Path("/checkKey")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @PrivilegePoint(privilegeMode=PrivilegePoint.ROLE_OTHER ,privilegeName="check Key")
    boolean checkKey(DataDictVO datadict);

}
