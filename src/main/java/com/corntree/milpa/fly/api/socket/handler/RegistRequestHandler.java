package com.corntree.milpa.fly.api.socket.handler;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.corntree.milpa.fly.api.socket.util.ResponseUtil;
import com.corntree.milpa.fly.protocol.request.Request.RegistRequest;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class RegistRequestHandler implements RequestHandler {
    public static final Logger logger = Logger.getLogger(RegistRequestHandler.class.getName());

    @Override
    public void handleRequestData(ByteString packetData, Channel channel) throws InvalidProtocolBufferException {
        RegistRequest registRequest = RegistRequest.parseFrom(packetData);
        logger.info(registRequest);
        channel.write(ResponseUtil.RESPONSE_OK);
    }

}
