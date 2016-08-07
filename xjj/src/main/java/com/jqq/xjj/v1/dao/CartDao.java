package com.jqq.xjj.v1.dao;

import java.util.List;
import java.util.Map;

import com.jqq.xjj.v1.utility.JdbcTemplateDao;

public class CartDao extends JdbcTemplateDao {
	
	public List<Map<String, Object>> query() {
		String sql = "select * from cart";
		return this.getManageTemplate().queryForList(sql);
	}
	
}
