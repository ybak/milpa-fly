package com.corntree.milpa.fly.api.socket.util;

import com.corntree.milpa.fly.protocol.ServerPacket.ResponseCode;
import com.corntree.milpa.fly.protocol.ServerPacket.ServerResponse;

public class Responses {
    public static final ServerResponse RESPONSE_OK = ServerResponse.newBuilder().setCode(ResponseCode.OK).build();
    public static final ServerResponse RESPONSE_BAD_PROTOCOL = ServerResponse.newBuilder()
            .setCode(ResponseCode.BAD_PROTOCOL).build();
    public static final ServerResponse RESPONSE_BAD_PARAMETER_EMAIL = ServerResponse.newBuilder()
            .setCode(ResponseCode.BAD_PARAMETER_EMAIL).build();
    public static final ServerResponse RESPONSE_BAD_PARAMETER_USERNAME_EXIST = ServerResponse.newBuilder()
            .setCode(ResponseCode.BAD_PARAMETER_USERNAME_EXIST).build();
    public static final ServerResponse RESPONSE_BAD_PARAMETER_LOGIN = ServerResponse.newBuilder()
            .setCode(ResponseCode.BAD_PARAMETER_LOGIN).build();
    public static final ServerResponse RESPONSE_BAD_PARAMETER_TOKEN = ServerResponse.newBuilder()
            .setCode(ResponseCode.BAD_PARAMETER_TOKEN).build();

}
