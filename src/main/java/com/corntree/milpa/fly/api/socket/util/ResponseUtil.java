package com.corntree.milpa.fly.api.socket.util;

import com.corntree.milpa.fly.protocol.response.Response.BaseResponse;
import com.corntree.milpa.fly.protocol.response.Response.ResponseCode;

public class ResponseUtil {
    public static final BaseResponse RESPONSE_OK = BaseResponse.newBuilder().setCode(ResponseCode.OK).build();

}
