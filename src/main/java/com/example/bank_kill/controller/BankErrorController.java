package com.example.bank_kill.controller;


import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.util.BaseResponsePackageUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestControllerAdvice
public class BankErrorController {

    @ExceptionHandler(Exception.class)
    public Map<String,Object> handleGlobalError(HttpServletRequest request, Exception ex){
        ex.printStackTrace();
        return BaseResponsePackageUtil.errorMessage(ex.getMessage());
    }

    public Map<String,Object> handleEchoError(HttpServletRequest request, BankException ex){
        ex.printStackTrace();
        return BaseResponsePackageUtil.errorMessage(ex.getMessage());
    }

}
