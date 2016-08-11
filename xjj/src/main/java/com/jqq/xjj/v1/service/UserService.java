package com.jqq.xjj.v1.service;

import javax.ws.rs.FormParam;

import com.laylib.common.model.Result;

public interface UserService {
	
	// 注册
	public Result register(String name, String password);
	
	// 登陆
	public Result login(String name, String password);
	
	// 忘记密码
	public Result forgetPassword(String name, String password);
	
	// 退出登陆
	public Result logout(int uid);
	
}
