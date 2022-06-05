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
 * 秒杀规则表
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("kill_rule")
public class KillRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rule_id", type = IdType.AUTO)
    private Integer ruleId;

    private Integer goodId;

    private Integer limiteAge;

    /**
     * 逾期计算起始时间
     */
    private Date limitStartDate;

    /**
     * 逾期时长限制,限制时间内还款不算一次违规记录
     */
    private Integer limitOverdueTime;

    /**
     * 逾期金额限制
     */
    private Integer limitOverdueAmount;

    /**
     * 逾期次数限制，超过次数不允许参加秒杀
     */
    private Integer limitOverdueFrequency;

    /**
     * 是否限制用户失业/限制or不限制
     */
    private String limitIsUnemployment;

    private String limitIsBlack;

    @TableField("isDelete")
    private Boolean isdelete;

    private Date createTime;

    private Date modifyTime;


}
