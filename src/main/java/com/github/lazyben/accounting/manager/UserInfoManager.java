package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.exception.ServiceException;
import com.github.lazyben.accounting.manager.model.common.UserInfo;

public interface UserInfoManager {
    UserInfo getUserInfoById(long id) throws ServiceException;
}
