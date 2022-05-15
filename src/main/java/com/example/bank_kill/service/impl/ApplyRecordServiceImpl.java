package com.example.bank_kill.service.impl;

import com.example.bank_kill.Dto.TempOrderDto;
import com.example.bank_kill.constant.ApplyResultConstant;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.mapper.GoodsMapper;
import com.example.bank_kill.mapper.KillGoodsMapper;
import com.example.bank_kill.model.ApplyRecord;
import com.example.bank_kill.mapper.ApplyRecordMapper;
import com.example.bank_kill.model.Goods;
import com.example.bank_kill.model.KillGoods;
import com.example.bank_kill.model.User;
import com.example.bank_kill.service.ApplyRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bank_kill.service.GoodsService;
import com.example.bank_kill.util.CacheConstantUtil;
import com.example.bank_kill.util.CacheUtil;
import com.example.bank_kill.util.SessionUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
public class ApplyRecordServiceImpl extends ServiceImpl<ApplyRecordMapper, ApplyRecord> implements ApplyRecordService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private KillGoodsMapper killGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ApplyRecordMapper applyRecordMapper;


    /**
     * 1.首先查看库存是否为空，如果为空，直接返回拒绝
     * 2.生成ApplyRecord记录，result设置为“已下单”
     * 3.生成订单信息返回，同时将订单编号存入redis，过期时间为15min
     * 4.配置redis过期监听器，对于15min还未付款的订单，更新ApplyRecord表中的result为“未付款”
     * @param session
     * @param goodId
     * @return
     */
    @Override
    @Transactional
    public TempOrderDto createTempOrder(HttpSession session, Integer goodId) {
        if(!goodsService.isOkToCreateOrder(session,goodId)) throw new BankException("您不满足此次秒杀条件，请阅读规则");
        if(!goodsService.checkTime(goodId)) throw new BankException("当前不在秒杀时间内!");
        KillGoods goods = killGoodsMapper.selectById(goodId);
        User user = SessionUtil.getUserFromSession(session);
        ApplyRecord applyRecord = new ApplyRecord();
        applyRecord.setUserId(user.getUserId());
        applyRecord.setUserName(user.getUserName());
        applyRecord.setGoodId(goodId);
        applyRecord.setGoodName(goods.getGoodName());
        applyRecord.setApplyResult(ApplyResultConstant.ORDER_ALREADY);
        applyRecord.setIsdelete(false);
        applyRecord.setModifyTime(new Date(System.currentTimeMillis()));
        applyRecord.setApplyTime(new Date(System.currentTimeMillis()));
        applyRecord.setCreateTime(new Date(System.currentTimeMillis()));
        applyRecordMapper.insert(applyRecord);
        //创建订单编号的Hash码，支付成功后返回这个Hash码，代表支付成功
        String hashKey = CacheConstantUtil.SALT+"_"+applyRecord.getRecordId()+"_"+user.getUserId();
        hashKey = Base64.getEncoder().encodeToString(hashKey.getBytes(StandardCharsets.UTF_8));
        TempOrderDto tempOrderDto = new TempOrderDto();
        tempOrderDto.setAmount(goods.getGoodPrice());
        tempOrderDto.setGoodId(goodId);
        tempOrderDto.setGoodName(goods.getGoodName());
        tempOrderDto.setCreateTime(new Date(System.currentTimeMillis()));
        tempOrderDto.setHashKey(hashKey);
        redisTemplate.opsForValue().set(CacheUtil.generateKey(CacheConstantUtil.TEMP_ORDER,hashKey)
                ,tempOrderDto,15, TimeUnit.MINUTES);
        return tempOrderDto;
    }

}
