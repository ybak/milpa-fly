package com.corntree.milpa.fly.api.socket;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.corntree.milpa.fly.api.socket.handler.RegistRequestHandler;
import com.corntree.milpa.fly.protocol.request.Request.ClientRequest;
import com.corntree.milpa.fly.protocol.request.Request.ClientRequestType;
import com.corntree.milpa.fly.protocol.request.Request.LoginRequest;
import com.corntree.milpa.fly.protocol.response.Response.BaseResponse;
import com.corntree.milpa.fly.protocol.response.Response.ResponseCode;
import com.google.protobuf.InvalidProtocolBufferException;

public class RequestDispatcher {
    public static final Logger logger = Logger.getLogger(RequestDispatcher.class.getName());
    private RegistRequestHandler registRequestHandler = new RegistRequestHandler();

    public void dispatchClientRequest(ClientRequest clientRequest, Channel channel) {
        try {
            ClientRequestType clientRequestType = clientRequest.getClientRequestType();
            logger.info(clientRequestType);
            switch (clientRequestType) {
            case REGIST_REQUEST:
                registRequestHandler.handleRequestData(clientRequest.getPacketData(), channel);
                break;
            case LOGIN_REQUEST:
                LoginRequest loginRequest = LoginRequest.parseFrom(clientRequest.getPacketData());
                logger.info(loginRequest);
                break;
            }
        } catch (InvalidProtocolBufferException e) {
            channel.write(BaseResponse.newBuilder().setCode(ResponseCode.ERROR_BAD_PROTOCOL).build());
        }
    }

}
