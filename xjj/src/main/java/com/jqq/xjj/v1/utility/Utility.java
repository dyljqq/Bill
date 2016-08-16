package com.jqq.xjj.v1.utility;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Utility {
	
	public static Map<String, Object> getData(String keyword) {
		try {
			String encode = URLEncoder.encode(keyword, "UTF-8");
			String url = "https://api.douban.com/v2/book/search?count=10&q=" + encode;
			String result = HttpUtil.get(url);
			JSONObject map = JSONObject.parseObject(result);
			JSONArray books = (JSONArray)map.get("books");
			if(books != null) {
				return (Map<String, Object>) books.get(0);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
