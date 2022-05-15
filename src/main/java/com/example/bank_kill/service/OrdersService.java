package com.example.bank_kill.service;

import com.example.bank_kill.Dto.TempOrderDto;
import com.example.bank_kill.model.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
public interface OrdersService extends IService<Orders> {
    boolean doVerfy(String hash);
    boolean isSuccess(String hash,Integer userId);
}
