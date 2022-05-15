package com.example.bank_kill.mq;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bank_kill.Dto.OrderMsg;
import com.example.bank_kill.constant.ApplyResultConstant;
import com.example.bank_kill.constant.MqConstant;
import com.example.bank_kill.mapper.ApplyRecordMapper;
import com.example.bank_kill.mapper.OrdersMapper;
import com.example.bank_kill.model.ApplyRecord;
import com.example.bank_kill.model.Orders;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Component
@RabbitListener(queues = MqConstant.KILL_SUCCESS_QUEUE)
public class KillSuccessConsumer {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private ApplyRecordMapper applyRecordMapper;

    @RabbitHandler
    @Transactional
    public void handleMsg(@Payload OrderMsg orderMsg){
        UpdateWrapper<ApplyRecord> updateWrapper = new UpdateWrapper<>();
        Map<String,Object> params = new HashMap<>();
        params.put("good_id",orderMsg.getGoodId());
        params.put("user_id",orderMsg.getUserId());
        updateWrapper.allEq(params).set("apply_result", ApplyResultConstant.ACCOMPLISH);
        applyRecordMapper.update(null,updateWrapper);
        Orders orders = new Orders();
        orders.setGoodId(orderMsg.getGoodId());
        orders.setHashCode(orderMsg.getHashCode());
        orders.setOderTime(orderMsg.getOrderTime());
        orders.setUserId(orderMsg.getUserId());
        ordersMapper.insert(orders);
    }
}
