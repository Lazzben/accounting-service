package com.github.lazyben.accounting.controller;

import com.alibaba.fastjson.JSON;
import com.github.lazyben.accounting.converter.c2s.UserInfoC2SConverter;
import com.github.lazyben.accounting.exception.GlobalExceptionHandler;
import com.github.lazyben.accounting.exception.ResourceNotFoundException;
import com.github.lazyben.accounting.manager.UserInfoManager;
import com.github.lazyben.accounting.manager.model.service.UserInfo;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserInfoControllerTest {
    @Mock
    private UserInfoManager userInfoManager;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        UserInfoController userInfoController = new UserInfoController(userInfoManager, new UserInfoC2SConverter());
        mockMvc = MockMvcBuilders.standaloneSetup(userInfoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testGetUserInfoById() throws Exception {
        // arrange
        val userid = 1L;
        val username = "lazyben";
        val password = "lazyben";
        val userInfoCommon = com.github.lazyben.accounting.manager.model.common.UserInfo.builder()
                .id(userid)
                .username(username)
                .password(password)
                .build();
        val userInfoService = UserInfo.builder()
                .id(userid)
                .username(username)
                .build();

        doReturn(userInfoCommon).when(userInfoManager).getUserInfoById(anyLong());

        // act && assert
        mockMvc.perform(get("/v1.0/userinfo/" + userid).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON.toJSONString(userInfoService)));
        verify(userInfoManager).getUserInfoById(anyLong());
    }

    @Test
    public void testGetUserInfoByIdWithInvalidParameter() throws Exception {
        // arrange
        val userid = -1L;

        // act && assert
        mockMvc.perform(get("/v1.0/userinfo/" + userid).contentType("application/json"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("{\"bizErrorCode\":\"INVALID_PARAMETER\",\"message\":\"User id -1 is invalid\"}"));
    }

    @Test
    public void testGetUserInfoByIdWithResourceNotFound() throws Exception {
        // arrange
        val userid = 100;
        doThrow(new ResourceNotFoundException(String.format("User %s was not found", userid)))
                .when(userInfoManager).getUserInfoById(userid);

        // act && assert
        mockMvc.perform(get("/v1.0/userinfo/" + userid).contentType("application/json"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("{\"bizErrorCode\":\"RESOURCE_NOT_FOUND\",\"message\":\"User 100 was not found\"}"));
    }
}
