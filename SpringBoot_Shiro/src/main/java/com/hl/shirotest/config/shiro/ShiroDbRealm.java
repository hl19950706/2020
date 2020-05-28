package com.hl.shirotest.config.shiro;

import com.hl.shirotest.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {

    private static Log log = LogFactory.getLog(ShiroDbRealm.class);

    @Autowired
    private ShiroService shiroService;

    public ShiroDbRealm() {
        super();
        setAuthenticationTokenClass(UsernamePasswordToken.class);
    }


    /*
     * shiro会将token传递给自定义realm
     * 此时realm会先调用doGetAuthenticationInfo(AuthenticationToken authcToken
     * )登录验证的方法， 验证通过后会接着调用 doGetAuthorizationInfo(PrincipalCollection
     * principals)获取角色和权限的方法（授权）
     */

    /**
     * 认证回调函数,登录时调用. AuthenticationInfo 中principal会设置到Session中并通过SessionDao保存起来
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();

        // 通过token，连接数据库或单点服务器认证登陆
        if (StringUtils.isEmpty(username)) {
            log.warn("用户名不能为空");
            return null;
        }

        try {
            //根据用户名称将用户信息填入ShiroPrincipal
            ShiroPrincipal principal = shiroService
                    .getPrincipalByUsername(username);
            // 创建principal身份
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                    principal, principal.getPwd(),
                    ByteSource.Util.bytes(principal.getName() + "salt"), getName());
            log.info("用户【" + username + "】获取信息成功");
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用. 只会被缓存，不会被设置到Session中
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> list = new ArrayList<>();
        list.add("/login/get");
        info.addStringPermissions(list);
        return info;
    }
}
