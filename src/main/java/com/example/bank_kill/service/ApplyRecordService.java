package com.example.bank_kill.service;

import com.example.bank_kill.Dto.TempOrderDto;
import com.example.bank_kill.model.ApplyRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
public interface ApplyRecordService extends IService<ApplyRecord> {
    TempOrderDto createTempOrder(HttpSession session, Integer goodId);
}
