package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.exception.ServiceException;
import com.github.lazyben.accounting.model.common.UserInfo;

public interface UserInfoManager {
    UserInfo getUserInfoById(long id);

    String login(String username, String password);

    UserInfo getUserInfoByUsername(String username);
}
