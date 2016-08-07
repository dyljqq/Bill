package com.jqq.xjj.v1.impl;

import java.util.List;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jqq.xjj.v1.dao.CartDao;
import com.jqq.xjj.v1.service.CartService;
import com.jqq.xjj.v1.utility.ErrorCode;
import com.laylib.common.model.Result;

@Path("/v1/cart")
@Produces(MediaType.APPLICATION_JSON)
public class CartServiceImpl implements CartService {
	
	@POST
	@Path("getItems")
	public Result getItems() {
		// TODO Auto-generated method stub
		CartDao dao = new CartDao();
		List<Map<String, Object>> list = dao.query();
		return list != null ? new Result(list) : new Result(ErrorCode.Error, "商品获取失败");
	}

}
