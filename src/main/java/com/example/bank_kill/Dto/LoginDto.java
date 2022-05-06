package com.example.bank_kill.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    private final String account;
    private final String pwd;

}
