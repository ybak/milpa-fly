package com.corntree.milpa.fly.api.socket.handler;

import org.jboss.netty.channel.Channel;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class LoginRequestHandler implements RequestHandler {

    @Override
    public void handleRequestData(ByteString packetData, Channel channel) throws InvalidProtocolBufferException {
        // TODO Auto-generated method stub

    }

}
