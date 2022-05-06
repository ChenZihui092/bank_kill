package com.example.bank_kill.service;

import com.example.bank_kill.Dto.LoginDto;
import com.example.bank_kill.Dto.UserRegDto;
import com.example.bank_kill.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
public interface UserService extends IService<User> {
    User findById(int userId);
    Map<String,Object> reg(UserRegDto userRegDto);
    Map<String,Object> login(HttpSession session, LoginDto loginDto);
    Map<String,Object> logout(HttpSession session);
}
