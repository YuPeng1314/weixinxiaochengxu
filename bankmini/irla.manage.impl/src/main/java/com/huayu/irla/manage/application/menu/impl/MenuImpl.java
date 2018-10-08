package com.huayu.irla.manage.application.menu.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.menu.vo.NodeMenuVO;
import com.huayu.irla.core.privilege.vo.SysAuthoritiesResourceVO;
import com.huayu.irla.core.privilege.vo.SysResoucesVO;
import com.huayu.irla.manage.application.menu.IMenu;
import com.huayu.irla.manage.service.menu.IMenuService;
import com.huayu.irla.manage.util.UserUtils;
import com.huayu.irla.privilege.manage.service.URLFilterInvocationSecurityMetadataSource;
import com.huayu.irla.privilege.manage.vo.SysAuthoritiesVO;

/**
 * @author luozehua
 *
 * @time 2016年8月24日-下午5:08:48
 */
@Component("nodeMenu")
public class MenuImpl implements IMenu {

    @Autowired
    private IMenuService menuServiceImpl;
    
    @Autowired
    private URLFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Override
    public JSONObject findMenus(NodeMenuVO menu) {
        List<NodeMenuVO> menuList = null;
        menuList = menuServiceImpl.findMenus(menu);
        Integer count = menuServiceImpl.getCount(menu);
        JSONObject result = new JSONObject();
        result.put("rows", JSONArray.toJSON(menuList));
        result.put("total", count);
        return result;
    }

    @Override
    public NodeMenuVO updateUI(String id) {
        if (id == null) {
            return null;
        }
        NodeMenuVO menu = new NodeMenuVO();
        menu.setId(Integer.parseInt(id));
        List<NodeMenuVO> menuList = menuServiceImpl.findMenus(menu);
        NodeMenuVO menuTemp = null;
        if (CollectionUtils.isNotEmpty(menuList)) {
            menuTemp = menuList.get(0);
        }
        return menuTemp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation=Isolation.READ_UNCOMMITTED)
    public boolean impromntMenuItem(NodeMenuVO menu) {
        if (menu != null) {
            if (menu.getId() == null) {
                //新增
                return addMenuItem(menu);
            } else {
                return updateMenuItem(menu);
            }
            
        }
        return false;
    }

    /**
      * @Title: updateMenuItem
      * @Description: 
      * @author luozehua
      * @date 2016年11月10日 上午11:53:42
      * @param menu
      */

    private boolean updateMenuItem(NodeMenuVO menu) {
        String userName = UserUtils.getLoginName();
        //删除权限关系
        delAuthRelation(menu);
        //关系重建
        addMenuAuthRelaTion(menu);
        menu.setLastUpdatedBy(userName);
        Integer num = menuServiceImpl.updateMenu(menu);
        
        //更新权限加载
        List<String> types = new ArrayList<String>();
		//后台权限和菜单
		types.add("admin");
		types.add("menu");
        securityMetadataSource.refreshResuorceMap(types);
        
        if (num > 0) {
            return true;
        }
        return false;
    }

    /**
      * @Title: addMenuItem
      * @Description: 
      * @author luozehua
      * @date 2016年11月9日 下午8:05:07
      * @param menu
      */

    private boolean addMenuItem(NodeMenuVO menu) {
        String userName = UserUtils.getLoginName();
        addMenuAuthRelaTion(menu);
        //添加菜单
        menu.setCreatedBy(userName);
        menu.setLastUpdatedBy(userName);
        Integer num = menuServiceImpl.addMenu(menu);
        if (num > 0) {
            return true;
        }
        return false;
    }

    /**
      * @Title: addMenuAuthRelaTion
      * @Description: 添加菜单选项的权限关系
      * @author luozehua
      * @date 2016年11月10日 上午11:25:48
      * @param menu
      */
    private void addMenuAuthRelaTion(NodeMenuVO menu) {
        //查询是否存在Mark为4的则添加一个mark为4 的记录
        String userName = UserUtils.getLoginName();
        SysAuthoritiesVO sysAuth = new SysAuthoritiesVO();
        sysAuth.setType("admin");
        sysAuth.setAuthorityName(menu.getAuthorityName());
        sysAuth.setAuthorityMark("1");
        List<SysAuthoritiesVO> sysAuthList = menuServiceImpl.findSysAuth(sysAuth);
        if (CollectionUtils.isEmpty(sysAuthList)) {
            sysAuth.setAuthorityName(menu.getAuthorityName());
            sysAuth.setCreatedBy(userName);
            sysAuth.setLastUpdatedBy(userName);
            sysAuth.setMessage(menu.getAuthorityName());
            sysAuth.setModuleID("1");
            sysAuth.setAuthorityDesc(menu.getAuthorityName());
            //增加4的记录
            menuServiceImpl.addSysAuth(sysAuth);
            sysAuthList = menuServiceImpl.findSysAuth(sysAuth);
        }
        String authId = sysAuthList.get(0).getAuthorityID();
        //往sys_resources表中插入一条数据
        SysResoucesVO sysResVo = new SysResoucesVO();
        sysResVo.setIsSys("1");
        sysResVo.setPriority("0");
        sysResVo.setModuleId("1");
        sysResVo.setType("menu");
        sysResVo.setResourceType("URL");
        sysResVo.setResourceDesc(menu.getDescription());
        sysResVo.setResourceName(menu.getmCname());
        sysResVo.setResourcePath(StringUtils.substringBefore(menu.getmUrl(), "."));
        sysResVo.setCreatedBy(userName);
        sysResVo.setLastUpdatedBy(userName);
        menuServiceImpl.addSysResource(sysResVo);
        //查询id
        List<SysResoucesVO> resourceList = menuServiceImpl.findSysResource(sysResVo);
        //往sys_authorities_resources维护关系
        SysAuthoritiesResourceVO rarVo = null;
        if (CollectionUtils.isNotEmpty(resourceList)) {
            SysResoucesVO sysResoucesVO = resourceList.get(0);
            rarVo = new SysAuthoritiesResourceVO();
            rarVo.setAuthorityId(authId);
            rarVo.setResourceId(sysResoucesVO.getResourceId());
            rarVo.setCreatedBy(userName);
            rarVo.setLastUpdatedBy(userName);
            menuServiceImpl.addSysAuthRes(rarVo);
            menu.setResourceId(sysResoucesVO.getResourceId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMenuItem(String id) {
        if (id == null) {
            return false;
        }
        NodeMenuVO menu = new NodeMenuVO();
        menu.setId(Integer.parseInt(id));
        List<NodeMenuVO> findMenus = menuServiceImpl.findMenus(menu);
        if (CollectionUtils.isEmpty(findMenus)) {
            return false;
        }
        NodeMenuVO menuTemp = findMenus.get(0);
        //如果删除的是顶级菜单(子菜单项都要删除)
        Integer pid = menuTemp.getmPid();
        Integer mCode = menuTemp.getmCode();
        if (pid == 0) {
            //顶级菜单
            //查询其下的子菜单项
            NodeMenuVO param = new NodeMenuVO();
            param.setmPid(mCode);
            List<NodeMenuVO> subMenuList = menuServiceImpl.findMenus(param);
            for (NodeMenuVO nodeMenuVO : subMenuList) {
                delAuthRelation(nodeMenuVO);
                menuServiceImpl.deleteMenu(String.valueOf(nodeMenuVO.getId()));
            }
        }
        //执行删除操作以前把其关系干掉
        delAuthRelation(menuTemp);
        menuServiceImpl.deleteMenu(id);
        return true;
    }

    /**
      * @Title: delAuthRelation
      * @Description: 
      * @author luozehua
      * @date 2016年11月9日 下午8:34:27
      * @param id
      */

    private void delAuthRelation(NodeMenuVO menuTemp) {
        SysResoucesVO sysResVo = new SysResoucesVO();
        sysResVo.setResourcePath(StringUtils.substringBefore(menuTemp.getmUrl(), "."));
        sysResVo.setResourceId(menuTemp.getResourceId());
        List<SysResoucesVO> resList = menuServiceImpl.findSysResource(sysResVo);
        if (CollectionUtils.isNotEmpty(resList)) {
            for (SysResoucesVO sysResoucesVO : resList) {
                //删除资源
                menuServiceImpl.deleteSysResource(sysResoucesVO.getResourceId());
                //删除对应的关心
                SysAuthoritiesResourceVO rarVo = new SysAuthoritiesResourceVO();
                rarVo.setResourceId(sysResoucesVO.getResourceId());
                menuServiceImpl.deleteSysAuthRes(rarVo);
            }
        }
    }

    @Override
    public List<String> getAdminAuthName() {
        SysAuthoritiesVO sysAuth = new SysAuthoritiesVO();
        sysAuth.setType("admin");
        List<SysAuthoritiesVO> authList = menuServiceImpl.findSysAuth(sysAuth);
        //去掉重复的authorityName
        Set<String> authorityNameSet = new HashSet<String>();
        if (CollectionUtils.isNotEmpty(authList)) {
            for (SysAuthoritiesVO sysAuthoritiesVO : authList) {
                authorityNameSet.add(sysAuthoritiesVO.getAuthorityName());
            }
        }
        List<String> authorityNamelist = new ArrayList<String>();
        authorityNamelist.addAll(authorityNameSet);
        return authorityNamelist;
    }

}
