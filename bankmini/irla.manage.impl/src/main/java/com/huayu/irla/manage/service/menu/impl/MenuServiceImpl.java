package com.huayu.irla.manage.service.menu.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.core.menu.vo.NodeMenuVO;
import com.huayu.irla.core.privilege.vo.SysAuthoritiesResourceVO;
import com.huayu.irla.core.privilege.vo.SysResoucesVO;
import com.huayu.irla.core.privilege.vo.SysRoleAuthVO;
import com.huayu.irla.core.privilege.vo.SysUserRoleVO;
import com.huayu.irla.manage.dao.menu.IMenuDao;
import com.huayu.irla.manage.dao.menu.IUserMuneAuthDao;
import com.huayu.irla.manage.service.menu.IMenuService;
import com.huayu.irla.privilege.manage.common.UserMesCommon;
import com.huayu.irla.privilege.manage.vo.SysAuthoritiesVO;
import com.huayu.irla.privilege.manage.vo.SysRolesVO;
import com.huayu.irla.privilege.manage.vo.SysUsersVO;

/**
 * @author luozehua
 *
 * @time 2016年8月24日-下午4:39:03
 */
@Service
@Transactional
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private IMenuDao menuDao;

    @Autowired
    private IUserMuneAuthDao userMuneAuthDao;

    @Override
    @Cacheable(value="menuCache", key="#userInfo.username")
    public JSONArray getMenuList(SysUsersVO userInfo, HttpServletRequest request) {
        //--------------------------------------
        List<SysRolesVO> roleMess = userInfo.getRoleMess();
        //通过用户ID查找角色ID  sys_users_roles
        //  List<SysUserRoleVO> roleList = getRoleListByUser(userInfo);
        //通过角色ID查询权限ID  sys_roles_authorities
        List<SysRoleAuthVO> authList = getAuthListByRole(roleMess);
        //通过权限ID查询Resource_ID sys_authorities_resources
        List<SysAuthoritiesResourceVO> authResList = getAuthResList(authList);
        //通过Resource_ID查询resourcePath sys_resources
        List<SysResoucesVO> resList = getResList(authResList);
        //获取登录用户拥有的菜单条目的URL
        List<String> urlList = new ArrayList<String>();
        for (SysResoucesVO sysResoucesVO : resList) {
            urlList.add(sysResoucesVO.getResourcePath());
        }
        List<NodeMenuVO> tempMenuList = menuDao.getMenuList(new NodeMenuVO());
        JSONObject root = new JSONObject();
        combainMenuJson(tempMenuList, root, 0);
        JSONArray allMenuJson = root.getJSONArray("children");
        //遍历jsonArray，去掉没有权限的子菜单，如果父级没有子，则全部去掉
        if(!UserMesCommon.isSystemAdmin(request)) {
        	return authMenuItem(allMenuJson, urlList);
        } else{
        	return allMenuJson;
        }
    }

    /**
      * @Title: authMenuItem
      * @Description: 过滤登录用户没有权限的菜单项
      * @author luozehua
      * @date 2016年11月9日 下午7:26:30
      * @param allJsonStr 还系统拥有的所有的目录条目
      * @param urlList 登录用户应该具有的权限URL列表
      * @return
     */
    private JSONArray authMenuItem(JSONArray allMenuJson, List<String> urlList) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < allMenuJson.size(); i++) {
            // 获取每个大的菜单项
            JSONObject root = allMenuJson.getJSONObject(i);
            // 获取每个大的菜单项的子菜单项
            JSONArray children = root.getJSONArray("children");
            // 循环每个children
            JSONArray newChild = new JSONArray();
            for (int j = 0; j < children.size(); j++) {
                JSONObject leaf = children.getJSONObject(j);
                String mUrl = leaf.getString("mUrl");
                mUrl = mUrl.replace(".html", "");
                if (urlList.contains(mUrl)) {
                    // 如果有权限，则添加该节点
                    newChild.add(leaf);
                }
            }
            root.put("children", newChild);
            result.add(root);
        }

        // 去掉没有孩子的父节点
        for (int i = 0; i < result.size(); i++) {
            // 获取每个大的菜单项
            JSONObject root = result.getJSONObject(i);
            // 获取每个大的菜单项的子菜单项
            JSONArray children = root.getJSONArray("children");
            if (children.size() <= 0) {
                allMenuJson.remove(root);
            }
        }
        return allMenuJson;
    }

    /**
      * @Title: getResList
      * @Description: 
      * @author luozehua
      * @date 2016年11月9日 下午3:37:24
      * @param authResList
      */

    private List<SysResoucesVO> getResList(List<SysAuthoritiesResourceVO> authResList) {
        SysResoucesVO sysResVo = null;
        List<SysResoucesVO> resList = new ArrayList<SysResoucesVO>();
        for (SysAuthoritiesResourceVO sysAuthoritiesResourceVO : authResList) {
            sysResVo = new SysResoucesVO();
            sysResVo.setResourceId(sysAuthoritiesResourceVO.getResourceId());
            sysResVo.setType("admin");
            List<SysResoucesVO> temp = menuDao.findSysResource(sysResVo);
            resList.addAll(temp);
        }
        return resList;
    }

    /**
      * @Title: getResList
      * @Description: 
      * @author luozehua
      * @date 2016年11月9日 下午3:31:58
      * @param authList
      */

    private List<SysAuthoritiesResourceVO> getAuthResList(List<SysRoleAuthVO> authList) {
        List<SysAuthoritiesResourceVO> resList = new ArrayList<SysAuthoritiesResourceVO>();
        if (CollectionUtils.isNotEmpty(authList)) {
            SysAuthoritiesResourceVO sysAuthRes = null;
            //通过权限ID查询Resource_ID sys_authorities_resources
            for (SysRoleAuthVO sysRoleAuthVO : authList) {
                sysAuthRes = new SysAuthoritiesResourceVO();
                sysAuthRes.setAuthorityId(sysRoleAuthVO.getAuthorityId());
                List<SysAuthoritiesResourceVO> temp = userMuneAuthDao.findAuthResByAuth(sysAuthRes);
                resList.addAll(temp);
            }
        }
        return resList;
    }

    /**
      * @Title: getAuthListByRole
      * @Description: 
      * @author luozehua
      * @date 2016年11月9日 下午3:14:24
      * @param roleList
      */

    private List<SysRoleAuthVO> getAuthListByRole(List<SysRolesVO> roleList) {
        List<SysRoleAuthVO> authList = new ArrayList<SysRoleAuthVO>();
        if (CollectionUtils.isNotEmpty(roleList)) {
            SysRoleAuthVO sysRoleAuth = null;
            for (SysRolesVO sysRolesVO : roleList) {
                sysRoleAuth = new SysRoleAuthVO();
                sysRoleAuth.setRoleId(sysRolesVO.getRoleID());
                List<SysRoleAuthVO> tempList = userMuneAuthDao.findAuthByRole(sysRoleAuth);
                authList.addAll(tempList);
            }
        }
        return authList;
    }

    /**
      * @Title: getRoleListByUser
      * @Description: 
      * @author luozehua
      * @date 2016年11月9日 下午3:08:31
      * @param userInfo
      */

    public List<SysUserRoleVO> getRoleListByUser(SysUsersVO userInfo) {
        if (userInfo != null) {
            SysUserRoleVO sysUserRole = new SysUserRoleVO();
            sysUserRole.setUserId(userInfo.getUserId());
            return userMuneAuthDao.findRoleByUser(sysUserRole);
        }
        return null;
    }

    /**
     * 将数据库中的菜单数据组装为json -----fastjson转json对象的时候，当值为null的时候，不显示，但是可以get，不会报错
     */
    private JSONObject combainMenuJson(List<NodeMenuVO> tempMenuList, JSONObject jsonObject, int prentId) {
        for (NodeMenuVO nodeMenuVO : tempMenuList) {
            int id = nodeMenuVO.getmCode();
            int pid = nodeMenuVO.getmPid();
            if (pid == prentId) {
                JSONArray children = jsonObject.getJSONArray("children");
                if (children == null) {
                    children = new JSONArray();
                    jsonObject.put("children", children);
                }
                JSONObject json = (JSONObject) JSONObject.toJSON(nodeMenuVO);
                children.add(json);
                combainMenuJson(tempMenuList, json, id);
            }
        }
        return jsonObject;
    }

    @Override
    @CacheEvict(value = "menuCache", allEntries = true)
    public Integer addMenu(NodeMenuVO menu) {
        return menuDao.addMenu(menu);
    }

    @Override
    @CacheEvict(value = "menuCache", allEntries = true)
    public Integer updateMenu(NodeMenuVO menu) {
        return menuDao.updateMenu(menu);
    }

    @Override
    @CacheEvict(value = "menuCache", allEntries = true)
    public Integer deleteMenu(String id) {
        return menuDao.deleteMenu(id);
    }

    @Override
    public List<NodeMenuVO> findMenus(NodeMenuVO menu) {
        return menuDao.getMenuList(menu);
    }

    @Override
    public Integer getCount(NodeMenuVO menu) {
        return menuDao.getCount(menu);
    }

    @Override
    public List<SysAuthoritiesVO> findSysAuth(SysAuthoritiesVO sysAuth) {
        return menuDao.findSysAuth(sysAuth);
    }

    @Override
    public Integer addSysAuth(SysAuthoritiesVO sysAuth) {
        return menuDao.addSysAuth(sysAuth);
    }

    @Override
    public Integer addSysResource(SysResoucesVO sysResVo) {
        return menuDao.addSysResource(sysResVo);
    }

    @Override
    public Integer deleteSysResource(String id) {
        return menuDao.deleteSysResource(id);
    }

    @Override
    public Integer addSysAuthRes(SysAuthoritiesResourceVO rarVo) {
        return menuDao.addSysAuthRes(rarVo);
    }

    @Override
    public Integer deleteSysAuthRes(SysAuthoritiesResourceVO rarVo) {
        return menuDao.deleteSysAuthRes(rarVo);
    }

    @Override
    public List<SysResoucesVO> findSysResource(SysResoucesVO sysResVo) {
        return menuDao.findSysResource(sysResVo);
    }

}
