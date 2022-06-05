package com.example.bank_kill.service;

import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.model.KillGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Repository
public interface KillGoodsService extends IService<KillGoods> {

    KillGoods selectByGoodId(Integer goodId) throws BankException;

    Integer getStock(Integer goodId);

}
