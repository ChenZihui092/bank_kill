package com.example.bank_kill.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("kill_goods")
public class KillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "kill_good_id", type = IdType.AUTO)
    private Integer killGoodId;

    private Integer goodId;

    private Integer goodStock;

    private String goodName;

    private Integer goodPrice;

    @TableField("isDelete")
    private boolean isDelete;




}
