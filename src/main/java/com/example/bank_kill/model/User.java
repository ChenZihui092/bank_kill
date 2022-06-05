package com.example.bank_kill.model;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

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
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String account;

    private String pwd;

    private String userName;

    private String tel;

    @TableField("idCard")
    private String idcard;

    private String sex;

    private Integer age;

    private String userType;

    private String address;

    private String status;

    @TableField("isBlack")
    private Boolean isblack;

    @TableField("isDelete")
    @TableLogic
    private Boolean isdelete;

    private Date createDate;

    private Date modifyDate;


}
