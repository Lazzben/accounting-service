package com.github.lazyben.accounting.dao.mapper;

import com.github.lazyben.accounting.dao.provider.TagSqlProvider;
import com.github.lazyben.accounting.model.persistence.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO tag(user_id, description, status, create_time)" +
            "VALUES (#{userId}, #{description}, #{status}, #{createTime})")
    void createTag(Tag newTag);

    @Select("SELECT id, user_id, description, status, create_time, update_time " +
            "from tag where description=#{description} and user_id=#{userId}")
    Tag getTagByDescription(Long userId, String description);

    @Select("SELECT id, user_id, description, status, create_time, update_time " +
            "from tag where id=#{id}")
    Tag getTagById(Long id);

    @Update("UPDATE tag set description=#{description}, status=#{status} where id=#{id}")
    void updateTag(com.github.lazyben.accounting.model.common.Tag tag);

    @SelectProvider(type = TagSqlProvider.class, method = "getTagListByIds")
    List<Tag> getTagListByTagIds(List<Long> ids);
}
