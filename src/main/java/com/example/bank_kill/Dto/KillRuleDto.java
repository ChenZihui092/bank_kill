package com.example.bank_kill.Dto;

import lombok.Data;

import java.util.Date;
@Data
public class KillRuleDto {
    private Integer ruleId ;
    private Integer limitAge ;
    private Date limitStartDate;
    private Integer limitOverdueTime ;
    private Integer limitOverdueAmount;
    private Integer limitOverdueFrequency ;
    private String limitIsUnemployment;
    private String limitIsBlack ;
    private Boolean isDelete  ;
    private Date createTime   ;
    private Date modifyTime  ;
}
