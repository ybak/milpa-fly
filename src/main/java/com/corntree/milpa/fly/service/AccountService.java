package com.corntree.milpa.fly.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corntree.milpa.fly.common.ValidateUtil;
import com.corntree.milpa.fly.domain.Account;
import com.corntree.milpa.fly.service.exception.BadRequestExecption;
import com.corntree.milpa.fly.service.exception.IllegalEmailException;
import com.corntree.milpa.fly.service.exception.LoginException;
import com.corntree.milpa.fly.service.exception.UsernameExistException;

@Service
public class AccountService {

    private static final Logger logger = Logger.getLogger(AccountService.class.getName());

    @Autowired
    private SessionService sessionService;

    public boolean registAccount(String username, String password, String email) throws BadRequestExecption {
        logger.info("regitst account for " + username);
        if (!ValidateUtil.isValidEmail(email)) {
            throw new IllegalEmailException();
        }
        if (Account.isUsernameUsed(username)) {
            throw new UsernameExistException();
        }
        new Account(username, password, email).persist();
        return true;
    }

    public String login(String username, String password) throws BadRequestExecption {
        Account account = Account.findAccountByUsername(username);
        if (account != null && account.validatePassword(password)) {
            logger.info("user " + username + " login ok.");
            return sessionService.createSession(username).getToken();
        }
        logger.warn("user " + username + " login failed.");
        throw new LoginException();
    }
}
