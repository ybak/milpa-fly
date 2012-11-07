package com.corntree.milpa.fly.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TypedQuery;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "int(11)")
    private Long id;

    @Column(unique = true, length = 50)
    private String username;

    @Column(length = 64)
    private String hashedPassword;

    @Column(length = 30)
    private String email;

    @OneToOne
    private Player currentPlayer;

    public Account() {
    }

    public Account(String username, String password, String email) {
        super();
        this.username = username;
        this.email = email;
        setPassword(password);
    }

    public boolean validatePassword(String password) {
        return hashedPassword.equals(DigestUtils.sha256Hex(password));
    }

    public void setPassword(String password) {
        this.hashedPassword = DigestUtils.sha256Hex(password);
    }

    public static boolean isUsernameUsed(String username) {
        TypedQuery<Boolean> query = entityManager()
                .createQuery(
                        "SELECT case when (count(*) > 0)  then true else false end  FROM Account o where o.username = :username",
                        Boolean.class);
        query.setParameter("username", username);
        return query.getResultList().get(0);
    }

    public static Account findAccountByUsername(String username) {
        TypedQuery<Account> query = entityManager().createQuery("SELECT o FROM Account o where o.username = :username",
                Account.class);
        query.setParameter("username", username);
        List<Account> resultList = query.getResultList();
        return resultList.size() == 0 ? null : resultList.get(0);
    }
    
}
