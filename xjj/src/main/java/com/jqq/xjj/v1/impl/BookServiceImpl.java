package com.jqq.xjj.v1.impl;

import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jqq.xjj.v1.dao.BookDao;
import com.jqq.xjj.v1.service.BookService;
import com.jqq.xjj.v1.utility.ErrorCode;
import com.laylib.common.model.Result;

import io.netty.util.internal.StringUtil;

@Path("v1/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookServiceImpl implements BookService {

	@POST
	@Path("add")
	public Result add(@FormParam("name") String name, 
					  @FormParam("author") String author,
					  @FormParam("price") double price, 
					  @FormParam("rate") double rate, 
					  @FormParam("image") String image, 
					  @FormParam("desc") String desc,
					  @FormParam("sid") String sid) {
		if (StringUtil.isNullOrEmpty(sid)) {
			return new Result(ErrorCode.ERROR_LOGIN, "请先登陆");
		}
		BookDao dao = new BookDao();
		int bid = dao.insert(name, author, price, rate, image, desc, sid);
		return bid > 0 ? new Result(): new Result(ErrorCode.Error, "书籍添加失败");
	}
	
	@POST
	@Path("query")
	public Result query(@FormParam("sid") String sid,
						@FormParam("page") int page, 
						@FormParam("pageSize") int pageSize) {
		BookDao dao = new BookDao();
		List<Map<String, Object>> list = dao.query(sid, page, pageSize);
		return list != null ? new Result(list) : new Result(ErrorCode.Error, "查询失败");
	}

}
