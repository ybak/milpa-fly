package com.corntree.milpa.fly.api.socket.handler;

import org.jboss.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corntree.milpa.fly.api.socket.util.Responses;
import com.corntree.milpa.fly.protocol.ClientPacket.LoginRequest;
import com.corntree.milpa.fly.protocol.Commons.PayloadType;
import com.corntree.milpa.fly.protocol.Commons.Token;
import com.corntree.milpa.fly.protocol.ServerPacket.ResponseCode;
import com.corntree.milpa.fly.protocol.ServerPacket.ServerResponse;
import com.corntree.milpa.fly.service.AccountService;
import com.corntree.milpa.fly.service.exception.BadRequestExecption;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

@Component("LOGIN_REQUEST")
public class LoginRequestHandler implements RequestHandler {

    @Autowired
    private AccountService accountService;

    @Override
    public void handleRequestData(ByteString packetData, Channel channel) throws InvalidProtocolBufferException {
        LoginRequest loginRequest = LoginRequest.parseFrom(packetData);
        try {
            String token = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());
            ServerResponse response = ServerResponse.newBuilder().setCode(ResponseCode.OK)
                    .setPayloadType(PayloadType.TOKEN)
                    .setPayloadData(Token.newBuilder().setToken(token).build().toByteString()).build();
            channel.write(response);
        } catch (BadRequestExecption e) {
            channel.write(Responses.RESPONSE_BAD_PARAMETER_LOGIN);
        }
    }

}
