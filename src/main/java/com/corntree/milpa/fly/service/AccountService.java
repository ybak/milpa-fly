package com.corntree.milpa.fly.service;

import org.apache.log4j.Logger;

import com.corntree.milpa.fly.common.ValidateUtil;
import com.corntree.milpa.fly.domain.Account;
import com.corntree.milpa.fly.service.exception.BadRequestExecption;
import com.corntree.milpa.fly.service.exception.IllegalEmailException;

public class AccountService {

    private static final Logger logger = Logger.getLogger(AccountService.class.getName());

    public boolean registAccount(String username, String password, String email) throws BadRequestExecption {
        logger.info("regitst account for " + username);
        if (!ValidateUtil.isValidEmail(email)) {
            throw new IllegalEmailException();
        }
        if (Account.isUsernameUsed(username)) {
            return false;
        }
        new Account(username, password, email).persist();
        return true;
    }
}
