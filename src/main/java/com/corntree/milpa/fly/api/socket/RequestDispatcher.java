package com.corntree.milpa.fly.api.socket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
import com.corntree.milpa.fly.service.SessionService;
import com.google.protobuf.InvalidProtocolBufferException;

@Component
public class RequestDispatcher {
    public static final Logger logger = Logger.getLogger(RequestDispatcher.class.getName());

    private static final Set<ClientRequestType> TOKEN_FREE_REQUEST_TYPES = new HashSet<ClientRequestType>(
            Arrays.asList(ClientRequestType.LOGIN_REQUEST, ClientRequestType.REGIST_REQUEST));

    @Autowired
    private SessionService sessionService;
    @Autowired
    private ApplicationContext applicationContext;

    public void dispatchClientRequest(final ClientRequest clientRequest, final Channel channel) {
        logger.info(clientRequest);
        ClientRequestType clientRequestType = clientRequest.getClientRequestType();

        boolean isTokenValidate = validateToken(clientRequest, clientRequestType);
        if (isTokenValidate) {
            try {
                RequestHandler requestHandler = getRequestHandler(clientRequestType);
                requestHandler.handleRequestData(clientRequest.getRequestData(), channel);
            } catch (InvalidProtocolBufferException e) {
                channel.write(Responses.RESPONSE_BAD_PROTOCOL);
            } catch (Exception e) {
                logger.error("internal error", e);
                channel.write(ResponseFactory.buildServerResponse(ResponseCode.ERROR_UNKOWN));
            }
        } else {
            logger.warn("passed bad token");
            channel.write(Responses.RESPONSE_BAD_PARAMETER_TOKEN);
        }
    }

    private RequestHandler getRequestHandler(ClientRequestType clientRequestType) {
        String requestTypeName = clientRequestType.name();
        return applicationContext.getBean(requestTypeName, RequestHandler.class);
    }

    private boolean validateToken(ClientRequest clientRequest, ClientRequestType clientRequestType) {
        boolean isTokenValidate = true;
        if (!TOKEN_FREE_REQUEST_TYPES.contains(clientRequestType)) {
            if (clientRequest.hasToken()) {
                isTokenValidate = sessionService.isTokenValidate(clientRequest.getToken());
            } else {
                isTokenValidate = false;
            }
        }
        return isTokenValidate;
    }
}
