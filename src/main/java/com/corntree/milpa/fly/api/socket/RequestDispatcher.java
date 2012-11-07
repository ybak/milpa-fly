package com.corntree.milpa.fly.api.socket;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.corntree.milpa.fly.api.socket.handler.RequestHandler;
import com.corntree.milpa.fly.api.socket.util.ResponseFactory;
import com.corntree.milpa.fly.api.socket.util.Responses;
import com.corntree.milpa.fly.protocol.ClientPacket.ClientRequest;
import com.corntree.milpa.fly.protocol.ClientPacket.ClientRequestType;
import com.corntree.milpa.fly.protocol.ServerPacket.ResponseCode;
import com.google.protobuf.InvalidProtocolBufferException;

@Component
public class RequestDispatcher {
    public static final Logger logger = Logger.getLogger(RequestDispatcher.class.getName());

    @Autowired
    private ApplicationContext applicationContext;

    public void dispatchClientRequest(final ClientRequest clientRequest, final Channel channel) {
        ClientRequestType clientRequestType = clientRequest.getClientRequestType();
        logger.info(clientRequest);
        String requestTypeName = clientRequestType.name();
        RequestHandler requestHandler = applicationContext.getBean(requestTypeName, RequestHandler.class);
        try {
            requestHandler.handleRequestData(clientRequest.getRequestData(), channel);
        } catch (InvalidProtocolBufferException e) {
            channel.write(Responses.RESPONSE_BAD_PROTOCOL);
        } catch (Exception e) {
            logger.error("internal error", e);
            channel.write(ResponseFactory.buildServerResponse(ResponseCode.ERROR_UNKOWN));
        }
    }
}
