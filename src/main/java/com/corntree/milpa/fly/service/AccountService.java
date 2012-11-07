package com.corntree.milpa.fly.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corntree.milpa.fly.common.ValidateUtil;
import com.corntree.milpa.fly.domain.Account;
import com.corntree.milpa.fly.domain.Player;
import com.corntree.milpa.fly.service.exception.BadRequestExecption;
import com.corntree.milpa.fly.service.exception.IllegalEmailException;
import com.corntree.milpa.fly.service.exception.LoginException;
import com.corntree.milpa.fly.service.exception.UsernameExistException;

@Service
public class AccountService {

    private static final Logger logger = Logger.getLogger(AccountService.class.getName());

    @Autowired
    private SessionService sessionService;

    @Transactional
    public boolean registAccount(String username, String password, String email) throws BadRequestExecption {
        logger.info("regitst account for " + username);
        if (!ValidateUtil.isValidEmail(email)) {
            throw new IllegalEmailException();
        }
        if (Account.isUsernameUsed(username)) {
            throw new UsernameExistException();
        }
        Account account = new Account(username, password, email);
        account.persist();
        Player player = new Player("newPlayer",account);
        player.persist();
        account.setCurrentPlayer(player);
        return true;
    }

    public String login(String username, String password) throws LoginException {
        Account account = Account.findAccountByUsername(username);
        Player player = (account == null) ? null : account.getCurrentPlayer();
        return login(username, password, account, player);
    }

    public String login(String username, String password, long playerId) throws LoginException {
        Account account = Account.findAccountByUsername(username);
        Player player = Player.findPlayer(playerId);
        return login(username, password, account, player);
    }

    private String login(String username, String password, Account account, Player player) throws LoginException {
        if (account == null) {
            throw new LoginException("username not exist.");
        }
        if (player == null) {
            throw new LoginException("player not exist. pls pass the right playerId or pass no playerId");
        }
        if (!account.validatePassword(password)) {
            throw new LoginException("bad password.");
        }
        return sessionService.createSession(account, player).getToken();
    }
}
