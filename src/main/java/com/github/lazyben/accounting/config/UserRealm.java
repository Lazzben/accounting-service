package com.github.lazyben.accounting.config;

import com.github.lazyben.accounting.manager.UserInfoManager;
import com.github.lazyben.accounting.model.common.UserInfo;
import lombok.val;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRealm extends AuthorizingRealm {
    private final UserInfoManager userInfoManager;

    @Autowired
    public UserRealm(UserInfoManager userInfoManager) {
        this.userInfoManager = userInfoManager;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        val password = new String((char[]) authenticationToken.getCredentials());
        val username = (String) authenticationToken.getPrincipal();

        UserInfo userInfo = userInfoManager.getUserInfoByUsername(username);

        if (!userInfo.getPassword().equals(password)) {
            throw new AuthenticationException(String.format("wrong password for user %s", username));
        }

        return new SimpleAuthenticationInfo(username, password, this.getName());
    }
}
