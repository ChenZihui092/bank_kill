package com.example.bank_kill.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto implements Serializable {

    private final Integer goodId;
    private final Integer userId;

}
