package com.example.bank_kill.Dto;

import lombok.Data;

@Data
public class KillGoodsDto {
    private Integer killGoodId;
    private Integer goodId;
    private Integer goodStock;
    private String goodName;
    private Boolean isDelete;
}
