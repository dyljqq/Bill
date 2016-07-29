package com.jqq.xjj.v1.service;

import com.laylib.common.model.Result;

public interface BillService {
	//添加数据
	public Result addBill( String name, double money, double rate, String startTime, int days, String note);
	
	//查询数据
	public Result query(int status, int pageIndex);
}
