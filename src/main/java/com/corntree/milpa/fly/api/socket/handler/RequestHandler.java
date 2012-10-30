package com.corntree.milpa.fly.api.socket.handler;

import org.jboss.netty.channel.Channel;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public interface RequestHandler {

    public abstract void handleRequestData(ByteString packetData, Channel channel)
            throws InvalidProtocolBufferException;

}