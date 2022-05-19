package com.github.lazyben.accounting.dao;

import com.github.lazyben.accounting.model.persistence.Tag;

import java.util.List;

public interface TagDao {
    List<Tag> getTagListByTagIds(List<Long> ids);

    List<Tag> getTags(Long id, int offset, int pageSize);

    int getTagsCount(Long id);
}
