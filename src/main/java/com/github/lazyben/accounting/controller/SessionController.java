package com.github.lazyben.accounting.controller;

import com.github.lazyben.accounting.manager.UserInfoManager;
import com.github.lazyben.accounting.model.common.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0/session")
public class SessionController {
    private final UserInfoManager userInfoManager;

    @Autowired
    public SessionController(UserInfoManager userInfoManager) {
        this.userInfoManager = userInfoManager;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public String login(@RequestBody UserInfo userInfo) {
        return userInfoManager.login(userInfo.getUsername(), userInfo.getPassword());
    }
}
