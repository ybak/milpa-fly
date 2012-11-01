package com.corntree.milpa.fly.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.corntree.milpa.fly.service.vo.Session;

@Service
public class SessionService {
    private static final Logger logger = Logger.getLogger(SessionService.class.getName());

    private Map<String, Session> sessionMap = new HashMap<String, Session>();

    public Session createSession(String username) {
        Session session = new Session(username);
        if (sessionMap.containsKey(session.getToken())) {
            logger.error("session token collision, problem with the md5 session token generation.");
        }
        sessionMap.put(session.getToken(), session);
        return session;
    }

}
