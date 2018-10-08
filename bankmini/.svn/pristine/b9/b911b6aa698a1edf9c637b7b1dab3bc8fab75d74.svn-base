package com.huayu.irla.manage.application.privilege.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.stereotype.Component;

import com.huayu.irla.manage.application.privilege.IPrivilegeManage;
import com.huayu.irla.privilege.manage.common.PrivilegeControl;

/**
 * @author luozehua
 *
 * @time 2016年8月31日-下午5:03:39
 */
@Component("privilageManage")
public class PrivilegeManageImpl implements IPrivilegeManage {

	@Override
    public boolean isAuthention(String url) {
    	Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        return PrivilegeControl.isAuthorize(url, request);
    }
}
