package com.github.lazyben.accounting.config;

import lombok.val;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        val username = new String((char[]) authenticationToken.getCredentials());
        val password = (String) authenticationToken.getPrincipal();

        if (!password.equals("lazyben")) {
            throw new AuthenticationException(String.format("wrong password for user %s", username));
        }

        return new SimpleAuthenticationInfo(username, password, this.getName());
    }
}
