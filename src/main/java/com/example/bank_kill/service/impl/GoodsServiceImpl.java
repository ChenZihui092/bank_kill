package com.example.bank_kill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bank_kill.Dto.GoodsDto;
import com.example.bank_kill.Dto.KillGoodsDto;
import com.example.bank_kill.Dto.KillRuleDto;
import com.example.bank_kill.constant.EmployConstant;
import com.example.bank_kill.constant.LimitConstant;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.mapper.KillGoodsMapper;
import com.example.bank_kill.mapper.KillRuleMapper;
import com.example.bank_kill.model.Goods;
import com.example.bank_kill.mapper.GoodsMapper;
import com.example.bank_kill.model.KillGoods;
import com.example.bank_kill.model.KillRule;
import com.example.bank_kill.model.User;
import com.example.bank_kill.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bank_kill.service.KillGoodsService;
import com.example.bank_kill.service.KillRuleService;
import com.example.bank_kill.util.BaseResponsePackageUtil;
import com.example.bank_kill.util.CacheConstantUtil;
import com.example.bank_kill.util.CacheUtil;
import com.example.bank_kill.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 产品库存	 服务实现类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    private final static Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private KillRuleMapper killRuleMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private KillGoodsMapper killGoodsMapper;
    @Autowired
    private KillGoodsService killGoodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private KillRuleService killRuleService;

    /**
     * 创建商品
     * 必填项：goodName,startTime,endTime,goodStock,goodKillPrice,每次 创建一个商品必须填写规则
     */
    @Override
    public void addGood(GoodsDto goodsDto) throws BankException {
        if (goodsDto.getStartTime() == null || goodsDto.getEndTime() == null || goodsDto.getGoodStock() == null || goodsDto.getGoodPrice() == null || goodsDto.getGoodPrice() < 0
                || goodsDto.getGoodKillPrice() == null || goodsDto.getGoodKillPrice() < 0)
            throw new BankException("请查看日期、价格、库存是否填写规范");
        /**省略set*/
        Goods goods = new Goods();
        goods.setCreateTime(new Date());
        goods.setGoodImg(goodsDto.getGoodImg());
        goods.setGoodsDetail(goodsDto.getGoodsDetail());
        goods.setGoodStock(goodsDto.getGoodStock());
        goods.setGoodName(goodsDto.getGoodName());
        goods.setEndTime(goodsDto.getEndTime());
        goods.setStartTime(goodsDto.getStartTime());
        goods.setGoodKillPrice(goodsDto.getGoodKillPrice());
        goods.setGoodPrice(goodsDto.getGoodPrice());
        goods.setIsdelete(false);
        goodsMapper.insert(goods);
        Integer goodId = goods.getGoodId();
        KillGoods killGoods = new KillGoods();
        killGoods.setGoodName(goodsDto.getGoodName());
        killGoods.setGoodStock(goodsDto.getGoodStock());
        killGoods.setGoodPrice(goodsDto.getGoodKillPrice());
        killGoods.setGoodId(goodId);
        killGoodsMapper.insert(killGoods);

        redisTemplate.opsForValue().set(CacheUtil.getCacheKey(CacheConstantUtil.KILL_GOODS, goodId + ""), killGoods, 1, TimeUnit.DAYS);

    }

    @Override
    public void deleteGood(Integer goodId) throws BankException {
        if (goodId == null || goodId == 0) throw new BankException("请输入正确的goodId");
        Goods goods = goodsMapper.selectById(goodId);
        if (goods == null) throw new BankException("没有找到对应的商品，请输入正确的goodId");
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("good_id", goodId).set("isDelete", 1);
        goodsMapper.update(null, updateWrapper);

        logger.warn("delete goods , goodId is {}", goodId);
        UpdateWrapper<KillGoods> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.eq("good_id", goodId).set("isDelete", 1);
        killGoodsMapper.update(null, updateWrapper1);
        logger.warn("delete killGood");


    }

    /**
     * 修改商品，
     * <p>
     * 用户上传需要修改的部分，系统对东西进行判空，if！=null 表示需要进行修改
     */
    @Override
    public void updateGood(GoodsDto goodsDto) throws BankException {
        if (goodsDto.getGoodsId() == null) throw new BankException("goodId不能为空");
        Goods goodsOld = goodsMapper.selectById(goodsDto.getGoodsId());
        if (goodsOld == null) throw new BankException("没有在数据库找到对应的商品，请输入正确的goodId");
        Goods goodsNew = new Goods();
        goodsNew.setGoodName(goodsDto.getGoodName());
        goodsNew.setGoodImg(goodsDto.getGoodImg());
        goodsNew.setGoodsDetail(goodsDto.getGoodsDetail());
        goodsNew.setStartTime(goodsDto.getStartTime());
        goodsNew.setEndTime(goodsDto.getEndTime());
        goodsNew.setGoodStock(goodsDto.getGoodStock());
        goodsNew.setGoodPrice(goodsDto.getGoodPrice());
        goodsNew.setGoodKillPrice(goodsDto.getGoodKillPrice());
        goodsNew.setIsdelete(goodsDto.getIsDelete());
        goodsNew.setModifyTime(new Date());
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_id", goodsDto.getGoodsId());
        Integer res = goodsMapper.update(goodsNew, queryWrapper);
        logger.warn("正在更新goods表格{}", res);

        KillGoods killGoodsOld = killGoodsService.selectByGoodId(goodsDto.getGoodsId());
        if (killGoodsOld == null) throw new BankException("没有在kill_goods表里找到对应的数据");
        KillGoods killGoodsNew = new KillGoods();
        killGoodsNew.setGoodStock(goodsDto.getGoodStock());
        killGoodsNew.setGoodName(goodsDto.getGoodName());
        killGoodsNew.setIsDelete(goodsDto.getIsDelete());
        killGoodsNew.setKillGoodId(killGoodsOld.getKillGoodId());
        killGoodsNew.setGoodId(killGoodsOld.getGoodId());
        killGoodsNew.setGoodPrice(goodsDto.getGoodPrice());
        QueryWrapper<KillGoods> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("kill_good_id",killGoodsOld.getKillGoodId());
        killGoodsMapper.update(killGoodsNew,queryWrapper1);
        logger.warn("正在更新kill_goods 表格");

    }


    /**
     * 用户查看对应的商品
     * 在killGood里查
     */
    @Override
    public KillGoodsDto selectById(Integer goodId) throws BankException {
        if (goodId == null || goodId <= 0) throw new BankException("请正确填写goodId");
        KillGoods killGoods = killGoodsService.selectByGoodId(goodId);
        if (killGoods == null) throw new BankException("没有找到对应的商品");
        KillGoodsDto killGoodsDto = new KillGoodsDto();
        killGoodsDto.setGoodStock(killGoods.getGoodStock());
        killGoodsDto.setGoodName(killGoods.getGoodName());
        killGoodsDto.setKillGoodId(killGoods.getKillGoodId());
        killGoodsDto.setGoodId(killGoods.getGoodId());
        killGoodsDto.setIsDelete(killGoods.getIsDelete());
        return killGoodsDto;
    }

    @Override
    public boolean isOkToCreateOrder(HttpSession session, Integer goodId) {
        User user = SessionUtil.getUserFromSession(session);
        KillRule detail;
        if ((detail = (KillRule) redisTemplate.opsForValue().get(CacheUtil.generateKey(CacheConstantUtil.GOOD_RULE, goodId.toString()))) == null) {
            detail = killRuleService.detail(goodId);
            redisTemplate.opsForValue().set(CacheUtil.generateKey(CacheConstantUtil.GOOD_RULE, goodId.toString()), detail, 1, TimeUnit.DAYS);
        }
        if (!checkIsOk(user, detail)) {
            return false;
        }
        return true;
    }


    public boolean checkIsOk(User user,KillRule rule){
        if(user.getAge()<rule.getLimiteAge()){
            System.out.println("===================="+"年龄限制"+"==================");
            return false;
        }
        if(user.getIsblack()&&rule.getLimitIsBlack().equals(LimitConstant.LIMITED)){
            System.out.println("===================="+"失信人"+"==================");
            return false;
        }
        if(user.getStatus().equals(EmployConstant.UNEMPLOYED)&&rule.getLimitIsUnemployment().equals(LimitConstant.LIMITED)){
            System.out.println("===================="+"失业"+"==================");
            return false;
        }
        if(killRuleMapper.checkUserQualify(user.getUserId(),rule)>rule.getLimitOverdueFrequency()){
            System.out.println("===================="+"欠款次数太多"+"==================");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkTime(Integer goodId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = format.format(new Date(System.currentTimeMillis()));
        int cnt = goodsMapper.checkTime(goodId, timeNow);
        if (cnt >= 1) return false;
        return true;
    }

//    /**
//     * 缓存中没有库存信息，从MySQL中查询
//     * @param goodId
//     * @return
//     */
//    public Integer getStock(Integer goodId) {
//        QueryWrapper<KillGoods> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("good_stock").eq("good_Id",goodId);
//        return killGoodsMapper.selectOne(queryWrapper).getGoodStock();
//    }
//
//    @Override
//    public int getStockFromRedis(Integer goodId) {
//        Integer stock = (Integer)redisTemplate.opsForValue().get(CacheUtil.generateCacheKey(CacheConstantUtil.GOOD_STOCK,goodId.toString()));
//        if(stock==null){
//            stock = getStock(goodId);
//        }
//        return stock;
//    }


}
