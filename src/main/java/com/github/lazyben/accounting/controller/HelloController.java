package com.github.lazyben.accounting.controller;

import com.github.lazyben.accounting.dao.mapper.UserInfoMapper;
import com.github.lazyben.accounting.model.service.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private final UserInfoMapper userInfoMapper;

    @Autowired
    public HelloController(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @GetMapping("/test")
    public UserInfo sayHi() {
        return userInfoMapper.getUserInfoById(1);
    }
}
