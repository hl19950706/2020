package com.hl.shirotest.config.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

public class MyShiroHttpServletResponse extends ShiroHttpServletResponse {

	public MyShiroHttpServletResponse(HttpServletResponse wrapped, ServletContext context,
                                      ShiroHttpServletRequest request) {
		super(wrapped, context, request);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String toEncoded(String url, String sessionId) {
		// TODO Auto-generated method stub
		if((url == null) || (sessionId == null)){
			return  (url);
		}
		String path = url;
		String query = "";
		String anchor = "";
		int question = url.indexOf('?');
		if(question >= 0){
			path = url.substring(0,question);
			query = url.substring(question);
		}
		int pound = path.indexOf('#');
		if(pound >= 0){
			anchor = path.substring(pound);
			path = path.substring(0,pound);
		}
		StringBuilder sb = new StringBuilder(path);
		sb.append(anchor);
		sb.append(query);
		return (sb.toString());
	}
	

}
