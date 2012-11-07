package com.corntree.milpa.fly.domain;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
public class AccountIntegrationTest {

    @Test
    public void test() {
        boolean isUsernameUsed = Account.isUsernameUsed("usernotexist");
        Assert.assertFalse(isUsernameUsed);
    }
}
