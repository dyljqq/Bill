package com.jqq.xjj.v1.impl;

import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jqq.xjj.v1.dao.UserDao;
import com.jqq.xjj.v1.service.UserService;
import com.jqq.xjj.v1.utility.ErrorCode;
import com.laylib.common.model.Result;

@Path("/v1/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserServiceImpl implements UserService {

	@POST
	@Path("register")
	public Result addUser(@FormParam("name") String name, 
						 @FormParam("password") String password) {
		// TODO Auto-generated method stub	
		UserDao dao = new UserDao();
		int key = dao.insert(name, password);
		return key > 0 ? new Result() : new Result(ErrorCode.INSERT_ERROR, "用户注册失败");
	}

	@POST
	@Path("login")
	public Result login(@FormParam("name") String name,
						@FormParam("password") String password) {
		// TODO Auto-generated method stub
		UserDao dao =  new UserDao();
		Map<String, Object> user = dao.query(name, password);
		return (user == null || user.size() == 0) ? new Result(ErrorCode.Error, "用户不存在") : new Result(user);
	}
	
}
