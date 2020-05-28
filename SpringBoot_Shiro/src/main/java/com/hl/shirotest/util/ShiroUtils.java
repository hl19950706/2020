package com.hl.shirotest.util;

import com.hl.shirotest.config.shiro.ShiroPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

/**
 * shiro工具类
 * 
 * @author yuqs
 * @since 0.1
 */
public class ShiroUtils {

	/**
	 * 获取当前登录的认证实体
	 * 
	 * @return
	 */
	public static ShiroPrincipal getPrincipal() {
		Subject subject = SecurityUtils.getSubject();
		return (ShiroPrincipal) subject.getPrincipal();
	}

	public static String MD5Pwd(String username, String pwd) {
		// 加密算法MD5
		// salt盐 username + salt
		// 迭代次数
		String md5Pwd = new SimpleHash("MD5", pwd,
				ByteSource.Util.bytes(username + "salt"), 2).toHex();
		return md5Pwd;
	}

}
