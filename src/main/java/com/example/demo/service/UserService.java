package com.example.demo.service;

import com.example.demo.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    void register(User user);

    User login(String username, String password);

    void logout(HttpSession session);
}
