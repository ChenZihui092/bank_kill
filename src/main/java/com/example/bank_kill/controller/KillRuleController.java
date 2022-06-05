package com.example.bank_kill.controller;


import com.example.bank_kill.Dto.KillRuleDto;
import com.example.bank_kill.constant.ResponseConstant;
import com.example.bank_kill.model.KillRule;
import com.example.bank_kill.service.KillRuleService;
import com.example.bank_kill.util.BaseResponsePackageUtil;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 秒杀规则表 前端控制器
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@RestController
@RequestMapping("/killRule")
public class KillRuleController {
    private static final Logger logger = LoggerFactory.getLogger(KillRuleController.class);

    @Autowired
    private KillRuleService killRuleService;
    @RequestMapping(value = "view/{rule_id}",method = RequestMethod.GET)
    public Map<String,Object> viewRule(@PathVariable("rule_id") Integer ruleId){
        KillRuleDto  killRuleDto=killRuleService.selectById(ruleId);
        logger.warn("查看规则 {}",killRuleDto);
        return BaseResponsePackageUtil.baseData(killRuleDto);
    }

    @RequestMapping(value = "add/{good_id}",method = RequestMethod.POST)
    private Map<String,Object> addRule(
        @PathVariable(name = "good_id") int goodId,
        @RequestBody KillRuleDto ruleDto
    ){
        KillRule killRule = killRuleService.addRule(goodId, ruleDto);
        return BaseResponsePackageUtil.baseData(killRule);
    }

}

