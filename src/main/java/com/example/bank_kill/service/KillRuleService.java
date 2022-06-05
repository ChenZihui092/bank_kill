package com.example.bank_kill.service;

import com.example.bank_kill.Dto.KillRuleDto;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.model.KillRule;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 秒杀规则表 服务类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
public interface KillRuleService extends IService<KillRule> {

//    void addKillRule(KillRuleDto killRuleDto) throws BankException;
    KillRuleDto selectById(Integer ruleId) throws BankException;
    KillRule detail(Integer goodId);

    KillRule addRule(Integer goodId,KillRuleDto ruleDto);
}
