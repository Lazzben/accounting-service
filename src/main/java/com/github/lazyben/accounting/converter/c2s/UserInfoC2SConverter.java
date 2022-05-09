package com.github.lazyben.accounting.converter.c2s;

import com.github.lazyben.accounting.manager.model.service.UserInfo;
import com.google.common.base.Converter;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode(callSuper = true)
public class UserInfoC2SConverter extends Converter<com.github.lazyben.accounting.manager.model.common.UserInfo, UserInfo> {
    @Override
    protected UserInfo doForward(com.github.lazyben.accounting.manager.model.common.UserInfo userInfo) {
        return UserInfo.builder()
                .username(userInfo.getUsername())
                .id(userInfo.getId())
                .build();
    }

    @Override
    protected com.github.lazyben.accounting.manager.model.common.UserInfo doBackward(UserInfo userInfo) {
        return com.github.lazyben.accounting.manager.model.common.UserInfo.builder()
                .username(userInfo.getUsername())
                .id(userInfo.getId())
                .password(null)
                .build();
    }
}
