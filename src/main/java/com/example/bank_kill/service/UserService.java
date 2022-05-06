package com.example.bank_kill.service;

import com.example.bank_kill.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
