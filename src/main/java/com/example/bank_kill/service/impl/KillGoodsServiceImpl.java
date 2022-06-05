package com.example.bank_kill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.model.KillGoods;
import com.example.bank_kill.mapper.KillGoodsMapper;
import com.example.bank_kill.service.KillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bank_kill.util.CacheConstantUtil;
import com.example.bank_kill.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Service
public class KillGoodsServiceImpl extends ServiceImpl<KillGoodsMapper, KillGoods> implements KillGoodsService {

    @Autowired
    private KillGoodsMapper killGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public KillGoods selectByGoodId(Integer goodId) throws BankException {
        QueryWrapper<KillGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_Id", goodId);
        KillGoods killGoods = killGoodsMapper.selectOne(queryWrapper);
        return killGoods;
    }

    public Integer getStockFromMySQL(Integer goodId) {
        QueryWrapper<KillGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("good_stock").eq("good_id", goodId);
        return killGoodsMapper.selectOne(queryWrapper).getGoodStock();
    }

    @Override
    public Integer getStock(Integer goodId) {
        Integer stock = (Integer) redisTemplate.opsForValue().get(CacheUtil.generateCacheKey(CacheConstantUtil.GOOD_STOCK, goodId.toString()));
        if (stock == null) {
            stock = getStockFromMySQL(goodId);
        }
        return stock;
    }


}
