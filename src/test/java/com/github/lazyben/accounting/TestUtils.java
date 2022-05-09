package com.github.lazyben.accounting;

import com.github.lazyben.accounting.converter.c2s.UserInfoC2SConverter;
import com.github.lazyben.accounting.converter.p2c.UserInfoP2CConverter;
import com.github.lazyben.accounting.manager.model.persistence.UserInfo;

import java.time.LocalDateTime;

public class TestUtils {
    private static final UserInfoP2CConverter userInfoP2CConverter = new UserInfoP2CConverter();
    private static final UserInfoC2SConverter userInfoC2SConverter = new UserInfoC2SConverter();

    public static final UserInfo userInfoPersistence = UserInfo.builder()
            .id(1L)
            .username("lazyben")
            .password("lazyben")
            .createTime(LocalDateTime.now())
            .build();
    public static final com.github.lazyben.accounting.manager.model.common.UserInfo userInfoCommon = userInfoP2CConverter.convert(userInfoPersistence);
    public static final com.github.lazyben.accounting.manager.model.service.UserInfo userInfoService = userInfoC2SConverter.convert(userInfoCommon);
}
