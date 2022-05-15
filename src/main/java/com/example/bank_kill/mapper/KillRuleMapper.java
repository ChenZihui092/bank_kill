package com.example.bank_kill.mapper;

import com.example.bank_kill.model.KillRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface KillRuleMapper extends BaseMapper<KillRule> {
    Integer checkUserQualify(int userId,KillRule rule);
}
