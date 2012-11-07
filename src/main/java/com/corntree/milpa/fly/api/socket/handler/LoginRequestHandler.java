package com.corntree.milpa.fly.api.socket.handler;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corntree.milpa.fly.api.socket.util.ResponseFactory;
import com.corntree.milpa.fly.protocol.ClientPacket.LoginRequest;
import com.corntree.milpa.fly.protocol.Commons.PayloadType;
import com.corntree.milpa.fly.protocol.Commons.Token;
import com.corntree.milpa.fly.protocol.ServerPacket.ResponseCode;
import com.corntree.milpa.fly.protocol.ServerPacket.ServerResponse;
import com.corntree.milpa.fly.service.AccountService;
import com.corntree.milpa.fly.service.exception.LoginException;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

@Component("LOGIN_REQUEST")
public class LoginRequestHandler implements RequestHandler {

    private static final Logger logger = Logger.getLogger(LoginRequestHandler.class.getName());

    @Autowired
    private AccountService accountService;

    @Override
    public void handleRequestData(ByteString packetData, Channel channel) throws InvalidProtocolBufferException {
        LoginRequest loginRequest = LoginRequest.parseFrom(packetData);
        try {
            String token = null;
            if (loginRequest.hasPlayerId()) {
                token = accountService.login(loginRequest.getUsername(), loginRequest.getPassword(),
                        loginRequest.getPlayerId());
            } else {
                token = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());
            }

            logger.info("user " + loginRequest.getUsername() + " login ok.");

            ByteString tokenByteString = Token.newBuilder().setToken(token).build().toByteString();

            ServerResponse response = ResponseFactory.buildServerResponse(PayloadType.TOKEN,
                    tokenByteString);
            channel.write(response);
        } catch (LoginException e) {
            logger.warn("user " + loginRequest.getUsername() + " login failed.");
            channel.write(ResponseFactory.buildServerResponse(ResponseCode.BAD_PARAMETER_LOGIN, e.getDesc()));
        }
    }

}
