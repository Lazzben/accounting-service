package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.model.common.UserInfo;

public interface UserInfoManager {
    String login(String username, String password);

    UserInfo register(String username, String password);

    UserInfo getUserInfoByUsername(String username);

    UserInfo getUserInfoById(long id);
}
