package com.jqq.xjj.v1.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.jqq.xjj.v1.utility.JdbcTemplateDao;
import com.laylib.common.jdbc.JdbcMapperFactory;
import com.laylib.common.jdbc.JdbcTemplateImpl;
import com.laylib.common.model.Result;

public class UserDao extends JdbcTemplateDao{
	
	public int insert(String name, String password) {
		String sql = "insert into user(name, password) values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getManageTemplate().update(JdbcMapperFactory.getPSCreator(sql, name, password), keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public Map<String, Object> query(String name, String password, String token) {
		String sql1 = "select * from user where name=? and password=?";
		String sql2 = "update user set sid = ? where name = ?";
		JdbcTemplateImpl jdbcT = this.getManageTemplate();
		jdbcT.beginTranstaion();
		try {
			Map<String, Object> map = jdbcT.queryForMap(sql1, name, password);
			if(map != null && jdbcT.update(sql2, token, name) > 0) {
				jdbcT.commit();
				return map;
			}
			jdbcT.rollback();
			return new HashMap<String, Object>();
		} catch(EmptyResultDataAccessException e) {
			jdbcT.rollback();
			return new HashMap<>();
		}
	}
	
	public int alter(String name, String password) {
		String sql = "update user set password = ? where name = ?";
		return this.getManageTemplate().update(sql, password, name);
	}
	
	public int update(int uid) {
		String sql = "update user set sid = '' where uid = ?";
		return this.getManageTemplate().update(sql, uid);
	}
	
	public boolean exist(String sid) {
		String sql = "select count(sid) from user where sid = ?";
		return this.getManageTemplate().queryForObject(sql, new Object[]{sid}, Integer.class) > 0;
	}
	
}
