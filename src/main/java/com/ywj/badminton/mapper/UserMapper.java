package com.ywj.badminton.mapper;

import com.ywj.badminton.model.Avatar;
import com.ywj.badminton.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    void register(User user);
    User login(String username,String password);
    User showUserInfo(String username);
    void editUser(User user);
    void resetPassword(String mail,String password);
    User findUserByMail(String mail);
    void uploadAvatar(Avatar avatar);
    Avatar getAvatar(String id);
}
