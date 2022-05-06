package com.example.bank_kill.service.impl;

import com.example.bank_kill.model.Orders;
import com.example.bank_kill.mapper.OrdersMapper;
import com.example.bank_kill.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
