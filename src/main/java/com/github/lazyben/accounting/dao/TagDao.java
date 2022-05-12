package com.github.lazyben.accounting.dao;

import com.github.lazyben.accounting.model.persistence.Tag;

import java.util.List;

public interface TagDao {
    List<Tag> getTagListByTagIds(List<Long> ids);
}
