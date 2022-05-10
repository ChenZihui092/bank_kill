package com.example.bank_kill.controller;


import com.example.bank_kill.Dto.LoginDto;
import com.example.bank_kill.Dto.UserRegDto;
import com.example.bank_kill.Dto.UserUpdateDto;
import com.example.bank_kill.service.UserService;
import com.example.bank_kill.util.SessionUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 前端控制器
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

    @RequestMapping(value = "reg", method = RequestMethod.POST)
    public Map<String, Object> reg(
            @RequestBody UserRegDto dto
    ) {
        return userService.reg(dto);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(
            HttpServletRequest request,
            @RequestBody LoginDto loginDto
    ) {
        return userService.login(request.getSession(), loginDto);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Map<String, Object> logout(
            HttpServletRequest request
    ) {
        return userService.logout(request.getSession());
    }

    @GetMapping(value = "/getAllUserInfo")
    public Map<String, Object> getAllUserInfo(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "null") String search

    ) {
        return userService.getAllUserInfo(pageNum, pageSize, search);
    }

    @PostMapping(value = "/delete")
    public Map<String, Object> delete(
        Integer userId
    ) {
        return userService.delete(userId);
    }

    @PostMapping(value = "/update")
    public Map<String, Object> update(
            UserUpdateDto userUpdateDto
    ) {
        return userService.update(userUpdateDto);
    }

}

