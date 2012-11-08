package com.corntree.milpa.fly.api.socket.handler;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.springframework.stereotype.Component;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

@Component("GET_NOTIFICATION_REQUEST")
public class GetNotificationRequestHandler implements RequestHandler{

    private static final Logger logger = Logger.getLogger(GetNotificationRequestHandler.class.getName());
    
    @Override
    public void handleRequestData(ByteString packetData, Channel channel) throws InvalidProtocolBufferException {
            logger.warn("you get here.");
    }

}
