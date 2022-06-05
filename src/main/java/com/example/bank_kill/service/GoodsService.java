package com.example.bank_kill.service;

import com.example.bank_kill.Dto.GoodsDto;
import com.example.bank_kill.Dto.KillGoodsDto;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.model.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bank_kill.model.KillGoods;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 * 产品库存	 服务类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
public interface GoodsService extends IService<Goods> {

    void addGood(GoodsDto goodsDto) throws BankException;

    void deleteGood(Integer goodId) throws BankException;

    void updateGood(GoodsDto goodsDto) throws BankException;

    KillGoodsDto selectById(Integer goodId) throws BankException;

    boolean isOkToCreateOrder(HttpSession session, Integer goodId);

    boolean checkTime(Integer goodId);
//    int getStockFromRedis(Integer goodId);
}
