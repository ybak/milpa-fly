package com.corntree.milpa.fly.api.socket.util;

import com.corntree.milpa.fly.protocol.response.Response.BaseResponse;
import com.corntree.milpa.fly.protocol.response.Response.ResponseCode;

public class Responses {
    public static final BaseResponse RESPONSE_OK = BaseResponse.newBuilder().setCode(ResponseCode.OK).build();
    public static final BaseResponse RESPONSE_BAD_PROTOCOL = BaseResponse.newBuilder()
            .setCode(ResponseCode.BAD_PROTOCOL).build();
    public static final BaseResponse RESPONSE_BAD_PARAMETER_EMAIL = BaseResponse.newBuilder()
            .setCode(ResponseCode.BAD_PARAMETER_EMAIL).build();
    public static final BaseResponse RESPONSE_BAD_PARAMETER_USERNAME_EXIST = BaseResponse.newBuilder()
            .setCode(ResponseCode.BAD_PARAMETER_USERNAME_EXIST).build();

}
