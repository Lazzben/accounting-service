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

    /**
     * @api {post} /session 登录
     * @apiName Login
     * @apiGroup 用户
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiBody {String} username 用户名
     * @apiBody {String} password 密码
     * @apiParamExample {json} Request-Example:
     * {
     * "username": "lazyben",
     * "password": "lazyben"
     * }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * @apiError 404 Not Found 用户不存在
     * @apiError 400 Bad Request 用户的密码错误
     * @apiErrorExample {json} Error-Response:
     * {
     * "bizErrorCode": "RESOURCE_NOT_FOUND",
     * "message": "Username xxx was not found"
     * }
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public String login(@RequestBody UserInfo userInfo) {
        return userInfoManager.login(userInfo.getUsername(), userInfo.getPassword());
    }
}
