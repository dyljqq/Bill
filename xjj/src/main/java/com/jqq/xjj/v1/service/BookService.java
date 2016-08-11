package com.jqq.xjj.v1.service;

import com.laylib.common.model.Result;

public interface BookService {
	
	/**
	 * 添加
	 * @param name
	 * @param author
	 * @param price
	 * @param rate
	 * @param image
	 * @param desc
	 * @return
	 */
	public Result add(String name, String author, double price, double rate, String image, String desc, String sid);
	
	/**
	 * 查询
	 * @param sid
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Result query(String sid, int page, int pageSize);
	
}
