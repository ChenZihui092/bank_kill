package com.example.bank_kill.service.impl;

import com.example.bank_kill.model.User;
import com.example.bank_kill.mapper.UserMapper;
import com.example.bank_kill.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public User findById(int userId) {
        return userMapper.selectById(userId);
    }
}
