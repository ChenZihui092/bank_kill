package com.example.bank_kill.Dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegDto implements Serializable {

    private final String account;
    private final String pwd;
    private final String userName;
    private final String tel;
    private final String idCard;
    private final String sex;
    private final int age;
    //个体或者团体
    private final String userType;
    private final String address;
    private final String status;
    private final Boolean isBlack;

}
