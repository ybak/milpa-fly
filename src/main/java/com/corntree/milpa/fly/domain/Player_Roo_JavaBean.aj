// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.corntree.milpa.fly.domain;

import com.corntree.milpa.fly.domain.Player;

privileged aspect Player_Roo_JavaBean {
    
    public String Player.getDisplayName() {
        return this.displayName;
    }
    
    public void Player.setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
}
