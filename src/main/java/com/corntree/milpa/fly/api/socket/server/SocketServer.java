package com.corntree.milpa.fly.api.socket.server;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Log4JLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.corntree.milpa.fly.common.ExecutorManager;

@Component
public class SocketServer {
    public static final Logger logger = Logger.getLogger(SocketServer.class.getName());

    private ExecutorService bossThreadPool = ExecutorManager.newCachedThreadPool("Socket-Server-Boss");
    private ExecutorService workerThreadPool = ExecutorManager.newCachedThreadPool("Socket-Server-Worker");

    @Value("${socketserver.port}")
    private int socketServerPort;
    @Autowired
    private SocketServerPipelineFactory pipelineFactory;

    @PostConstruct
    public void start() {
        InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
        ChannelFactory channelFactory = new NioServerSocketChannelFactory(bossThreadPool, workerThreadPool);
        ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);
        bootstrap.setPipelineFactory(pipelineFactory);
        bootstrap.bind(new InetSocketAddress(socketServerPort));
        logger.info("Socket server started, listening on :" + socketServerPort);
    }
}
