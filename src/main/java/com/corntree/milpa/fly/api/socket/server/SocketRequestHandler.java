package com.corntree.milpa.fly.api.socket.server;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corntree.milpa.fly.api.socket.RequestDispatcher;
import com.corntree.milpa.fly.protocol.ClientPacket.ClientRequest;

@Sharable
@Component
public class SocketRequestHandler extends SimpleChannelUpstreamHandler {

    public static final Logger logger = Logger.getLogger(SocketRequestHandler.class.getName());

    @Autowired
    private RequestDispatcher dispatcher;
    
    public SocketRequestHandler() {
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Channel channel = e.getChannel();
        ClientRequest clientRequest = (ClientRequest) e.getMessage();
        dispatcher.dispatchClientRequest(clientRequest, channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        logger.warn("Unexpected exception from downstream.", e.getCause());
        e.getChannel().close();
    }
}
