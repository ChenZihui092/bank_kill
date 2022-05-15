package com.example.bank_kill.mq;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bank_kill.Dto.OrderMsg;
import com.example.bank_kill.constant.ApplyResultConstant;
import com.example.bank_kill.mapper.ApplyRecordMapper;
import com.example.bank_kill.model.ApplyRecord;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RabbitListener
public class KillFillConsumer {

    @Autowired
    private ApplyRecordMapper applyRecordMapper;

    @RabbitHandler
    public void handler(@Payload OrderMsg orderMsg){
        UpdateWrapper<ApplyRecord> updateWrapper = new UpdateWrapper<>();
        Map<String,Object> params = new HashMap<>();
        params.put("good_id",orderMsg.getGoodId());
        params.put("user_id",orderMsg.getUserId());
        updateWrapper.allEq(params).set("apply_result", ApplyResultConstant.SORRY_UNSUCCESSFUL);
        applyRecordMapper.update(null,updateWrapper);
    }

}
