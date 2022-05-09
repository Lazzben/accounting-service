package com.github.lazyben.accounting.converter.p2c;

import com.github.lazyben.accounting.manager.model.persistence.UserInfo;
import com.google.common.base.Converter;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode(callSuper = true)
public class UserInfoP2CConverter extends Converter<UserInfo, com.github.lazyben.accounting.manager.model.common.UserInfo> {
    @Override
    protected com.github.lazyben.accounting.manager.model.common.UserInfo doForward(UserInfo userInfo) {
        return com.github.lazyben.accounting.manager.model.common.UserInfo.builder()
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .id(userInfo.getId())
                .build();
    }

    @Override
    protected UserInfo doBackward(com.github.lazyben.accounting.manager.model.common.UserInfo userInfo) {
        return UserInfo.builder()
                .username(userInfo.getUsername())
                .id(userInfo.getId())
                .build();
    }
}
