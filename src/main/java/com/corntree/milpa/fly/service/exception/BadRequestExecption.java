package com.corntree.milpa.fly.service.exception;

public class BadRequestExecption extends Exception {

    private static final long serialVersionUID = 8875217550541523288L;

    private String desc;

    public BadRequestExecption() {
        super();
    }

    public BadRequestExecption(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
