package com.github.lazyben.accounting.dao.mapper;

import com.github.lazyben.accounting.model.persistence.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RecordMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO record(user_id, note, status, amount, category, create_time)" +
            "VALUES (#{userId}, #{note}, #{status}, #{amount}, #{category}, #{createTime})")
    void createRecord(Record record);

    @Select("SELECT id, user_id, note, status, amount, create_time, update_time, category from record " +
            "where id=#{recordId}")
    Record getRecordByRecordId(Long recordId);
}
