package com.example.bank_kill.controller;


import com.example.bank_kill.Dto.KillRuleDto;
import com.example.bank_kill.service.KillRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

//    @Autowired
//    private KillRuleService killRuleService;
//    @RequestMapping(value = "create",method = RequestMethod.POST)
//    public Map<String,Object> create(@RequestBody KillRuleDto killRuleDto){
//        killRuleService
//    }

}

