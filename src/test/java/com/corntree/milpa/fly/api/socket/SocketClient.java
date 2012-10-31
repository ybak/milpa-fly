package com.corntree.milpa.fly.api.socket;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Log4JLoggerFactory;

import com.corntree.milpa.fly.protocol.request.Request.ClientRequest;
import com.corntree.milpa.fly.protocol.request.Request.ClientRequestType;
import com.corntree.milpa.fly.protocol.request.Request.RegistRequest;
import com.google.protobuf.ByteString;

public class SocketClient {
    private static final Logger logger = Logger.getLogger(SocketClient.class.getName());


    public static void main(String[] argvs) throws Exception {
        InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
        String[] args = { "localhost", "8080" };

        // Parse options.
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        // Set up.
        ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

        // Configure the event pipeline factory.
        bootstrap.setPipelineFactory(new SocketClientPipelineFactory());

        // Make a new connection.
        ChannelFuture connectFuture = bootstrap.connect(new InetSocketAddress(host, port));

        // Wait until the connection is made successfully.
        Channel channel = connectFuture.awaitUninterruptibly().getChannel();

        // Get the handler instance to initiate the request.
        SocketClientHandler handler = channel.getPipeline().get(SocketClientHandler.class);
        long now = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            ByteString data = RegistRequest.newBuilder().setUsername("ybak").setPassword("passwd")
                    .setEmail("ybak@mail.com").build().toByteString();
            handler.sendRequest(ClientRequest.newBuilder().setClientRequestType(ClientRequestType.REGIST_REQUEST)
                    .setPacketData(data).build());
        }
        while (handler.getResponseCounter().intValue() != 1000) {
            TimeUnit.MILLISECONDS.sleep(10);
        }
        logger.info(System.currentTimeMillis() - now);

        // Close the connection.
        channel.close().awaitUninterruptibly();

        // Shut down all thread pools to exit.
        bootstrap.releaseExternalResources();
    }
}
