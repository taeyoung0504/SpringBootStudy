package com.mysite.sbb.user;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
    private String social_nickname;
    private String email;
    private String sns;
   

    public SessionUser(SiteUser user) {
        this.social_nickname = user.getNickname();
        this.email = user.getEmail();
        this.sns = user.getSns();
      
    }
}