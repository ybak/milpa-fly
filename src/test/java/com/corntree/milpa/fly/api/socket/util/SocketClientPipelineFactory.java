package com.corntree.milpa.fly.api.socket.util;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.jboss.netty.handler.logging.LoggingHandler;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.corntree.milpa.fly.protocol.ServerPacket;

public class SocketClientPipelineFactory implements ChannelPipelineFactory {

    private Timer timer = new HashedWheelTimer();

    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline p = pipeline();

        p.addLast("timeoutHandler", new ReadTimeoutHandler(timer, 5));
        p.addLast("log", new LoggingHandler());
        p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        p.addLast("protobufDecoder", new ProtobufDecoder(ServerPacket.ServerResponse.getDefaultInstance()));

        p.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
        p.addLast("protobufEncoder", new ProtobufEncoder());

        p.addLast("handler", new SocketClientHandler());
        return p;
    }
}
