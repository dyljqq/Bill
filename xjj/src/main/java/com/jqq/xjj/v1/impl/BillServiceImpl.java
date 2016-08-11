package com.jqq.xjj.v1.impl;

import com.laylib.common.model.MutableResult;
import com.laylib.common.model.Result;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jqq.xjj.v1.dao.BillDao;
import com.jqq.xjj.v1.model.Bill;
import com.jqq.xjj.v1.service.BillService;
import com.jqq.xjj.v1.utility.ErrorCode;
import com.jqq.xjj.v1.utility.TimeUtility;

@Path("/v1/bill")
@Produces(MediaType.APPLICATION_JSON)
public class BillServiceImpl implements BillService {

	@POST
	@Path("add")
	public Result addBill(@FormParam("name") String name,
						@FormParam("money") double money,
						@FormParam("rate") double rate,
						@FormParam("startTime") String startTime,
						@FormParam("days") int days,
						@FormParam("note") String note) {
		Bill bill = new Bill();
		bill.setName(name);
		bill.setDays(days);
		bill.setMoney(money);
		bill.setNote(note);
		bill.setStartTime(startTime);
		bill.setRate(rate);
		
		double interest = money * rate * days / 365;
		bill.setInterest(interest);
		bill.setEndTime(TimeUtility.getTimeStamp(startTime, days));
		
		BillDao dao = new BillDao();
		int key = dao.insert(bill);
		
		return key > 0 ? new Result() : new Result(ErrorCode.Error, "添加失败");
	}
	
	@POST
	@Path("query")
	public Result query(@FormParam("status") int status,
						@FormParam("pageIndex") int pageIndex) {
		BillDao dao = new BillDao();
		MutableResult result = new MutableResult();
		result.put("pageIndex", pageIndex);
		result.put("total", dao.count());
		result.put("list", dao.find(status, pageIndex, 20));
		return result.toResult();
	}

	@POST
	@Path("delete")
	public Result delete(@FormParam("uid") int uid) {
		// TODO Auto-generated method stub
		System.out.println("uid" + uid);
		BillDao dao = new BillDao();
		return dao.delete(uid) ? new Result() : new Result(ErrorCode.ERROR_DELETE, "删除失败");
	}
	
}
