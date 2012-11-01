package com.corntree.milpa.fly.service.vo;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

public class Session {

    private String token;
    private Date loginTime;

    public Session(String username) {
        loginTime = new Date();
        token = DigestUtils.md5Hex(username + loginTime.getTime());
    }

    public String getToken() {
        return token;
    }

    public Date getLoginTime() {
        return loginTime;
    }

}
