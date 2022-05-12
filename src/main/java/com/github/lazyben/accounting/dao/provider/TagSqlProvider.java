package com.github.lazyben.accounting.dao.provider;

import com.github.lazyben.accounting.model.persistence.Tag;
import com.google.common.base.Joiner;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class TagSqlProvider {
    public String getTagListByIds(List<Long> ids) {
        return new SQL() {
            {
                SELECT("id", "user_id", "description", "status");
                FROM("tag");
                WHERE(String.format("id in ('%s')", Joiner.on("','").skipNulls().join(ids)));
            }
        }.toString();
    }
}
