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

    /**
     * @api {get} /userinfo/:id 获取用户
     * @apiName Get user by id
     * @apiGroup User
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParam {Long} id 用户id
     * @apiSuccessExample {json} Success-Response:
     * {
     * "id": 1,
     * "username": "lazyben"
     * }
     * @apiError 404 Not Found 用户不存在
     * @apiError 401 Unauthorized 用户未登录
     * @apiErrorExample {json} Error-Response:
     * {
     * "bizErrorCode": "RESOURCE_NOT_FOUND",
     * "message": "Username xxx was not found"
     * }
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public UserInfo getUserInfoById(@PathVariable("id") long id) {
        // 参数校验
        if (id <= 0) {
            throw new InvalidParameterException(String.format("User id %s is invalid", id));
        }
        val userInfo = userInfoManager.getUserInfoById(id);
        return userInfoC2SConverter.convert(userInfo);
    }

    /**
     * @api {post} /userinfo 注册
     * @apiName Register
     * @apiGroup User
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiBody {String} username 用户名
     * @apiBody {String} password 密码
     * @apiParamExample {json} Request-Example:
     * {
     * "username": "lazyben",
     * "password": "lazyben"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "id": 1,
     * "username": "lazyben"
     * }
     * @apiError 400 Bad Request 用户已经存在
     * @apiErrorExample {json} Error-Response:
     * {
     * "bizErrorCode": "INVALID_PARAMETER",
     * "message": "Username lazyben already exists"
     * }
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public UserInfo register(@RequestBody com.github.lazyben.accounting.model.common.UserInfo userInfo) {
        val newUserInfo = userInfoManager.register(userInfo.getUsername(), userInfo.getPassword());
        return userInfoC2SConverter.convert(newUserInfo);
    }
}
