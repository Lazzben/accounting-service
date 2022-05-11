package com.github.lazyben.accounting.manager;

import com.github.lazyben.accounting.converter.p2c.TagP2CConverter;
import com.github.lazyben.accounting.dao.mapper.TagMapper;
import com.github.lazyben.accounting.exception.InvalidParameterException;
import com.github.lazyben.accounting.exception.ResourceNotFoundException;
import com.github.lazyben.accounting.model.Status;
import com.github.lazyben.accounting.model.common.Tag;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TagManagerImpl implements TagManager {
    private final TagMapper tagMapper;
    private final UserInfoManager userInfoManager;
    private final TagP2CConverter tagP2CConverter;

    @Autowired
    public TagManagerImpl(TagMapper tagMapper, UserInfoManager userInfoManager, TagP2CConverter tagP2CConverter) {
        this.tagMapper = tagMapper;
        this.userInfoManager = userInfoManager;
        this.tagP2CConverter = tagP2CConverter;
    }


    /**
     * @param tag tag to be created
     * @return created new tag
     */
    @Override
    public Tag createTag(com.github.lazyben.accounting.model.service.Tag tag) {
        // 判断userId对应user是否存在
        userInfoManager.getUserInfoById(tag.getUserId());
        // 检验该 userid 下是否有该 description
        if (getTagByDescription(tag.getUserId(), tag.getDescription()) != null) {
            throw new InvalidParameterException(String.format("User id had tag %s", tag.getDescription()));
        }
        // 构造newTag
        val newTag = com.github.lazyben.accounting.model.persistence.Tag.builder()
                .userId(tag.getUserId())
                .description(tag.getDescription())
                .createTime(LocalDateTime.now())
                .status(Status.ENABLE)
                .createTime(LocalDateTime.now())
                .build();

        tagMapper.createTag(newTag);
        return tagP2CConverter.convert(newTag);
    }

    /**
     * @param userId      user id
     * @param description tag's description
     * @return The tag with the related user id and description.
     */
    @Override
    public Tag getTagByDescription(Long userId, String description) {
        return Optional.ofNullable(tagMapper.getTagByDescription(userId, description))
                .map(tagP2CConverter::convert)
                .orElse(null);
    }

    @Override
    public Tag getTagById(Long id) {
        return Optional.ofNullable(tagMapper.getTagById(id))
                .map(tagP2CConverter::convert)
                .orElse(null);
    }

    @Override
    public Tag updateTag(Long tagId, com.github.lazyben.accounting.model.service.Tag tagTobeUpdated) {
        val tag = getTagById(tagId);
        // 检测该tagId是否对应有tag
        if (tag == null) {
            throw new ResourceNotFoundException(String.format("tag %s not found", tagId));
        }
        // 检测该tag是否属于指定的用户
        if (!tag.getUserId().equals(tagTobeUpdated.getUserId())) {
            throw new InvalidParameterException(String.format("tag %s doesn't belong to user id %s", tagId, tagTobeUpdated.getUserId()));
        }
        // 检测是否有更改
        if (!checkUpdate(tag, tagTobeUpdated)) {
            throw new InvalidParameterException("tag has nothing to change");
        }
        return doUpdateTag(tag, tagTobeUpdated);
    }

    private boolean checkUpdate(Tag oldTag, com.github.lazyben.accounting.model.service.Tag tagTobeUpdated) {
        return (tagTobeUpdated.getStatus() != null && !tagTobeUpdated.getStatus().equals(oldTag.getStatus())) ||
                (tagTobeUpdated.getDescription() != null && !tagTobeUpdated.getDescription().equals(oldTag.getDescription()));
    }

    private Tag doUpdateTag(Tag tag, com.github.lazyben.accounting.model.service.Tag tagTobeUpdated) {
        if (tagTobeUpdated.getDescription() != null) {
            tag.setDescription(tagTobeUpdated.getDescription());
        }
        if (tagTobeUpdated.getStatus() != null) {
            tag.setStatus(tagTobeUpdated.getStatus());
        }
        tagMapper.updateTag(tag);
        return tag;
    }
}
