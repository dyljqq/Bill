package com.jqq.xjj.v1.extension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.logging.Logger;

import com.jqq.xjj.v1.dao.UserDao;
import com.jqq.xjj.v1.utility.ErrorCode;
import com.laylib.common.model.Result;
import com.laylib.common.support.ContentType;

@Provider
public class AuthInterceptor implements ContainerRequestFilter {
	
	Logger logger = Logger.getLogger(AuthInterceptor.class);
	
	@Context
	HttpServletRequest request;
	
	private List<String> noneAuthUrls = new ArrayList<String>() {{
		add("user/login");
		add("user/registe");
		add("user/forgetPassword");
		add("getItems");
		add("public");
	}};
	
	public void filter(ContainerRequestContext ctx) throws IOException {
		StringBuilder sbParams = null;
		String reqPath = ctx.getUriInfo().getPath();
		for(String key : request.getParameterMap().keySet()) {
			if (sbParams == null)
        		sbParams = new StringBuilder();
        	else
        		sbParams.append("&");
        	sbParams.append(key);
        	sbParams.append("=");
        	sbParams.append(request.getParameter(key));
		}
		if (sbParams == null) {
			sbParams = new StringBuilder();
		}
		logger.info(String.format("----收到请求:%s,请求方式:%s", reqPath, ctx.getMethod()));
        logger.info(String.format("----请求参数:%s", sbParams.toString()));
        
        if (!isExclude(reqPath)) {
			System.out.println("通用参数校验 ----");
			if (request.getParameter("sid") == null) {
				System.out.println("----");
//            	ctx.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
				showError(ctx, ErrorCode.ERROR_USER_NOT_EXIST, "用户不存在");
			} else{
				UserDao dao = new UserDao();
				if (!dao.exist(request.getParameter("sid"))) {
					showError(ctx, ErrorCode.ERROR_LOGIN, "请登陆");
				}
			}
		}
	}
	
	public Boolean isExclude(String authUrl) {
		boolean isExcluded = false;
		for(String url: noneAuthUrls) {
			if (authUrl.indexOf(url) > -1) {
				isExcluded = true;
				break;
			}
		}
		return isExcluded;	
	}
	
	private void showError(ContainerRequestContext crc, int errorCode, String msg) {
    	Result result = new Result(errorCode, null, msg);
		crc.abortWith(Response.status(Response.Status.OK).entity(result).type(ContentType.APPLICATION_JSON_UTF_8).build());
    }
	
}
