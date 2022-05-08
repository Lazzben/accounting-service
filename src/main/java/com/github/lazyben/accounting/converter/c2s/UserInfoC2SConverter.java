package com.github.lazyben.accounting.converter.c2s;

import com.github.lazyben.accounting.model.common.UserInfo;
import com.google.common.base.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserInfoC2SConverter extends Converter<UserInfo, com.github.lazyben.accounting.model.service.UserInfo> {
    @Override
    protected com.github.lazyben.accounting.model.service.UserInfo doForward(UserInfo userInfo) {
        return com.github.lazyben.accounting.model.service.UserInfo.builder()
                .username(userInfo.getUsername())
                .id(userInfo.getId())
                .build();
    }

    @Override
    protected UserInfo doBackward(com.github.lazyben.accounting.model.service.UserInfo userInfo) {
        return UserInfo.builder()
                .username(userInfo.getUsername())
                .id(userInfo.getId())
                .password(null)
                .build();
    }
}
