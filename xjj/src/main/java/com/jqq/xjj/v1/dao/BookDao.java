package com.jqq.xjj.v1.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.jqq.xjj.v1.utility.JdbcTemplateDao;
import com.laylib.common.jdbc.JdbcMapperFactory;

public class BookDao extends JdbcTemplateDao {
	
	public int insert(String name, String author, double price, double rate, String image, String desc, String sid) {
		String sql = "insert into book(name, author, price, rate, image, des, sid) values(?, ?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getManageTemplate().update(JdbcMapperFactory.getPSCreator(sql, name, author, price, rate, image, desc, sid), keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public List<Map<String, Object>> query(int uid, int start, int end) {
		String sql = "select * from book where uid=? limit ?, ?";
		return this.getManageTemplate().queryForList(sql, uid, start, end);
	}
	
}
