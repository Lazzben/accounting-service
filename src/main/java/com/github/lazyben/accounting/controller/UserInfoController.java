package com.github.lazyben.accounting.controller;

import com.github.lazyben.accounting.converter.c2s.UserInfoC2SConverter;
import com.github.lazyben.accounting.exception.InvalidParameterException;
import com.github.lazyben.accounting.manager.UserInfoManager;
import com.github.lazyben.accounting.model.service.UserInfo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/{id}", produces = "application/json")
    public UserInfo getUserInfoById(@PathVariable("id") long id) {
        // 参数校验
        if (id <= 0) {
            throw new InvalidParameterException(String.format("User id %s is invalid", id));
        }
        val userInfo = userInfoManager.getUserInfoById(id);
        return userInfoC2SConverter.convert(userInfo);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public UserInfo register(@RequestBody com.github.lazyben.accounting.model.common.UserInfo userInfo) {
        val newUserInfo = userInfoManager.register(userInfo.getUsername(), userInfo.getPassword());
        return userInfoC2SConverter.convert(newUserInfo);
    }
}
