package com.huayu.irla.manage.service.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.huayu.irla.core.privilege.vo.SysAuthoritiesResourceVO;
import com.huayu.irla.core.privilege.vo.SysResoucesVO;
import com.huayu.irla.privilege.manage.vo.SysAuthoritiesVO;
import com.huayu.irla.privilege.manage.vo.SysUsersVO;
import com.huayu.irla.core.menu.vo.NodeMenuVO;

/**
 * 菜单服务接口
 * 
 * @author luozehua
 *
 * @time 2016年8月24日-下午4:36:39
 */
public interface IMenuService {
    /**
      * @Title: getMenuList
      * @Description: 通过用户信息来获取
      * @author luozehua
      * @date 2016年11月9日 上午11:37:10
      * @param userInfo
      * @return
     */
    JSONArray getMenuList(SysUsersVO userInfo, HttpServletRequest request);

    /**
      * @Title: findMenus
      * @Description: 分页查询菜单，后台管理
      * @author luozehua
      * @date 2016年11月7日 下午3:05:27
      * @param page
      * @return
     */
    List<NodeMenuVO> findMenus(NodeMenuVO menu);

    Integer getCount(NodeMenuVO menu);

    /**
     * @Title: addMenu
     * @Description: 添加菜单栏目
     * @author luozehua
     * @date 2016年11月7日 上午11:37:48
     * @return
    */
    Integer addMenu(NodeMenuVO menu);

    /**
     * @Title: updateMenu
     * @Description: 修改菜单栏目 
     * @author luozehua
     * @date 2016年11月7日 上午11:38:04
     * @return
    */
    Integer updateMenu(NodeMenuVO menu);

    /**
     * @Title: deleteMenu
     * @Description: 删除菜单栏目
     * @author luozehua
     * @date 2016年11月7日 上午11:38:21
     * @return
    */
    Integer deleteMenu(String id);

    List<SysAuthoritiesVO> findSysAuth(SysAuthoritiesVO sysAuth);

    Integer addSysAuth(SysAuthoritiesVO sysAuth);

    /**
     * @Title: addSysResource
     * @Description: 添加系统资源配置（权限）
     * @author luozehua
     * @date 2016年11月8日 下午3:24:22
     * @param sysResVo
     * @return
    */
    Integer addSysResource(SysResoucesVO sysResVo);

    /**
     * @Title: deleteSysResource
     * @Description: 删除系统资源配置（权限）
     * @author luozehua
     * @date 2016年11月8日 下午3:24:26
     * @param id
     * @return
    */
    Integer deleteSysResource(String id);

    /**
     * @Title: addSysAuthRes
     * @Description: 添加权限关系
     * @author luozehua
     * @date 2016年11月8日 下午3:24:29
     * @param rarVo
     * @return
    */
    Integer addSysAuthRes(SysAuthoritiesResourceVO rarVo);

    /**
     * @Title: deleteSysAuthRes
     * @Description: 删除资源权限关系
     * @author luozehua
     * @date 2016年11月8日 下午3:24:33
     * @param id
     * @return
    */
    Integer deleteSysAuthRes(SysAuthoritiesResourceVO rarVo);
    
    List<SysResoucesVO> findSysResource(SysResoucesVO sysResVo);
}
