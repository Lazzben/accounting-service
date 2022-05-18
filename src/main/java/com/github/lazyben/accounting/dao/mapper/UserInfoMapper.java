package com.github.lazyben.accounting.dao.mapper;

import com.github.lazyben.accounting.model.persistence.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    @Select("SELECT id, username, password, salt, create_time, update_time from userinfo where id=#{id}")
    UserInfo getUserInfoById(@Param("id") long id);

    @Select("SELECT id, username, password, salt, create_time, update_time from userinfo where username =#{username}")
    UserInfo getUserInfoByUsername(@Param("username") String username);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Select("INSERT into userinfo(username, password, salt, create_time) "
            + "VALUES (#{username}, #{password}, #{salt}, #{createTime})")
    void createNewUser(UserInfo newUserInfo);
}
