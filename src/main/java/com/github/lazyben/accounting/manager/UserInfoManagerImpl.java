package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.converter.p2c.UserInfoP2CConverter;
import com.github.lazyben.accounting.dao.mapper.UserInfoMapper;
import com.github.lazyben.accounting.exception.InvalidParameterException;
import com.github.lazyben.accounting.exception.ResourceNotFoundException;
import com.github.lazyben.accounting.model.common.UserInfo;
import com.sun.org.apache.xml.internal.security.algorithms.implementations.SignatureDSA;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserInfoManagerImpl implements UserInfoManager {
    public static final int HASH_ITERATIONS = 1000;
    private final UserInfoMapper userInfoMapper;
    private final UserInfoP2CConverter userInfoP2CConverter;

    @Autowired
    public UserInfoManagerImpl(UserInfoMapper userInfoMapper, UserInfoP2CConverter userInfoP2CConverter) {
        this.userInfoMapper = userInfoMapper;
        this.userInfoP2CConverter = userInfoP2CConverter;
    }

    public UserInfo getUserInfoById(long id) {
        return Optional.ofNullable(userInfoMapper.getUserInfoById(id))
                .map(userInfoP2CConverter::convert)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s was not found", id)));
    }

    @Override
    public String login(String username, String password) {
        // get Subject
        val subject = SecurityUtils.getSubject();
        // create token
        val token = new UsernamePasswordToken(username, password);
        // login
        subject.login(token);
        return "success";
    }

    @Override
    public UserInfo getUserInfoByUsername(String username) {
        return Optional.ofNullable(userInfoMapper.getUserInfoByUsername(username))
                .map(userInfoP2CConverter::convert)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User name %s was not found", username)));
    }

    @Override
    public UserInfo register(String username, String password) {
        val userInfo = userInfoMapper.getUserInfoByUsername(username);
        if (userInfo != null) {
            throw new InvalidParameterException(String.format("User name %s already exists", username));
        }

        val salt = UUID.randomUUID().toString();
        val encodePassword = new Sha256Hash(password, salt, HASH_ITERATIONS).toBase64();
        val newUserInfo = com.github.lazyben.accounting.model.persistence.UserInfo.builder()
                .username(username)
                .password(encodePassword)
                .salt(salt)
                .createTime(LocalDateTime.now())
                .build();

        userInfoMapper.createNewUser(newUserInfo);

        return userInfoP2CConverter.convert(newUserInfo);
    }
}
