package com.corntree.milpa.fly.api.socket;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.corntree.milpa.fly.api.socket.handler.RequestHandler;
import com.corntree.milpa.fly.protocol.request.Request.ClientRequest;
import com.corntree.milpa.fly.protocol.request.Request.ClientRequestType;
import com.corntree.milpa.fly.protocol.response.Response.BaseResponse;
import com.corntree.milpa.fly.protocol.response.Response.ResponseCode;
import com.google.protobuf.InvalidProtocolBufferException;

public class RequestDispatcher {
    public static final Logger logger = Logger.getLogger(RequestDispatcher.class.getName());
    public Map<String, RequestHandler> handlerMapper = new HashMap<String, RequestHandler>();

    public void dispatchClientRequest(ClientRequest clientRequest, Channel channel) {
        try {
            ClientRequestType clientRequestType = clientRequest.getClientRequestType();
            logger.info(clientRequestType);
            String requestTypeName = clientRequestType.name();
            RequestHandler requestHandler = handlerMapper.get(requestTypeName);
            if (requestHandler == null) {
                String handlerName = getRequestHandlerName(requestTypeName);
                try {
                    requestHandler = (RequestHandler) Class.forName(handlerName).newInstance();
                    handlerMapper.put(requestTypeName, requestHandler);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            requestHandler.handleRequestData(clientRequest.getPacketData(), channel);
        } catch (InvalidProtocolBufferException e) {
            channel.write(BaseResponse.newBuilder().setCode(ResponseCode.ERROR_BAD_PROTOCOL).build());
        }
    }

    private static String getRequestHandlerName(String requestTypeName) {
        StringBuilder sb = new StringBuilder("com.corntree.milpa.fly.api.socket.handler.");
        String[] splitedName = requestTypeName.split("_");
        for (String fragment : splitedName) {
            sb.append(fragment.substring(0, 1).toUpperCase());
            sb.append(fragment.substring(1, fragment.length()).toLowerCase());
        }
        sb.append("Handler");
        return sb.toString();
    }

}
