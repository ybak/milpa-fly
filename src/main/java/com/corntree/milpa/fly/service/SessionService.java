package com.corntree.milpa.fly.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.corntree.milpa.fly.domain.Account;
import com.corntree.milpa.fly.domain.Player;
import com.corntree.milpa.fly.domain.network.Session;

@Service
public class SessionService {
    private static final Logger logger = Logger.getLogger(SessionService.class.getName());

    private Map<String, Session> tokenSessionMap = new HashMap<String, Session>();

    public Session createSession(Account account, Player player) {
        Session session = new Session(account, player);
        if (tokenSessionMap.containsKey(session.getToken())) {
            logger.error("session token collision, problem with the md5 session token generation.");
        }
        tokenSessionMap.put(session.getToken(), session);
        return session;
    }

    public boolean isTokenValidate(String token) {
        return tokenSessionMap.containsKey(token);
    }

}
