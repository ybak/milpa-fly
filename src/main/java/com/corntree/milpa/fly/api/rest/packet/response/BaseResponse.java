package com.corntree.milpa.fly.api.rest.packet.response;

import com.corntree.milpa.fly.api.rest.packet.gson.GsonFactory;

public class BaseResponse {
    private String code;
    private String desc;

    public BaseResponse() {
        super();
    }

    public BaseResponse(String code, String desc) {
        super();
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toJson() {
        return GsonFactory.getThreadLocalGson().toJson(this);
    }

    public static BaseResponse fromJson(String json) {
        return GsonFactory.getThreadLocalGson().fromJson(json, BaseResponse.class);
    }

}
