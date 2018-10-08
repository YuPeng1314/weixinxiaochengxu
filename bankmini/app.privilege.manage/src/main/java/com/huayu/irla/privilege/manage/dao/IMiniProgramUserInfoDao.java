package com.huayu.irla.privilege.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huayu.core.mysql.DataSourceName;
import com.huayu.core.mysql.aspect.DataSource;
import com.huayu.irla.privilege.manage.vo.MiniProUserInfoVO;

public interface IMiniProgramUserInfoDao {

	@DataSource(DataSourceName.SLAVE_DATA_SOURCE)
	public List<MiniProUserInfoVO> getMiniProgramUserInfo(@Param("userinfo") MiniProUserInfoVO infoVO);

	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void updateMiniProgramUserInfo(@Param("userinfo") MiniProUserInfoVO infoVO);
	
	@DataSource(DataSourceName.MASTER_DATA_SOURCE)
	public void addMiniProgramUserInfo(@Param("userinfo") MiniProUserInfoVO infoVO);

	
}
