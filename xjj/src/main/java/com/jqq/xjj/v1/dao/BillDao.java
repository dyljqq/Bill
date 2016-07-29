package com.jqq.xjj.v1.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.jqq.xjj.v1.model.Bill;
import com.jqq.xjj.v1.utility.BillStatus;
import com.jqq.xjj.v1.utility.JdbcTemplateDao;
import com.jqq.xjj.v1.utility.TimeUtility;
import com.laylib.common.jdbc.JdbcMapperFactory;
import com.laylib.common.utils.SqlUtil;

public class BillDao extends JdbcTemplateDao {
	
	public int insert(Bill bill) {
		String sql = "insert into bill_info(name, money, rate, begin_time, end_time, days, interest, note) values(?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getManageTemplate().update(JdbcMapperFactory.getPSCreator(sql, bill.getName(), bill.getMoney(), bill.getRate(), bill.getStartTime()
				, bill.getEndTime(), bill.getDays(), bill.getInterest(), bill.getNote()), keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public List<Map<String, Object>> find(int status, int pageIndex, int pageSize) {
		String whereClause = "";
		String currentTime = TimeUtility.convertTime(System.currentTimeMillis());
		switch (status) {
		case BillStatus.ALREADY_PAY:
			whereClause = "end_time <= ?";
			break;
			
		case BillStatus.WAITING_PAY:
			whereClause = "end_time > ?";
			break;

		default:
			break;
		}
		String sql = SqlUtil.findAll("bill_info", "*", whereClause, "", pageIndex, pageSize);
		return this.getManageTemplate().queryForList(sql, TimeUtility.timeToTimeStamp(currentTime));
	}
	
	public int count() {
		String sql = "select count(uid) from bill_info";
		return this.getManageTemplate().queryForObject(sql, Integer.class);
	}
	
}
