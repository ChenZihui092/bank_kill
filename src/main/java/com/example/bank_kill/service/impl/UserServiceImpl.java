package com.example.bank_kill.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bank_kill.Dto.LoginDto;
import com.example.bank_kill.Dto.UserRegDto;
import com.example.bank_kill.Dto.UserUpdateDto;
import com.example.bank_kill.constant.ResponseConstant;
import com.example.bank_kill.exception.BankException;
import com.example.bank_kill.model.User;
import com.example.bank_kill.mapper.UserMapper;
import com.example.bank_kill.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bank_kill.util.BaseResponsePackageUtil;
import com.example.bank_kill.util.PasswordUtil;
import com.example.bank_kill.util.SessionUtil;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jfy
 * @since 2022-05-06
 */
/*
{
  "account": "31901172",
  "address": "zucc",
  "age": 22,
  "idCard": "34235235235242",
  "isBlack": false,
  "pwd": "123",
  "sex": "男",
  "state": "normal",
  "status": "normal",
  "tel": "12414352",
  "userName": "jfy",
  "userType": "个人"
}
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    public Map<String, Object> reg(UserRegDto userRegDto) {
        if (userRegDto.getIdCard() == null) throw new BankException("身份证不能为空！");
        if (userRegDto.getTel() == null) throw new BankException("联系方式不能为空");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", userRegDto.getAccount());
        User user1 = userMapper.selectOne(wrapper);
        if (user1 != null) throw new BankException("账号已存在");
        User user = new User();
        user.setAccount(userRegDto.getAccount());
        user.setAddress(userRegDto.getAddress());
        user.setAge(userRegDto.getAge());
        user.setUserName(userRegDto.getUserName());
        user.setIdcard(userRegDto.getIdCard());
        user.setIsblack(false);
        user.setCreateDate(new Date(System.currentTimeMillis()));
        user.setIsdelete(false);
        user.setSex(userRegDto.getSex());
        user.setUserType(userRegDto.getUserType());
        user.setPwd(PasswordUtil.generatePassword(userRegDto.getUserName(), userRegDto.getPwd()));
        user.setTel(userRegDto.getTel());
        user.setStatus(userRegDto.getStatus());
        user.setIsblack(userRegDto.getIsBlack());
        userMapper.insert(user);
        return ResponseConstant.V_ADD_SUCCESS;
    }

    public User getByAccount(String account) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public Map<String, Object> login(HttpSession session, LoginDto loginDto) {
        User user = getByAccount(loginDto.getAccount());
        if (user == null) return BaseResponsePackageUtil.errorMessage("账户不存在");
        if (!PasswordUtil.generatePassword(user.getUserName(), loginDto.getPwd()).equals(user.getPwd()))
            return ResponseConstant.X_PWD;
        SessionUtil.saveUserToSession(session, user);
        return ResponseConstant.V_USER_LOGIN_SUCCESS;
    }

    @Override
    public Map<String, Object> logout(HttpSession session) {
        SessionUtil.removeUserFromSession(session);
        return ResponseConstant.V_USER_LOGOUT_SUCCESS;
    }

    @Override
    public Map<String, Object> getAllUserInfo(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<User> wrappers = Wrappers.<User>lambdaQuery();
        if (!search.equals("null")) {
            wrappers.like(User::getAccount, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        return BaseResponsePackageUtil.baseData(
                ImmutableMap.of(
                        "msg", "查找成功",
                        "res", userPage
                )
        );
    }

    @Override
    public Map<String, Object> delete(Integer userId) {
        User user = getById(userId);
        user.setIsdelete(true);
        userMapper.updateById(user);
        return ResponseConstant.V_DELETE_SUCCESS;
    }

    @Override
    public Map<String, Object> update(UserUpdateDto userUpdateDto) {
        User user = new User(); //后面修改为当前用户
        if (userUpdateDto.getIsBlack()!=null)user.setIsdelete(userUpdateDto.getIsBlack());
        if (userUpdateDto.getUserName()!=null)user.setUserName(userUpdateDto.getUserName());
        if (userUpdateDto.getAccount()!=null)user.setAccount(userUpdateDto.getAccount());
        if (userUpdateDto.getAddress()!=null)user.setAddress(userUpdateDto.getAddress());
        if (userUpdateDto.getUserType()!=null)user.setUserType(userUpdateDto.getUserType());

        if (userUpdateDto.getAge()!=null)user.setAge(userUpdateDto.getAge());
        if (userUpdateDto.getSex()!=null)user.setSex(userUpdateDto.getSex());
        if (userUpdateDto.getIdCard()!=null)user.setIdcard(userUpdateDto.getIdCard());
        if (userUpdateDto.getPwd()!=null)user.setPwd(userUpdateDto.getPwd());
        if (userUpdateDto.getStatus()!=null)user.setStatus(userUpdateDto.getStatus());

        if (userUpdateDto.getTel()!=null)user.setTel(userUpdateDto.getTel());
        userMapper.updateById(user);
        return ResponseConstant.V_UPDATE_SUCCESS;
    }

    @Override
    public User findById(int userId) {
        return userMapper.selectById(userId);
    }
}
