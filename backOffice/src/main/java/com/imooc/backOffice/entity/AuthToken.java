package com.imooc.backOffice.entity;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author:hsoluo
 * @date 2021/3/12 17:26
 */
public class AuthToken implements AuthenticationToken {
    private String token;

    public AuthToken() {
    }

    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
