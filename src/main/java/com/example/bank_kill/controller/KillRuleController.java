package com.example.bank_kill.controller;


import com.example.bank_kill.Dto.KillRuleDto;
import com.example.bank_kill.constant.ResponseConstant;
import com.example.bank_kill.service.KillRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "view{id}",method = RequestMethod.GET)
    public Map<String,Object> viewRule(@RequestParam("ruleId") Integer ruleId){
        KillRuleDto  killRuleDto=killRuleService.selectById(ruleId);
        logger.warn("查看规则 {}",killRuleDto);
        return ResponseConstant.V_UPDATE_SUCCESS;
    }

}

