package com.example.bank_kill.controller;


import com.example.bank_kill.Dto.GoodsDto;
import com.example.bank_kill.Dto.KillGoodsDto;
import com.example.bank_kill.constant.ResponseConstant;
import com.example.bank_kill.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 产品库存	 前端控制器
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    private final static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    /**
     * 增加一个商品
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Map<String, Object> createGoods(@RequestBody GoodsDto goodsDto) {
        goodsService.addGood(goodsDto);
        logger.warn("创建一个商品 ");
        return ResponseConstant.V_ADD_SUCCESS;
    }

    @RequestMapping(value = "/delete{id}",method = RequestMethod.GET)
    public Map<String,Object> deleteGood(@RequestParam("goodId") Integer goodId){
        goodsService.deleteGood(goodId);
        logger.warn("删除商品");
        return ResponseConstant.V_UPDATE_SUCCESS;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Map<String,Object> updateGood(@RequestBody GoodsDto goodsDto){
        goodsService.updateGood(goodsDto);
        logger.warn("修改商品");
        return ResponseConstant.V_UPDATE_SUCCESS;
    }

    @RequestMapping(value = "view{goodId}",method = RequestMethod.GET)
    public Map<String,Object> viewGoods(@RequestParam("goodId") Integer goodID){

        KillGoodsDto killGoodsDto = goodsService.selectById(goodID);
        logger.warn("查看商品 {}",killGoodsDto);
        return ResponseConstant.V_CONTEST_REGISTER_SUCCESS;
    }



}

