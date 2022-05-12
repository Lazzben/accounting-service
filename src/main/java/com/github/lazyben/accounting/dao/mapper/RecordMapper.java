package com.github.lazyben.accounting.dao.mapper;

import com.github.lazyben.accounting.model.persistence.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface RecordMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO record(user_id, note, status, amount, category, create_time)" +
            "VALUES (#{userId}, #{note}, #{status}, #{amount}, #{category}, #{createTime})")
    void createRecord(Record record);
}
