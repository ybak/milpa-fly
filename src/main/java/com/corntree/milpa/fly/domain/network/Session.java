package com.corntree.milpa.fly.domain.network;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.corntree.milpa.fly.domain.Account;
import com.corntree.milpa.fly.domain.Player;

public class Session {

    private String token;
    private Date loginTime;
    private Account account;
    private Player player;
    private Notifier notifier; 

    public Session(Account account, Player player) {
        this.account = account;
        this.player = player;
        this.loginTime = new Date();
        this.token = DigestUtils.md5Hex(account.getUsername() + loginTime.getTime());
    }

    public String getToken() {
        return token;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public Account getAccount() {
        return account;
    }

    public Player getPlayer() {
        return player;
    }
    
    public void notify(Object message){
        notifier.notify(message);
    }

}
