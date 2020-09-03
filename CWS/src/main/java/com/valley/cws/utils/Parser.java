package com.valley.cws.utils;

import org.codehaus.jackson.map.ObjectMapper;

import com.valley.cws.bean.PolicyResBean;

public class Parser {

	public static Object JsonToObject(String json) {
		ObjectMapper mapper;
		PolicyResBean resBean = null;

		try {
			mapper = new ObjectMapper();
			resBean = mapper.readValue(json.getBytes(), PolicyResBean.class);
		} catch (Exception e) {
		}
		return resBean;
	}
}
