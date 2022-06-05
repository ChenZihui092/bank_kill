package com.example.bank_kill.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderMsg implements Serializable {

    private Integer goodId;
    private Integer userId;
    private String hashCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    public OrderMsg(Integer goodId, Integer userId, String hashCode, Date orderTime) {
        this.goodId = goodId;
        this.userId = userId;
        this.hashCode = hashCode;
        this.orderTime = orderTime;
    }
}
