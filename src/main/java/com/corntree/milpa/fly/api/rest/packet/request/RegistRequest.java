package com.corntree.milpa.fly.api.rest.packet.request;

import com.corntree.milpa.fly.api.rest.packet.gson.GsonFactory;

public class RegistRequest {
    private String username;
    private String password;
    private String email;

    public RegistRequest() {
        super();
    }

    public RegistRequest(String username, String password, String email) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toJson() {
        return GsonFactory.getThreadLocalGson().toJson(this);
    }

    public static RegistRequest fromJson(String json) {
        return GsonFactory.getThreadLocalGson().fromJson(json, RegistRequest.class);
    }

}
