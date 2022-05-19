package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.model.PagedResponse;
import com.github.lazyben.accounting.model.common.Tag;

public interface TagManager {
    Tag createTag(com.github.lazyben.accounting.model.service.Tag tag);

    Tag getTagByDescription(Long userId, String description);

    Tag updateTag(Long tagId, com.github.lazyben.accounting.model.service.Tag tag);

    Tag getTagById(Long id);

    PagedResponse<Tag> getTags(Long id, int pageNum, int pageSize);
}
