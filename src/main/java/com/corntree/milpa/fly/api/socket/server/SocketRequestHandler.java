/*
 * Copyright 2009 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.corntree.milpa.fly.api.socket.server;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.corntree.milpa.fly.api.socket.RequestDispatcher;
import com.corntree.milpa.fly.protocol.request.Request.ClientRequest;
import com.corntree.milpa.fly.protocol.request.Request.LoginRequest;
import com.corntree.milpa.fly.protocol.request.Request.RegistRequest;
import com.corntree.milpa.fly.protocol.response.Response.BaseResponse;
import com.corntree.milpa.fly.protocol.response.Response.ResponseCode;

public class SocketRequestHandler extends SimpleChannelUpstreamHandler {

    private static final Logger logger = Logger.getLogger(SocketRequestHandler.class.getName());

    private RequestDispatcher dispatcher;

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            logger.info(e.toString());
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ClientRequest clientRequest = (ClientRequest) e.getMessage();
        logger.info(clientRequest.getClientRequestType());
        switch (clientRequest.getClientRequestType()) {
        case REGIST_REQUEST:
            RegistRequest registRequest = RegistRequest.parseFrom(clientRequest.getPacketData());
            logger.info(registRequest);
            break;
        case LOGIN_REQUEST:
            LoginRequest loginRequest = LoginRequest.parseFrom(clientRequest.getPacketData());
            logger.info(loginRequest);
            break;
        }
        e.getChannel().write(BaseResponse.newBuilder().setCode(ResponseCode.OK).build());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        logger.warn("Unexpected exception from downstream.", e.getCause());
        e.getChannel().close();
    }
}
