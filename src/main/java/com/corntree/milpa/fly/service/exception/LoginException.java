package com.corntree.milpa.fly.service.exception;

public class LoginException extends BadRequestExecption{

    private static final long serialVersionUID = -2962543931680402031L;

    public LoginException() {
        super();
    }

    public LoginException(String desc) {
        super(desc);
    }

}
