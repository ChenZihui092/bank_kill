package com.example.bank_kill.service.impl;

import com.example.bank_kill.Dto.OrderDto;
import com.example.bank_kill.Dto.OrderMsg;
import com.example.bank_kill.Dto.TempOrderDto;
import com.example.bank_kill.constant.MqConstant;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.mapper.GoodsMapper;
import com.example.bank_kill.model.Goods;
import com.example.bank_kill.model.Orders;
import com.example.bank_kill.mapper.OrdersMapper;
import com.example.bank_kill.model.User;
import com.example.bank_kill.service.GoodsService;
import com.example.bank_kill.service.KillGoodsService;
import com.example.bank_kill.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bank_kill.util.CacheConstantUtil;
import com.example.bank_kill.util.CacheUtil;
import com.example.bank_kill.util.SessionUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private KillGoodsService killGoodsService;

    @Override
    public boolean doVerfy(String hash) {
        if (!redisTemplate.hasKey(CacheUtil.generateKey(CacheConstantUtil.TEMP_ORDER,hash))) return false;
        return true;
    }


    /**
     * 需要判断该用户是否重复购买，redis中找到购买该产品的用户集合，判断当前用户是否在集合中
     * 先从缓存中读取产品库存，如果大于0表示还有库存，将库存-1
     * @param hash
     * @return
     */
    @Override
    public boolean isSuccess(String hash,Integer userId) {
        TempOrderDto tempOrderDto = (TempOrderDto) redisTemplate.opsForValue().get(CacheUtil.generateKey(CacheConstantUtil.TEMP_ORDER,hash));
        Integer stock = killGoodsService.getStock(tempOrderDto.getGoodId());
        OrderDto orderDto = new OrderDto(tempOrderDto.getGoodId(),userId);
        if (redisTemplate.opsForSet().isMember(CacheUtil.generateKey(CacheConstantUtil.ORDER_SET,tempOrderDto.getGoodId().toString()),orderDto))
            throw new BankException("请勿重复购买");
        if(stock>0){
            redisTemplate.opsForValue().increment(CacheUtil.generateKey(CacheConstantUtil.GOOD_STOCK,tempOrderDto.getGoodId().toString()),-1);
            redisTemplate.opsForSet().add(CacheUtil.generateKey(CacheConstantUtil.ORDER_SET,tempOrderDto.getGoodId().toString()),orderDto);
            /**
             * 购买成功发送消息到`购买成功`的消息队列，更新applyRecord表
             */
            amqpTemplate.convertAndSend(MqConstant.EX_DIRECT,"kill.success",
                    new OrderMsg(tempOrderDto.getGoodId(),userId,tempOrderDto.getHashKey(),tempOrderDto.getCreateTime()));
            return true;
        }
        /**
         * 库存为0，发送消息到购买失败队列
         */
        amqpTemplate.convertAndSend(MqConstant.EX_DIRECT,"kill.fill",
                new OrderMsg(tempOrderDto.getGoodId(),userId,tempOrderDto.getHashKey(),tempOrderDto.getCreateTime()));
        return false;
    }
}
