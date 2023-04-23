package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.UserMapper;
import com.ywj.badminton.model.User;
import com.ywj.badminton.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public void register(User user) {
        userMapper.register(user);
    }

    @Override
    public User login(String username, String password) {
        return userMapper.login(username,password);
    }

    @Override
    public void logout(HttpSession session) {
        session.removeAttribute("user");
    }

    @Override
    public User showUserInfo(String username) {
        return userMapper.showUserInfo(username);
    }

    @Override
    public void editUser(User user) {
        userMapper.editUser(user);
    }

    @Override
    public String captchaForResetting(String username, String mail) {
        return null;
    }
}
