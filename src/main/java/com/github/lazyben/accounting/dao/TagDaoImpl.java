package com.github.lazyben.accounting.dao;

import com.github.lazyben.accounting.dao.mapper.TagMapper;
import com.github.lazyben.accounting.model.persistence.Tag;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TagDaoImpl implements TagDao {
    private final TagMapper tagMapper;

    @Autowired
    public TagDaoImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public List<Tag> getTagListByTagIds(List<Long> ids) {
        val tags = tagMapper.getTagListByTagIds(ids);
        log.debug("getTagListByTagIds {}", tags);
        return tags;
    }
}
