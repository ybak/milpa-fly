package com.corntree.milpa.fly.api.socket.handler;

import org.jboss.netty.channel.Channel;

import com.corntree.milpa.fly.service.AccountService;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class LoginRequestHandler implements RequestHandler {

    private AccountService accountService = new AccountService();
    
    @Override
    public void handleRequestData(ByteString packetData, Channel channel) throws InvalidProtocolBufferException {

    }

}
