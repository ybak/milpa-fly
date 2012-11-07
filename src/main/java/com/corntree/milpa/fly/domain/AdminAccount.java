package com.corntree.milpa.fly.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class AdminAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",columnDefinition="int(11)")
    private Long id;

    @Column(unique = true, length = 50)
    public String username;
    
    public String password;
    
}
