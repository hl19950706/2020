package com.hl.shirotest.service.impl;

import com.hl.shirotest.config.shiro.ShiroPrincipal;
import com.hl.shirotest.entity.User;
import com.hl.shirotest.service.ShiroService;
import com.hl.shirotest.util.ShiroUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName ShiroServiceImpl
 * @Description TODO
 * @Author hanlei
 * @Date 2020/5/18 9:40 上午
 * @Version 1.0
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Override
    public ShiroPrincipal getPrincipalByUsername(String username) throws Exception {
        User user = new User();
        user.setName(username);
        user.setId(1L);
        user.setCreateTime(new Date());
        user.setBatchDownload('1');
        user.setListen('1');
        user.setPrivilegeId(111L);
        user.setDownload('1');
        user.setPwd(ShiroUtils.MD5Pwd(username, "000000"));
        user.setStatus('1');
        user.setUpdateTime(new Date());
        //创建shiro的subject实体
        ShiroPrincipal shiroPrincipal = new ShiroPrincipal();
        BeanUtils.copyProperties(user, shiroPrincipal);
        if (user.getListen() == '1') {
            shiroPrincipal.setCanListen(true);
        } else {
            shiroPrincipal.setCanListen(false);
        }
        if (user.getBatchDownload() == '1') {
            shiroPrincipal.setCanBatchDownload(true);
        } else {
            shiroPrincipal.setCanBatchDownload(false);
        }
        if (user.getStatus() == '1') {
            shiroPrincipal.setCanUse(true);
        } else {
            shiroPrincipal.setCanUse(false);
        }
        if (user.getDownload() == '1') {
            shiroPrincipal.setCanDownload(true);
        } else {
            shiroPrincipal.setCanDownload(false);
        }
        shiroPrincipal.setUser(user);
        return shiroPrincipal;
    }
}
