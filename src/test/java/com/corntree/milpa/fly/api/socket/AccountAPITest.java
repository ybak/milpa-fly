package com.corntree.milpa.fly.api.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

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
import com.corntree.milpa.fly.protocol.ServerPacket.ResponseCode;
import com.corntree.milpa.fly.protocol.ServerPacket.ServerResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class AccountAPITest {
    private static final Logger logger = Logger.getLogger(AccountAPITest.class.getName());
    private static final InetSocketAddress REMOTE_ADDRESS = new InetSocketAddress("localhost", 8081);

    @Test
    public void loginRequestTestWithNetty() throws Exception {
        new SocketClientUtil() {
            public void sendRequest(SocketClientHandler handler) throws Exception {
                LoginRequest loginRequest = LoginRequest.newBuilder().setUsername("ybak").setPassword("password")
                        .build();
                ClientRequest clientRequest = ClientRequest.newBuilder()
                        .setClientRequestType(ClientRequestType.LOGIN_REQUEST)
                        .setRequestData(loginRequest.toByteString()).build();
                ServerResponse serverResponse = handler.requestAndGet(clientRequest);
                logger.warn(serverResponse);
                Assert.assertEquals(ResponseCode.BAD_PARAMETER_LOGIN, serverResponse.getCode());
            }
        }.doTest();
    }

    @Test
    public void loginRequestTest() throws Exception {
        Socket socket = new Socket();
        socket.connect(REMOTE_ADDRESS);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        LoginRequest loginRequest = LoginRequest.newBuilder().setUsername("ybak").setPassword("password").build();
        ClientRequest clientRequest = ClientRequest.newBuilder().setClientRequestType(ClientRequestType.LOGIN_REQUEST)
                .setRequestData(loginRequest.toByteString()).build();
        clientRequest.writeDelimitedTo(outputStream);

        ServerResponse serverResponse = ServerResponse.parseDelimitedFrom(inputStream);
        logger.warn(serverResponse);
        Assert.assertEquals(ResponseCode.BAD_PARAMETER_LOGIN, serverResponse.getCode());

        socket.close();
    }
}
