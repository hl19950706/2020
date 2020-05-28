package com.hl.shirotest.service;

import com.hl.shirotest.config.shiro.ShiroPrincipal;

public interface ShiroService {

	ShiroPrincipal getPrincipalByUsername(String username) throws Exception;
}
