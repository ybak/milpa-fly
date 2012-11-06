package com.corntree.milpa.fly.api.socket.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.corntree.milpa.fly.protocol.ClientPacket.ClientRequest;
import com.corntree.milpa.fly.protocol.ServerPacket.ServerResponse;

public class SocketClientHandler extends SimpleChannelUpstreamHandler {

    private static final Logger logger = Logger.getLogger(SocketClientHandler.class.getName());

    private volatile Channel channel;

    private final BlockingQueue<ServerResponse> responses = new LinkedBlockingQueue<ServerResponse>();

    private AtomicInteger responseCounter = new AtomicInteger(0);

    public AtomicInteger getResponseCounter() {
        return responseCounter;
    }

    public ServerResponse requestAndGet(ClientRequest request) throws Exception {
        channel.write(request);
        return responses.take();
    }

    public void sendRequest(ClientRequest request) throws Exception {
        channel.write(request);
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            logger.info(e.toString());
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        channel = e.getChannel();
        super.channelOpen(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, final MessageEvent e) {
        responses.add((ServerResponse) e.getMessage());
        responseCounter.incrementAndGet();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        logger.warn("Unexpected exception from downstream.", e.getCause());
        e.getChannel().close();
    }
}
