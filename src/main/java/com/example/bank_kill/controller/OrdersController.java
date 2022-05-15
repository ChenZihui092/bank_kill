package com.example.bank_kill.controller;


import com.example.bank_kill.Dto.TempOrderDto;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.model.User;
import com.example.bank_kill.service.OrdersService;
import com.example.bank_kill.util.BaseResponsePackageUtil;
import com.example.bank_kill.util.SessionUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.RateLimiter;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AmqpTemplate amqpTemplate;


    RateLimiter rateLimiter = RateLimiter.create(100);

    @RequestMapping(value = "payOrder/{hash}",method = RequestMethod.POST)
    public Map<String,Object> payOrder(
            @PathVariable(name = "hash") String hash,
            HttpServletRequest request
    ){
        if (!rateLimiter.tryAcquire(100,TimeUnit.SECONDS))
            throw new BankException("系统繁忙！请稍后再试");
        if(!ordersService.doVerfy(hash)) return BaseResponsePackageUtil.errorMessage("订单号已过期或不存在");
        User user = SessionUtil.getUserFromSession(request.getSession());
        /**
         * 如果该用户之前没有购买过该产品，并且库存充足，就
         */
        if(ordersService.isSuccess(hash,user.getUserId()))
            return BaseResponsePackageUtil.succeedMessage("购买成功");
        return BaseResponsePackageUtil.succeedMessage("抱歉！手速慢了");
    }

}

