package com.example.bank_kill.service.impl;

import com.example.bank_kill.Dto.KillRuleDto;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.model.KillRule;
import com.example.bank_kill.mapper.KillRuleMapper;
import com.example.bank_kill.service.KillRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀规则表 服务实现类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Service
public class KillRuleServiceImpl extends ServiceImpl<KillRuleMapper, KillRule> implements KillRuleService {

//    @Autowired
//    private KillRuleMapper killRuleMapper;
//    @Override
//    public void addKillRule(KillRuleDto killRuleDto) throws BankException {
//
//    }
}
