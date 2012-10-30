package com.corntree.milpa.fly.service;

import org.apache.log4j.Logger;

public class AccountService {

    private static final Logger logger = Logger.getLogger(AccountService.class.getName());

    public void registAccount(String username, String password, String email) {
        logger.info("regitst account for " + username);
    }
}
