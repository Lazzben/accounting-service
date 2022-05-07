package com.github.lazyben.accounting.dao.mapper;

import com.github.lazyben.accounting.model.service.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    @Select("SELECT id, username from userinfo where id=#{id}")
    UserInfo getUserInfoById(@Param("id") long id);
}
