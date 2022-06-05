package com.example.bank_kill.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class KillRuleDto {
    private Integer ruleId ;
    private Integer limitAge ;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date limitStartDate;
    private Integer limitOverdueTime ;
    private Integer limitOverdueAmount;
    private Integer limitOverdueFrequency ;
    private String limitIsUnemployment;
    private String limitIsBlack ;
    private Boolean isDelete  ;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime   ;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime  ;
}
