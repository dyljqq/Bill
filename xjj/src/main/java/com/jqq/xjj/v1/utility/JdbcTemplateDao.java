package com.jqq.xjj.v1.utility;

import com.laylib.common.jdbc.JdbcTemplateImpl;
import com.laylib.common.utils.SpringUtil;

public class JdbcTemplateDao {
	protected JdbcTemplateImpl _manageTemplate;
	
	public JdbcTemplateDao()
	{
		_manageTemplate = (JdbcTemplateImpl)SpringUtil.getBean("manageTemplate");
	}
	
	protected JdbcTemplateImpl getManageTemplate()
	{
		return _manageTemplate;
	}
}
