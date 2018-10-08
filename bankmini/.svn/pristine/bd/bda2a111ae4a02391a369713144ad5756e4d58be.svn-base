package com.huayu.irla.manage.dao.menu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.core.menu.vo.NodeMenuVO;
import com.huayu.irla.core.privilege.vo.SysAuthoritiesResourceVO;
import com.huayu.irla.core.privilege.vo.SysResoucesVO;
import com.huayu.irla.privilege.manage.vo.SysAuthoritiesVO;

/**
 * 菜单操作接口
 * 
 * @author luozehua
 *
 * @time 2016年8月24日-下午2:13:49
 */
public interface IMenuDao {
    /**
     * 得到菜单列表
     * 
     * @return
     */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
    List<NodeMenuVO> getMenuList(@Param("menu") NodeMenuVO menu);

    /**
      * @Title: getCount
      * @Description: 获取总条数
      * @author luozehua
      * @date 2016年11月7日 下午2:58:32
      * @return
     */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
    Integer getCount(@Param("menu") NodeMenuVO menu);

    /**
      * @Title: addMenu
      * @Description: 添加菜单栏目
      * @author luozehua
      * @date 2016年11月7日 上午11:37:48
      * @return
     */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
    Integer addMenu(NodeMenuVO menu);

    /**
      * @Title: updateMenu
      * @Description: 修改菜单栏目 
      * @author luozehua
      * @date 2016年11月7日 上午11:38:04
      * @return
     */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
    Integer updateMenu(NodeMenuVO menu);

    /**
      * @Title: deleteMenu
      * @Description: 删除菜单栏目
      * @author luozehua
      * @date 2016年11月7日 上午11:38:21
      * @return
     */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
    Integer deleteMenu(String id);

    //对权限表的操作
    /**
      * @Title: findSysAuth
      * @Description: 查询权限
      * @author luozehua
      * @date 2016年11月8日 上午11:35:37
      * @param sysAuth
      * @return
     */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
    List<SysAuthoritiesVO> findSysAuth(@Param("sysAuth") SysAuthoritiesVO sysAuth);

    /**
      * @Title: addSysAuth
      * @Description: 如果没有Mark 为4 的权限则添加一条
      * @author luozehua
      * @date 2016年11月8日 上午11:35:53
      * @param sysAuth
      * @return
     */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
    Integer addSysAuth(SysAuthoritiesVO sysAuth);

    /**
      * @Title: findSysResource
      * @Description: 查询系统资源配置（权限）
      * @author luozehua
      * @date 2016年11月8日 下午4:45:54
      * @param sysResVo
      * @return
     */
	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
    List<SysResoucesVO> findSysResource(@Param("sysRes") SysResoucesVO sysResVo);

    /**
      * @Title: addSysResource
      * @Description: 添加系统资源配置（权限）
      * @author luozehua
      * @date 2016年11月8日 下午3:24:22
      * @param sysResVo
      * @return
     */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
    Integer addSysResource(SysResoucesVO sysResVo);

    /**
      * @Title: deleteSysResource
      * @Description: 删除系统资源配置（权限）
      * @author luozehua
      * @date 2016年11月8日 下午3:24:26
      * @param id
      * @return
     */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
    Integer deleteSysResource(String id);

    /**
      * @Title: addSysAuthRes
      * @Description: 添加权限关系
      * @author luozehua
      * @date 2016年11月8日 下午3:24:29
      * @param rarVo
      * @return
     */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
    Integer addSysAuthRes(SysAuthoritiesResourceVO rarVo);

    /**
      * @Title: deleteSysAuthRes
      * @Description: 删除资源权限关系
      * @author luozehua
      * @date 2016年11月8日 下午3:24:33
      * @param id
      * @return
     */
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
    Integer deleteSysAuthRes(@Param("sar") SysAuthoritiesResourceVO rarVo);
}
