package com.ywj.badminton.mapper;

import com.ywj.badminton.model.User;

public interface UserMapper {
    void register(User user);
    User login(String username,String password);
    User showUserInfo(String username);
}
