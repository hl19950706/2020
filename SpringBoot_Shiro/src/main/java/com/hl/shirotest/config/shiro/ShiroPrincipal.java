package com.hl.shirotest.config.shiro;

import com.hl.shirotest.entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 自定义认证主体
 *
 * @author yuqs
 * @since 0.1
 */
public class ShiroPrincipal implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1428196040744555722L;

    private Long id;

    private String name;

    private String pwd;

    private Boolean canUse;

    private Boolean canListen;

    private Boolean canDownload;

    private Boolean canBatchDownload;

    //绑定企业
    private Long[] entIds[];
    private String[] entName[];

    //绑定权限名称
    private List<String> permissions;

    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(Boolean canUse) {
        this.canUse = canUse;
    }

    public Boolean getCanListen() {
        return canListen;
    }

    public void setCanListen(Boolean canListen) {
        this.canListen = canListen;
    }

    public Boolean getCanDownload() {
        return canDownload;
    }

    public void setCanDownload(Boolean canDownload) {
        this.canDownload = canDownload;
    }

    public Boolean getCanBatchDownload() {
        return canBatchDownload;
    }

    public void setCanBatchDownload(Boolean canBatchDownload) {
        this.canBatchDownload = canBatchDownload;
    }

    public Long[][] getEntIds() {
        return entIds;
    }

    public void setEntIds(Long[][] entIds) {
        this.entIds = entIds;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String[][] getEntName() {
        return entName;
    }

    public void setEntName(String[][] entName) {
        this.entName = entName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
