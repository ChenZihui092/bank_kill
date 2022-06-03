package com.example.bank_kill.service;

import com.example.bank_kill.Dto.RecordDto;
import com.example.bank_kill.model.Record;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
public interface RecordService extends IService<Record> {

    Map<String, Object> create(RecordDto recordDto);

    Map<String, Object> delete(Integer recordId);

    Map<String, Object> update(Integer recordId, RecordDto recordDto);

    Map<String, Object> getAllRecordInfo(Integer pageNum, Integer pageSize, String searchUserId);
}
