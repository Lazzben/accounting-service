package com.github.lazyben.accounting.controller;

import com.github.lazyben.accounting.manager.UserInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0/session")
public class SessionController {
    private final UserInfoManager userInfoManager;

    @Autowired
    public SessionController(UserInfoManager userInfoManager) {
        this.userInfoManager = userInfoManager;
    }

    @PostMapping
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        return userInfoManager.login(username, password);
    }
}
