package com.example.bank_kill.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产品库存	
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "good_id", type = IdType.AUTO)
    private Integer goodId;

    private Integer ruleId;

    private Integer killGoodId;

    private String goodName;

    @TableField("good_Img")
    private String goodImg;

    private String goodsDetail;

    private Date startTime;

    private Date endTime;

    private KillRule killRule;

    /**
     * 库存	            
     */
    private Integer goodStock;

    private Integer goodPrice;

    /**
     * 秒杀价格	            
     */
    private Integer goodKillPrice;

    @TableField("isDelete")
    private Boolean isdelete;

    private Date createTime;

    private Date modifyTime;


}
