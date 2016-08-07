package com.jqq.xjj.v1.service;

import com.laylib.common.model.Result;

public interface UserService {
	
	// 注册
	public Result addUser(String name, String password);
	
	// 登陆
	public Result login(String name, String password);
	
}
