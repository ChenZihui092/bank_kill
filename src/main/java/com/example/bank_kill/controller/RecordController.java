package com.example.bank_kill.controller;


import com.example.bank_kill.Dto.RecordDto;
import com.example.bank_kill.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping(value = "/create")
    public Map<String, Object> create(
            RecordDto recordDto
    ) {
        return recordService.create(recordDto);
    }

    @PostMapping(value = "/delete")
    public Map<String, Object> delete(
            Integer recordId
    ) {
        return recordService.delete(recordId);
    }

    @PostMapping(value = "/update")
    public Map<String, Object> update(
            Integer recordId,
            RecordDto recordDto
    ) {
        return recordService.update(recordId, recordDto);
    }

    @GetMapping(value = "/getAllRecordInfo")
    public Map<String, Object> getAllRecordInfo(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "null") String searchUserId

    ) {
        return recordService.getAllRecordInfo(pageNum, pageSize, searchUserId);
    }

}

