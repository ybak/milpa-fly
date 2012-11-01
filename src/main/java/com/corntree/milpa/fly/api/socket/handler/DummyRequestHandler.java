package com.corntree.milpa.fly.api.socket.handler;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.springframework.stereotype.Component;

import com.corntree.milpa.fly.api.socket.util.Responses;
import com.corntree.milpa.fly.protocol.ClientPacket.DummyRequest;
import com.corntree.milpa.fly.protocol.ClientPacket.RegistRequest;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

@Component("DUMMY_REQUEST")
public class DummyRequestHandler implements RequestHandler {
    public static final Logger logger = Logger.getLogger(DummyRequestHandler.class.getName());

    @Override
    public void handleRequestData(ByteString packetData, Channel channel) throws InvalidProtocolBufferException {
        DummyRequest dummyRequest = DummyRequest.parseFrom(packetData);
        
        ByteString byteString = DummyRequest.newBuilder().setUsername("username").setPassword("password").setEmail("email").build().toByteString();
        
        logger.info(dummyRequest);
        channel.write(Responses.RESPONSE_OK);
    }
    
    public static void main(String[] args) throws InvalidProtocolBufferException {
//        ByteString byteString = DummyRequest.newBuilder().setUsername("username").setPassword("password").setEmail("email").build().toByteString();
//        DummyRequest dummyRequest = DummyRequest.parseFrom(byteString);
//        logger.info(dummyRequest);
//        
        ByteString byteString = RegistRequest.newBuilder().setUsername("username").setPassword("password").setEmail("email").build().toByteString();
        RegistRequest registRequest = RegistRequest.parseFrom(byteString);
        logger.info(registRequest);
        
    }

}
