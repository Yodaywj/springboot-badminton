package com.ywj.badminton.service;

import com.ywj.badminton.model.User;

import javax.servlet.http.HttpSession;

public interface UserService {
    void register(User user);

    User login(String username, String password);

    void logout(HttpSession session);
}
