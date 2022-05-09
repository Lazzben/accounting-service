package com.github.lazyben.accounting.dao.mapper;

import com.github.lazyben.accounting.model.persistence.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    @Select("SELECT id, username, password, create_time, update_time from userinfo where id=#{id}")
    UserInfo getUserInfoById(@Param("id") long id);

    @Select("SELECT id, username, password, create_time, update_time from userinfo where username=#{username}")
    UserInfo getUserInfoByUsername(@Param("username") String username);
}
