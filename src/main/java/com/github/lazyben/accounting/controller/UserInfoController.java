package com.github.lazyben.accounting.controller;

import com.github.lazyben.accounting.converter.c2s.UserInfoC2SConverter;
import com.github.lazyben.accounting.exception.InvalidParameterException;
import com.github.lazyben.accounting.exception.ServiceException;
import com.github.lazyben.accounting.manager.UserInfoManager;
import com.github.lazyben.accounting.model.service.UserInfo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {
    private final UserInfoManager userInfoManager;

    @Autowired
    public UserInfoController(UserInfoManager userInfoManager) {
        this.userInfoManager = userInfoManager;
    }

    @GetMapping("/test/{id}")
    public UserInfo sayHi(@PathVariable("id") long id) throws ServiceException {
        if (id < 0) {
            throw new InvalidParameterException(String.format("User id %s is invalid", id));
        }
        val userInfo = userInfoManager.getUserInfoById(id);
        return new UserInfoC2SConverter().convert(userInfo);
    }
}
