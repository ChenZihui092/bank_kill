package com.example.bank_kill.config;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bank_kill.constant.ApplyResultConstant;
import com.example.bank_kill.mapper.ApplyRecordMapper;
import com.example.bank_kill.model.ApplyRecord;
import com.example.bank_kill.util.CacheConstantUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private ApplyRecordMapper applyRecordMapper;

    public RedisKeyExpirationListener(@Qualifier("redis_key_overdue") RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {

        String orderCode = message.toString();
        System.out.println(orderCode);
        if (!StringUtils.isBlank(orderCode)) {
            if(orderCode.contains(CacheConstantUtil.TEMP_ORDER)){
                String[] strings = orderCode.split(":");
                UpdateWrapper<ApplyRecord> updateWrapper = new UpdateWrapper<>();
                String hash = strings[strings.length-1];
                String s = new String(Base64.getDecoder().decode(hash)) ;
                String[] num = s.split("_");
                updateWrapper.eq("record_id",Integer.valueOf(num[num.length-2])).set("apply_result", ApplyResultConstant.OVERDUE);
                applyRecordMapper.update(null,updateWrapper);
            }
        }
        System.out.println("过期的订单号是: " + orderCode);
    }

}
