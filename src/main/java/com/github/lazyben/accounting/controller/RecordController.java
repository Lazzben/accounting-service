package com.github.lazyben.accounting.controller;

import com.github.lazyben.accounting.converter.c2s.RecordC2SConverter;
import com.github.lazyben.accounting.exception.InvalidParameterException;
import com.github.lazyben.accounting.manager.RecordManager;
import com.github.lazyben.accounting.model.service.Record;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0/record")
public class RecordController {
    private final RecordManager recordManager;
    private final RecordC2SConverter recordC2SConverter;

    @Autowired
    public RecordController(RecordManager recordManager, RecordC2SConverter recordC2SConverter) {
        this.recordManager = recordManager;
        this.recordC2SConverter = recordC2SConverter;
    }

    /**
     * @api {post} /record 创建记录
     * @apiName createRecord
     * @apiGroup Record
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiBody {Long} userId 用户id
     * @apiBody {BigDecimal} amount 金额
     * @apiBody {String} category 支出或收入
     * @apiBody {String} [note] 备注
     * @apiBody {List} tags 标签
     * @apiParamExample {json} Request-Example:
     * {
     * "userId": 1,
     * "amount": 64.50,
     * "note" : "买衣服和买书",
     * "category": "outcome",
     * "tags":[
     * {
     * "id": 1
     * },
     * {
     * "id": 3
     * }
     * ]
     * }
     * @apiSuccessExample Success-Response:
     * {
     * "id": 2,
     * "userId": 1,
     * "amount": 64.50,
     * "note": "买衣服和买书",
     * "category": "outcome",
     * "tags": [
     * {
     * "id": 1,
     * "userId": 1,
     * "status": "ENABLE",
     * "description": "shopping"
     * },
     * {
     * "id": 3,
     * "userId": 1,
     * "status": "ENABLE",
     * "description": "read"
     * }
     * ]
     * }
     * @apiError 400 Bad Request userId为空或非法，tags为空或非法，amount为空或非法，category为空或非法
     * @apiError 401 Unauthorized 用户未登录
     * @apiErrorExample {json} Error-Response:
     * {
     * "bizErrorCode": "INVALID_PARAMETER",
     * "message": "Category is null or invalid"
     * }
     */
    @PostMapping(produces = "application/json", consumes = "application/json")
    public Record createRecord(@RequestBody Record record) {
        checkRecord(record);
        val recordCommon = recordC2SConverter.reverse().convert(record);
        return recordC2SConverter.convert(recordManager.createRecord(recordCommon));
    }

    /**
     * @api {get} /record/:id 获取记录
     * @apiName getRecordById
     * @apiGroup Record
     * @apiHeader {String} Content-Type application/json
     * @apiParam {Long} id 记录id
     * @apiSuccessExample Success-Response:
     * {
     * "id": 2,
     * "userId": 1,
     * "amount": 64.50,
     * "note": "买衣服和买书",
     * "category": "outcome",
     * "tags": [
     * {
     * "id": 1,
     * "userId": 1,
     * "status": "ENABLE",
     * "description": "shopping"
     * },
     * {
     * "id": 3,
     * "userId": 1,
     * "status": "ENABLE",
     * "description": "read"
     * }
     * ]
     * }
     * @apiError 400 Bad Request recordId为空或非法
     * @apiError 404 Not Found 记录不存在
     * @apiError 401 Unauthorized 用户未登录
     * @apiErrorExample {json} Error-Response:
     * {
     * "bizErrorCode": "RESOURCE_NOT_FOUND",
     * "message": "record 5 is not found"
     * }
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public Record getRecordByRecordId(@PathVariable("id") Long recordId) {
        if (recordId == null || recordId <= 0) {
            throw new InvalidParameterException("recordId is null or invalid");
        }
        return recordC2SConverter.convert(recordManager.getRecordByRecordId(recordId));
    }

    /**
     * @api {put} /record/:id 更新记录
     * @apiName updateRecord
     * @apiGroup Record
     * @apiParam id recordId
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiBody {Long} [userId] 用户id
     * @apiBody {BigDecimal} [amount] 金额
     * @apiBody {String} [category] 支出或收入
     * @apiBody {String} [note] 备注
     * @apiBody {List} [tags] 标签
     * @apiParamExample {json} Request-Example:
     * {
     * "userId": 1,
     * "amount": 107,
     * "note": "买衣服",
     * "category": "income",
     * "tags": [
     * {
     * "id": 3
     * },
     * {
     * "id": 1
     * }
     * ]
     * }
     * @apiSuccessExample Success-Response:
     * {
     * "id": 1,
     * "userId": 1,
     * "amount": 107.00,
     * "note": "买衣服",
     * "category": "income",
     * "tags": [
     * {
     * "id": 1,
     * "userId": 1,
     * "status": "ENABLE",
     * "description": "shopping"
     * },
     * {
     * "id": 3,
     * "userId": 1,
     * "status": "ENABLE",
     * "description": "read"
     * }
     * ]
     * }
     * @apiError 400 Bad Request userId非法，tags非法，amount非法，category非法
     * @apiError 404 Not Found 该record未找到
     * @apiError 401 Unauthorized 用户未登录
     * @apiErrorExample {json} Error-Response:
     * {
     * "bizErrorCode": "INVALID_PARAMETER",
     * "message": "some tags do not exits"
     * }
     */
    @PutMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public Record updateRecord(@PathVariable("id") Long recordId, @RequestBody Record record) {
        if (recordId == null || recordId <= 0) {
            throw new InvalidParameterException("recordId is null or invalid");
        }
        checkAndCleanUpdateRecord(record);
        val recordCommon = recordC2SConverter.reverse().convert(record);
        return recordC2SConverter.convert(recordManager.updateRecord(recordId, recordCommon));
    }

    private void checkAndCleanUpdateRecord(Record record) {
        if (record.getAmount() != null && record.getAmount().doubleValue() <= 0) {
            throw new InvalidParameterException("Mount is invalid");
        }
        if (record.getCategory() != null &&
                !(record.getCategory().equals("outcome") || record.getCategory().equals("income"))) {
            throw new InvalidParameterException("Category is invalid");
        }
        record.setId(null);
        record.setUserId(null);
    }

    private void checkRecord(Record record) {
        if (record.getAmount() == null || record.getAmount().doubleValue() <= 0) {
            throw new InvalidParameterException("Mount is null or invalid");
        }
        if (record.getCategory() == null ||
                !(record.getCategory().equals("outcome") || record.getCategory().equals("income"))) {
            throw new InvalidParameterException("Category is null or invalid");
        }
        if (record.getUserId() == null || record.getUserId() <= 0) {
            throw new InvalidParameterException("User id is null or invalid");
        }
        if (record.getTags() == null) {
            throw new InvalidParameterException("Tag is null");
        }
    }
}
