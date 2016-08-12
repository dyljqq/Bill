package com.jqq.xjj.v1.impl;

import java.util.Map;
import java.util.UUID;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;

import com.jqq.xjj.v1.dao.UserDao;
import com.jqq.xjj.v1.model.User;
import com.jqq.xjj.v1.service.UserService;
import com.jqq.xjj.v1.utility.ErrorCode;
import com.laylib.common.model.Result;

@Path("/v1/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserServiceImpl implements UserService {

	@POST
	@Path("register")
	public Result register(@FormParam("name") String name,
			@FormParam("password") String password) {
		// TODO Auto-generated method stub
		UserDao dao = new UserDao();
		if (dao.isExist(name)) {
			return new Result(ErrorCode.Error, "用户已存在");
		}
		int key = dao.insert(name, password);
		return key > 0 ? new Result() : new Result(ErrorCode.INSERT_ERROR, "用户注册失败");
	}

	@POST
	@Path("login")
	public Result login(@FormParam("name") String name,
						@FormParam("password") String password) {
		// TODO Auto-generated method stub
		UserDao dao =  new UserDao();
		UUID token = UUID.randomUUID();
		Map<String, Object> user = dao.query(name, password, token.toString());
		if(user == null || user.size() == 0) {
			return new Result(ErrorCode.Error, "用户不存在");
		} else {
			user.put("sid", token);
			return new Result(user);
		}
	}
	
	@POST
	@Path("forgetPassword")
	public Result forgetPassword(@FormParam("name") String name,
								 @FormParam("password") String password) {
		UserDao dao = new UserDao();
		return dao.alter(name, password) > 0 ? new Result() : new Result(ErrorCode.Error, "失败");
	}

	@POST
	@Path("logout")
	public Result logout(@FormParam("uid") int uid) {
		UserDao dao = new UserDao();
		return dao.update(uid) > 0 ? new Result() : new Result(ErrorCode.Error, "退出登陆失败");
	}

	@POST
	@Path("alterName")
	public Result alterName(@FormParam("uid") int uid,
							@FormParam("nickName") String nickName) {
		// TODO Auto-generated method stub
		UserDao dao = new UserDao();
		return dao.updateName(uid, nickName) > 0 ? new Result() : new Result(ErrorCode.Error, "用户名修改失败");
	}

	@POST
	@Path("alterPassword")
	public Result alterPassword(@FormParam("uid") int uid, 
								@FormParam("password") String password, 
								@FormParam("newPassword") String newPassword) {
		// TODO Auto-generated method stub
		UserDao dao = new UserDao();
		return dao.updatePassword(uid, password, newPassword) > 0 ? new Result() : new Result(ErrorCode.Error, "密码修改失败");
	}
	
}
