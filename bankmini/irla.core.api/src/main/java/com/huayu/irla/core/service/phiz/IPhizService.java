package com.huayu.irla.core.service.phiz;

import java.util.List;
import com.huayu.irla.core.phiz.vo.PhizVO;
/**
 * 表情包服务层接口
 * @author ggt
 *
 */
public interface IPhizService {
	/**
	 * 表情包查询
	 * @param phiz 表情实体类
	 * @return
	 */
	List<PhizVO> getPhizList(PhizVO phiz);
	/**
	 * 获取表情数量
	 * @param phiz 表情实体类
	 * @return
	 */
	Integer getPhizCount(PhizVO phiz);
	/**
	 * 表情新增
	 * @param phiz 表情实体类
	 */
	Integer addPhiz(PhizVO phiz);
	/**
	 * 表情修改
	 * @param phiz 表情实体类
	 */
	Integer updatePhiz(PhizVO phiz);
	/**
	 * 表情删除
	 * @param id 表情记录id
	 */
	Integer delPhiz(Integer id);
}
