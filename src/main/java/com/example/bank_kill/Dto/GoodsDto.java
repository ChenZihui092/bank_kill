package com.example.bank_kill.Dto;



import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class GoodsDto {
    private Integer goodsId ;
    private String goodName;
    private String goodImg ;
    private String goodsDetail ;
    private Date startTime;
    private Date endTime ;
    private Integer goodStock ;

    private Integer goodPrice  ;
    private Integer goodKillPrice   ;
    private Boolean   isDelete  ;
    private Instant createTime  ;
    private Instant modifyTime  ;
    private KillRuleDto killRuleDto;
}
