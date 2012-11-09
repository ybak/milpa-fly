package com.corntree.milpa.fly.api.socket.util;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Log4JLoggerFactory;

public abstract class SocketClientUtil {
    private static final Logger logger = Logger.getLogger(SocketClientUtil.class.getName());
    private static final InetSocketAddress REMOTE_ADDRESS = new InetSocketAddress("localhost", 8081);

    public void doTest() throws Exception {
        InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
        ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new SocketClientPipelineFactory());
        
        ChannelFuture connectFuture = bootstrap.connect(REMOTE_ADDRESS);
        Channel channel = connectFuture.awaitUninterruptibly().getChannel();
        SocketClientHandler handler = channel.getPipeline().get(SocketClientHandler.class);

        try {
            sendRequest(handler);
        } catch (Exception e) {
            logger.error(e);
            Assert.fail(e.getMessage());
        }

        channel.close().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
    }

    public abstract void sendRequest(SocketClientHandler handler) throws Exception;
}
