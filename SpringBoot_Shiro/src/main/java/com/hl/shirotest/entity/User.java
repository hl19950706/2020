package com.hl.shirotest.entity;

import java.util.Date;

/**
 * @ClassName User
 * @Description TODO
 * @Author hanlei
 * @Date 2020/5/18 9:28 上午
 * @Version 1.0
 */
public class User {
    // 用户对象
    private Long id;

    private String name;

    private String pwd;

    private char status;

    private char listen;

    private char download;

    private char batchDownload;

    private Long privilegeId;

    private Date createTime;

    private Date updateTime;

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

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public char getListen() {
        return listen;
    }

    public void setListen(char listen) {
        this.listen = listen;
    }

    public char getDownload() {
        return download;
    }

    public void setDownload(char download) {
        this.download = download;
    }

    public char getBatchDownload() {
        return batchDownload;
    }

    public void setBatchDownload(char batchDownload) {
        this.batchDownload = batchDownload;
    }

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
