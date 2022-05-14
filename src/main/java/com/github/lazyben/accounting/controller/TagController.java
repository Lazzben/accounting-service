package com.github.lazyben.accounting.controller;

import com.github.lazyben.accounting.converter.c2s.TagC2SConverter;
import com.github.lazyben.accounting.converter.c2s.TagPagedResponseC2SConverter;
import com.github.lazyben.accounting.exception.InvalidParameterException;
import com.github.lazyben.accounting.manager.TagManager;
import com.github.lazyben.accounting.manager.UserInfoManager;
import com.github.lazyben.accounting.model.PagedResponse;
import com.github.lazyben.accounting.model.service.Tag;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0/tag")
public class TagController {
    private final TagManager tagManager;
    private final TagC2SConverter tagC2SConverter;
    private final UserInfoManager userInfoManager;
    private final TagPagedResponseC2SConverter pagedResponseC2SConverter;

    @Autowired
    public TagController(TagManager tagManager,
                         TagC2SConverter tagC2SConverter,
                         UserInfoManager userInfoManager,
                         TagPagedResponseC2SConverter pagedResponseC2SConverter) {
        this.tagManager = tagManager;
        this.tagC2SConverter = tagC2SConverter;
        this.userInfoManager = userInfoManager;
        this.pagedResponseC2SConverter = pagedResponseC2SConverter;
    }

    /**
     * @api {post} /tag 新建标签
     * @apiGroup Tag
     * @apiName createTag
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiBody {long} userId 用户id
     * @apiBody {String} description 标签的描述
     * @apiParamExample {json} Request-Example:
     * {
     * "user_id": 1,
     * "description": "吃饭"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "id": 1,
     * "user_id": 1,
     * "description": "吃饭",
     * "status": "ENABLE"
     * }
     * @apiError 400 Bad Request 用户已经有该标签，userId或description为空
     * @apiError 404 Not Found 该用户不存在
     * @apiError 401 Unauthorized 用户未登录
     * @apiErrorExample {json} Error-Response:
     * {
     * "bizErrorCode": "INVALID_PARAMETER",
     * "message": "Tag's description and userId must not be null"
     * }
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public Tag createTag(@RequestBody Tag newTag) {
        if (newTag == null || newTag.getDescription() == null || newTag.getUserId() == null) {
            throw new InvalidParameterException("Tag's description and userId must not be null");
        }
        return tagC2SConverter.convert(tagManager.createTag(newTag));
    }

    /**
     * @api {put} /tag/:id 更新标签
     * @apiGroup Tag
     * @apiName updateTag
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParam {Long} id 标签id
     * @apiBody {Long} userId 用户id
     * @apiBody {String} [status] 标签的状态
     * @apiBody {String} [description] 标签的描述
     * @apiParamExample {json} Request-Example:
     * {
     * "user_id": 1,
     * "status": "DELETED"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "id": 1,
     * "userId": 1,
     * "status": "DELETED",
     * "description": "girl"
     * }
     * @apiError 400 Bad Request tagId为空或非法，userId为空或非法，该标签不属于该id用户，标签没有任何更新
     * @apiError 404 Not Found 该id的标签不存在
     * @apiError 401 Unauthorized 用户未登录
     * @apiErrorExample {json} Error-Response:
     * {
     * "bizErrorCode": "INVALID_PARAMETER",
     * "message": "tag has nothing to change"
     * }
     */
    @PutMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public Tag updateTag(@PathVariable("id") Long tagId, @RequestBody Tag tag) {
        if (tagId == null || tagId <= 0) {
            throw new InvalidParameterException("The tagId is empty or invalid");
        }
        if (tag.getUserId() == null || tag.getUserId() <= 0L) {
            throw new InvalidParameterException("The userId is empty or invalid");
        }
        return tagC2SConverter.convert(tagManager.updateTag(tagId, tag));
    }

    /**
     * @api {get} /tag/:id 根据id获取标签
     * @apiGroup Tag
     * @apiName getTagById
     * @apiHeader {String} Content-Type application/json
     * @apiParam {Long} id 标签id
     * @apiSuccessExample {json} Success-Response:
     * {
     * "id": 1,
     * "userId": 1,
     * "status": "ENABLE",
     * "description": "house"
     * }
     * @apiError 400 Bad Request tagId为空或非法
     * @apiError 404 Not Found 该id的标签不存在
     * @apiError 401 Unauthorized 用户未登录
     * @apiErrorExample {json} Error-Response:
     * {
     * "bizErrorCode": "RESOURCE_NOT_FOUND",
     * "message": "tag 5 not found"
     * }
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public Tag getTagByTagId(@PathVariable("id") Long tagId) {
        if (tagId == null || tagId <= 0) {
            throw new InvalidParameterException("The tagId is empty or invalid");
        }
        return tagC2SConverter.convert(tagManager.getTagById(tagId));
    }

    /**
     * @api {get} /tag 分页获取标签
     * @apiGroup Tag
     * @apiName getTags
     * @apiHeader {String} Content-Type application/json
     * @apiQuery {int} pageNum 页码
     * @apiQuery {int} pageSize 页大小
     * @apiSuccessExample {json} Success-Response:
     * {
     * "totalPages": 2,
     * "totalElementSize": 3,
     * "pageNum": 1,
     * "pageSize": 2,
     * "hasNextPage": true,
     * "data": [
     * {
     * "id": 1,
     * "userId": 1,
     * "status": "ENABLE",
     * "description": "shopping"
     * },
     * {
     * "id": 2,
     * "userId": 1,
     * "status": "ENABLE",
     * "description": "house"
     * }
     * ]
     * }
     * @apiError 400 Bad Request 页码非法，页大小非法
     * @apiError 401 Unauthorized 用户未登录
     * @apiErrorExample {json} Error-Response:
     * {
     * "bizErrorCode": "INVALID_PARAMETER",
     * "message": "Max pageNum is 2, which is smaller than 5.Total tags 3"
     * }
     */
    @GetMapping(produces = "application/json")
    public PagedResponse<Tag> getTags(@RequestParam("pageNum") int pageNum,
                                      @RequestParam("pageSize") int pageSize) {
        if (pageNum <= 0 || pageSize <= 0) {
            throw new InvalidParameterException("PageNum or pageSize is invalid");
        }
        val username = (String) SecurityUtils.getSubject().getPrincipal();
        val user = userInfoManager.getUserInfoByUsername(username);
        return pagedResponseC2SConverter.convert(tagManager.getTags(user.getId(), pageNum, pageSize));
    }
}
