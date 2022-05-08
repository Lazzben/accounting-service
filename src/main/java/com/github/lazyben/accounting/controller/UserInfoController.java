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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/userinfo")
public class UserInfoController {
    private final UserInfoManager userInfoManager;
    private final UserInfoC2SConverter userInfoC2SConverter;

    @Autowired
    public UserInfoController(UserInfoManager userInfoManager, UserInfoC2SConverter userInfoC2SConverter) {
        this.userInfoManager = userInfoManager;
        this.userInfoC2SConverter = userInfoC2SConverter;
    }

    @GetMapping("/{id}")
    public UserInfo getUserInfoById(@PathVariable("id") long id) throws ServiceException {
        // 参数校验
        if (id <= 0) {
            throw new InvalidParameterException(String.format("User id %s is invalid", id));
        }
        val userInfo = userInfoManager.getUserInfoById(id);
        return userInfoC2SConverter.convert(userInfo);
    }
}
