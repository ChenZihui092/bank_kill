package com.example.bank_kill.exception;

public class BankException extends RuntimeException{

    public BankException(){}

    public BankException(String msg){
        super(msg);
    }
}
