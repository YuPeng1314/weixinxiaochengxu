package com.huayu.irla.mobile.share;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huayu.irla.core.base.ResultVO;
import com.huayu.irla.core.service.share.IShareService;
import com.huayu.irla.core.share.vo.ShareVO;
import com.huayu.irla.mobile.comment.CommentAction;

/**
 * 
  * @ClassName: ShareAction
  * @Description: 小程序资源分享转发统计接口
  * @author GuGuangting
  * @date 2018年8月28日 下午5:00:35
  * Copyright: Copyright (c) 2018
  * Company:华煜网络教育有限公司
 */

@Controller
@RequestMapping("/shareAction")
public class ShareAction {
	private Logger logger = Logger.getLogger(CommentAction.class);
	@Autowired
	private IShareService shareService;
	@ResponseBody
	@RequestMapping(value = "/shareStatistics", method = RequestMethod.GET)
	public ResultVO shareStatistics(HttpServletRequest request, HttpServletResponse response) {
		ResultVO result = new ResultVO();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			String resourceCode = request.getParameter("resourseCode"); //资源编码
			String lastUpdatedBy = request.getParameter("userCode");//用户编码
			String courseCode = request.getParameter("courseCode");//课程编码
			//评论
			ShareVO share = new ShareVO();
			share.setResourceCode(resourceCode);
			Integer num = shareService.getShareCount(share);
			if(num==0) {
				share.setCourseCode(courseCode);
				share.setLastUpdatedBy(lastUpdatedBy);
				shareService.addShare(share);
				result.setSubMessage("新增分享数量成功");
			}else {
				shareService.updateShare(share);
				result.setSubMessage("修改分享数量成功");
			}
		} catch (Exception e) {
			result.setNetCode(500);
			logger.error(e);
		}
		return result;
	}

}
