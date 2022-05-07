package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.converter.p2c.UserInfoP2CConverter;
import com.github.lazyben.accounting.dao.mapper.UserInfoMapper;
import com.github.lazyben.accounting.exception.ResourceNotFoundException;
import com.github.lazyben.accounting.model.common.UserInfo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoManager {
    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserInfoManager(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    public UserInfo getUserInfoById(long id) throws ResourceNotFoundException {
        val userInfo = Optional.ofNullable(userInfoMapper.getUserInfoById(id))
                .orElseThrow(()->new ResourceNotFoundException(String.format("User %s was not found", id)));
        return new UserInfoP2CConverter().convert(userInfo);
    }
}
