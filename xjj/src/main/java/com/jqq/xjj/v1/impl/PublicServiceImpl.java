package com.jqq.xjj.v1.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jqq.xjj.v1.service.PublicSerivce;
import com.laylib.common.model.MutableResult;
import com.laylib.common.model.Result;

@Path("/v1/public")
@Produces(MediaType.APPLICATION_JSON)
public class PublicServiceImpl implements PublicSerivce {

	@GET
	@Path("hello")
	public Result hello() {
		MutableResult mResult = new MutableResult();
		mResult.put("JQQ", "XJJ");
		return mResult.toResult();
	}

}
