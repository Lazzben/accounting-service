package com.github.lazyben.accounting.dao.mapper;

import com.github.lazyben.accounting.model.persistence.RecordTagMapping;
import com.github.lazyben.accounting.model.persistence.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordTagMappingMapper {
    @Insert({
            "<script>",
            "INSERT INTO record_tag_mapping(tag_id, record_id, status, create_time) VALUES",
            "<foreach item='item' index='index' collection='recordTagMappings' ",
            "open='(' separator = '),(' close=')'>",
            "#{item.tagId}, #{item.recordId}, #{item.status}, #{item.createTime}",
            "</foreach>",
            "</script>"
    })
    void batchInsertRecordTagMapping(@Param("recordTagMappings") List<RecordTagMapping> recordTagMappings);

    @Select("SELECT t.id, t.user_id, t.status, t.description, t.create_time, t.update_time " +
            "from tag t " +
            "left join record_tag_mapping r " +
            "on r.tag_id = t.id " +
            "where r.record_id = #{recordId}")
    List<Tag> getTagsByRecordId(Long recordId);
}
