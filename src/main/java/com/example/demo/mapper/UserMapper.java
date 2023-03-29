package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    void register(User user);
    User login(String username,String password);
}
