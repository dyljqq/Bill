package com.jqq.xjj.v1.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.jqq.xjj.v1.utility.JdbcTemplateDao;
import com.laylib.common.jdbc.JdbcMapperFactory;
import com.laylib.common.model.Result;

public class UserDao extends JdbcTemplateDao{
	
	public int insert(String name, String password) {
		String sql = "insert into user values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getManageTemplate().update(JdbcMapperFactory.getPSCreator(sql, name, password), keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public Map<String, Object> query(String name, String password) {
		String sql = "select * from user where name=? and password=?";
		try {
			return this.getManageTemplate().queryForMap(sql, name, password);
		} catch(EmptyResultDataAccessException e) {
			return new HashMap<>();
		}
	}
	
}
