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
 * 
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    private Integer userId;

    private Integer amount;

    private Date overdueDate;

    private Integer overdueTime;

    @TableField("isBlack")
    private Boolean isblack;

    private Date createTime;

    private Date modifyTime;


}
