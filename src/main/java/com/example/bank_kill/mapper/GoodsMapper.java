package com.example.bank_kill.mapper;

import com.example.bank_kill.model.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 产品库存	 Mapper 接口
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
    Integer checkTime(Integer goodId, String time);
}
