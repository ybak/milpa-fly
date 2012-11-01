package com.corntree.milpa.fly.api.socket.handler;

import org.jboss.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corntree.milpa.fly.service.AccountService;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

@Component("LOGIN_REQUEST")
public class LoginRequestHandler implements RequestHandler {

    @Autowired
    private AccountService accountService;
    
    @Override
    public void handleRequestData(ByteString packetData, Channel channel) throws InvalidProtocolBufferException {

    }

}
