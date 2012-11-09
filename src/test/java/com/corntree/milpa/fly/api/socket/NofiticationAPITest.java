package com.corntree.milpa.fly.api.socket;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.corntree.milpa.fly.api.socket.util.SocketClientHandler;
import com.corntree.milpa.fly.api.socket.util.SocketClientUtil;
import com.corntree.milpa.fly.protocol.ClientPacket.ClientRequest;
import com.corntree.milpa.fly.protocol.ClientPacket.ClientRequestType;
import com.corntree.milpa.fly.protocol.ClientPacket.LoginRequest;
import com.corntree.milpa.fly.protocol.Commons.PayloadType;
import com.corntree.milpa.fly.protocol.Commons.Token;
import com.corntree.milpa.fly.protocol.ServerPacket.ResponseCode;
import com.corntree.milpa.fly.protocol.ServerPacket.ServerResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class NofiticationAPITest {

    private static final Logger logger = Logger.getLogger(NofiticationAPITest.class.getName());

    @Test
    public void getNotification() throws Exception {
        new SocketClientUtil() {
            public void sendRequest(SocketClientHandler handler) throws Exception {
                LoginRequest loginRequest = LoginRequest.newBuilder().setUsername("test").setPassword("password")
                        .build();
                ClientRequest clientRequest = ClientRequest.newBuilder()
                        .setClientRequestType(ClientRequestType.LOGIN_REQUEST)
                        .setRequestData(loginRequest.toByteString()).build();
                ServerResponse serverResponse = handler.sendAndGet(clientRequest);
                Assert.assertEquals(ResponseCode.OK, serverResponse.getCode());
                Assert.assertEquals(PayloadType.TOKEN, serverResponse.getPayloadType());
                Token token = Token.parseFrom(serverResponse.getPayloadData());

                clientRequest = ClientRequest.newBuilder()
                        .setClientRequestType(ClientRequestType.GET_NOTIFICATION_REQUEST).setToken(token.getToken()).build();
                serverResponse = handler.sendAndGet(clientRequest, 3);
                Assert.assertEquals(ResponseCode.OK, serverResponse.getCode());
            }
        }.doTest();
    }

}
