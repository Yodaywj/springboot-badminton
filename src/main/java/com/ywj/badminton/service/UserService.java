package com.ywj.badminton.service;

import com.ywj.badminton.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    void register(User user);

    User login(String username, String password);

    void logout(HttpSession session);

    User showUserInfo(String username);
    void editUser(User user);
    void resetPassword(String mail,String password);
    User findUserByMail(String mail);
}
