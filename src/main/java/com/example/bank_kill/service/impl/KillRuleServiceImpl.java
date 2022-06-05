package com.example.bank_kill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bank_kill.Dto.KillRuleDto;
import com.example.bank_kill.controller.KillRuleController;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.model.KillRule;
import com.example.bank_kill.mapper.KillRuleMapper;
import com.example.bank_kill.service.KillRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    @Autowired
    private KillRuleMapper killRuleMapper;

    @Override
    public KillRuleDto selectById(Integer ruleId) throws BankException {
        if (ruleId == null || ruleId <= 0) throw new BankException("ruleId请输入正确");
        KillRule killRule = killRuleMapper.selectById(ruleId);
        if (killRule == null) throw new BankException("没有找到对应的规则");
        KillRuleDto killRuleDto = new KillRuleDto();
        killRuleDto.setRuleId(killRule.getRuleId());
        killRuleDto.setLimitAge(killRule.getLimiteAge());
        killRuleDto.setLimitStartDate(killRule.getLimitStartDate());
        killRuleDto.setLimitOverdueTime(killRule.getLimitOverdueTime());
        killRuleDto.setLimitOverdueAmount(killRule.getLimitOverdueAmount());
        killRuleDto.setLimitOverdueFrequency(killRule.getLimitOverdueFrequency());
        killRuleDto.setLimitIsUnemployment(killRule.getLimitIsUnemployment());
        killRuleDto.setLimitIsBlack(killRule.getLimitIsBlack());
        killRuleDto.setIsDelete(killRule.getIsdelete());
        killRuleDto.setCreateTime(killRule.getCreateTime());
        killRuleDto.setModifyTime(killRule.getModifyTime());
        return killRuleDto;
    }

    public KillRule detail(Integer goodId) {
        QueryWrapper<KillRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_id", goodId);
        KillRule killRule = killRuleMapper.selectOne(queryWrapper);
        return killRule;
    }

    @Override
    public KillRule addRule(Integer goodId, KillRuleDto killRuleDto) {
        KillRule killRule = new KillRule();
        killRule.setGoodId(goodId);
        killRule.setCreateTime(new Date());
        killRule.setIsdelete(false);
        killRule.setLimiteAge(killRuleDto.getLimitAge());
        killRule.setLimitIsBlack(killRuleDto.getLimitIsBlack());
        killRule.setLimitStartDate(killRuleDto.getLimitStartDate());
        killRule.setLimitOverdueAmount(killRuleDto.getLimitOverdueAmount());
        killRule.setLimitOverdueTime(killRuleDto.getLimitOverdueTime());
        killRule.setLimitOverdueFrequency(killRuleDto.getLimitOverdueFrequency());
        killRule.setLimitIsUnemployment(killRuleDto.getLimitIsUnemployment());
        killRuleMapper.insert(killRule);
        return killRule;
    }
}
