package com.corntree.milpa.fly.api.socket.handler;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corntree.milpa.fly.api.socket.util.Responses;
import com.corntree.milpa.fly.protocol.request.Request.RegistRequest;
import com.corntree.milpa.fly.service.AccountService;
import com.corntree.milpa.fly.service.exception.BadRequestExecption;
import com.corntree.milpa.fly.service.exception.IllegalEmailException;
import com.corntree.milpa.fly.service.exception.UsernameExistException;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

@Component("REGIST_REQUEST")
public class RegistRequestHandler implements RequestHandler {
    public static final Logger logger = Logger.getLogger(RegistRequestHandler.class.getName());

    @Autowired
    private AccountService accountService;

    @Override
    public void handleRequestData(ByteString packetData, Channel channel) throws InvalidProtocolBufferException {
        RegistRequest registRequest = RegistRequest.parseFrom(packetData);
        logger.info(registRequest);
            channel.write(Responses.RESPONSE_OK);
//        try {
//            accountService.registAccount(registRequest.getUsername(), registRequest.getPassword(),
//                    registRequest.getEmail());
//            channel.write(Responses.RESPONSE_OK);
//        } catch (BadRequestExecption e) {
//            if (e instanceof IllegalEmailException) {
//                channel.write(Responses.RESPONSE_BAD_PARAMETER_EMAIL);
//            } else if (e instanceof UsernameExistException) {
//                channel.write(Responses.RESPONSE_BAD_PARAMETER_USERNAME_EXIST);
//            }
//        }
    }

}
