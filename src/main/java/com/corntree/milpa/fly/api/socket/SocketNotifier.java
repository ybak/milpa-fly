package com.corntree.milpa.fly.api.socket;

import org.jboss.netty.channel.Channel;

import com.corntree.milpa.fly.domain.network.Notifier;

public class SocketNotifier implements Notifier{

    private Channel channel;

    public SocketNotifier(Channel channel) {
        this.channel = channel;
    }
    
    @Override
    public void notify(Object message){
        channel.write(message);
    }
   

}
