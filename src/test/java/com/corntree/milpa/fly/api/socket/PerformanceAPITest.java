package com.corntree.milpa.fly.api.socket;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.corntree.milpa.fly.api.socket.util.SocketClientHandler;
import com.corntree.milpa.fly.api.socket.util.SocketClientUtil;
import com.corntree.milpa.fly.protocol.ClientPacket.ClientRequest;
import com.corntree.milpa.fly.protocol.ClientPacket.ClientRequestType;
import com.corntree.milpa.fly.protocol.ClientPacket.DummyRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class PerformanceAPITest {

    private static final Logger logger = Logger.getLogger(PerformanceAPITest.class.getName());
    private static final int TOTAL_COUNT_DUMMY_REQUESTS = 1001;

    @Test
    public void dummyRequestLoopTest() throws Exception {

        new SocketClientUtil() {
            public void sendRequest(SocketClientHandler handler) throws Exception {
                DummyRequest dummyRequest = DummyRequest.newBuilder().setUsername("ybak").setPassword("passwd")
                        .setEmail("ybak@mail.com").build();
                ClientRequest clientRequest = ClientRequest.newBuilder()
                        .setClientRequestType(ClientRequestType.DUMMY_REQUEST)
                        .setRequestData(dummyRequest.toByteString()).build();

                handler.sendRequest(clientRequest);

                long now = System.currentTimeMillis();
                for (int i = 0; i < TOTAL_COUNT_DUMMY_REQUESTS - 1; i++) {
                    handler.sendRequest(clientRequest);
                }
                while (handler.getResponseCounter().intValue() != TOTAL_COUNT_DUMMY_REQUESTS) {
                    TimeUnit.MILLISECONDS.sleep(10);
                }
                // time with logging info: 900
                // time without logging: 300
                logger.warn("Time of send 1000 dummy requests spend : " + (System.currentTimeMillis() - now));
            }
        }.doTest();
    }

}
