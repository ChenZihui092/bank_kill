package com.example.bank_kill.service;

import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.model.KillGoods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
public interface KillGoodsService extends IService<KillGoods> {

    KillGoods selectByGoodId(Integer goodId) throws BankException;
}
