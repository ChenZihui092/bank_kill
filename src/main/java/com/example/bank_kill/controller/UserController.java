package com.example.bank_kill.controller;


import com.example.bank_kill.Dto.LoginDto;
import com.example.bank_kill.Dto.UserRegDto;
import com.example.bank_kill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "reg",method = RequestMethod.POST)
    public Map<String,Object> reg(
            @RequestBody UserRegDto dto
    ){
        return userService.reg(dto);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object> login(
            HttpServletRequest request,
            @RequestBody LoginDto loginDto
    ){
        return userService.login(request.getSession(),loginDto);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Map<String,Object> logout(
            HttpServletRequest request
    ){
        return userService.logout(request.getSession());
    }

}

