package com.hl.shirotest.controller;

import com.hl.shirotest.config.shiro.ShiroPrincipal;
import com.hl.shirotest.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author hanlei
 * @Date 2020/5/18 9:54 上午
 * @Version 1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @ResponseBody
    @RequestMapping("/login")
    public String login(String name, String pwd) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
            subject.login(token);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "seccess";
    }

    @ResponseBody
    @RequestMapping("get")
    public String getmsg(){
        ShiroPrincipal shiroPrincipal = ShiroUtils.getPrincipal();
        return shiroPrincipal.getName();
    }
}
