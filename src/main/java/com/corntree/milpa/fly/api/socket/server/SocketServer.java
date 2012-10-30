package com.corntree.milpa.fly.api.socket.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Log4JLoggerFactory;

public class SocketServer {

	public void start() {
		InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new SocketServerPipelineFactory());
		bootstrap.bind(new InetSocketAddress(8080));
	}
	
	public static void main(String[] args) {
		new SocketServer().start();
	}
}
