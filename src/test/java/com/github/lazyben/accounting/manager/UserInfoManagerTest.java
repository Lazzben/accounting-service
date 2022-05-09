package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.converter.p2c.UserInfoP2CConverter;
import com.github.lazyben.accounting.dao.mapper.UserInfoMapper;
import com.github.lazyben.accounting.exception.ResourceNotFoundException;
import com.github.lazyben.accounting.exception.ServiceException;
import com.github.lazyben.accounting.manager.model.persistence.UserInfo;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class UserInfoManagerTest {
    private UserInfoManagerImpl userInfoManager;
    @Mock
    private UserInfoMapper userInfoMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userInfoManager = new UserInfoManagerImpl(userInfoMapper, new UserInfoP2CConverter());
    }

    @Test
    public void testGetUserInfoById() throws ServiceException {
        // arrange
        val userid = 1L;
        val username = "lazyben";
        val password = "lazyben";
        val createTime = LocalDateTime.now();

        val userInfoPersistence = UserInfo.builder()
                .id(userid)
                .username(username)
                .password(password)
                .createTime(createTime)
                .build();

        doReturn(userInfoPersistence).when(userInfoMapper).getUserInfoById(anyLong());
        // act
        val userInfo = userInfoManager.getUserInfoById(userid);
        // assert
        assertEquals(userid, userInfo.getId());
        assertEquals(username, userInfo.getUsername());
        assertEquals(password, userInfo.getPassword());
        verify(userInfoMapper).getUserInfoById(anyLong());
    }

    @Test
    public void testGetUserInfoByIdWithInvalidParameter() {
        // arrange
        val userid = 100;
        // act
        doReturn(null).when(userInfoMapper).getUserInfoById(userid);
        // assert
        assertThrows(ResourceNotFoundException.class, () -> userInfoManager.getUserInfoById(userid));
    }
}
