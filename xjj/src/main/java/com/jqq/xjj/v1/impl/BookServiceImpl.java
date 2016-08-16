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
import com.jqq.xjj.v1.utility.Utility;
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
					  @FormParam("type") int type,
					  @FormParam("uid") int uid) {
		BookDao dao = new BookDao();
		// 添加我的藏书
		if (type == 1) {
			Map<String, Object> map = Utility.getData(name);
			image = map.get("image").toString();
			String r = ((Map<String, Object>)map.get("rating")).get("average").toString();
			if (r != null) {
				rate = Float.parseFloat(r);
			} else {
				rate = 0.0;
			}
		}
		int bid = dao.insert(name, author, price, rate, image, desc, type, uid);
		return bid > 0 ? new Result(): new Result(ErrorCode.Error, "书籍添加失败");
	}
	
	@POST
	@Path("query")
	public Result query(@FormParam("uid") int uid,
						@FormParam("type") int type,
						@FormParam("page") int page, 
						@FormParam("pageSize") int pageSize) {
		BookDao dao = new BookDao();
		int start = (page - 1) * pageSize;
		int end = page * pageSize - 1;
		List<Map<String, Object>> list = dao.query(uid, type, start, end);
		return list != null ? new Result(list) : new Result(ErrorCode.Error, "查询失败");
	}

}
