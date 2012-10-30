package com.corntree.milpa.fly.api.socket;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.corntree.milpa.fly.protocol.request.Request.ClientRequest;

public class SocketClientHandler extends SimpleChannelUpstreamHandler {

	private static final Logger logger = Logger.getLogger(SocketClientHandler.class.getName());

	// Stateful properties
	private volatile Channel channel;
	
	private AtomicInteger responseCounter = new AtomicInteger(0);

	public AtomicInteger getResponseCounter() {
        return responseCounter;
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
	    responseCounter.incrementAndGet();
		logger.info(e);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.warn("Unexpected exception from downstream.", e.getCause());
		e.getChannel().close();
	}
}