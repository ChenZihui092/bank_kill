package com.example.bank_kill.controller;


import com.example.bank_kill.Dto.TempOrderDto;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.service.ApplyRecordService;
import com.example.bank_kill.service.KillGoodsService;
import com.example.bank_kill.util.BaseResponsePackageUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@RestController
@RequestMapping("/applyRecord")
public class ApplyRecordController {

    @Autowired
    private ApplyRecordService applyRecordService;

    @Autowired
    private KillGoodsService killGoodsService;

    RateLimiter rateLimiter = RateLimiter.create(100, 1, TimeUnit.SECONDS);

    @RequestMapping(value = "/createTemp/{goodId}",method = RequestMethod.POST)
    public Map<String,Object> createTempOrder(
            HttpServletRequest request,
            @PathVariable(name = "goodId") Integer goodId
    ){
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS))
            throw new BankException("下单失败，限流");
        if(killGoodsService.getStock(goodId)<=0) throw new BankException("已售罄");
        TempOrderDto tempOrder = applyRecordService.createTempOrder(request.getSession(), goodId);
        return BaseResponsePackageUtil.baseData(
                ImmutableMap.of(
                        "msg","请支付",
                        "res",tempOrder
                ));
    }

}

