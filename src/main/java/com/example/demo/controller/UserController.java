package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.ResultMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    private final ObjectMapper mapper = new ObjectMapper();
    private ResultMessage resultMessage = new ResultMessage();
    @PostMapping("/register")
    public ResultMessage register(@RequestBody User newUser) {
        try {
            userService.register(newUser);
            resultMessage = ResultMessage.success("注册成功");
            resultMessage.setOther("用户名",newUser.getUsername());
            return resultMessage;
        }catch (Exception e){
            resultMessage = ResultMessage.failure("用户名已存在");
            return resultMessage;
        }
    }
    @PostMapping("/login")
    public ResultMessage login(@RequestBody String values, HttpSession session) {
        try {
            JsonNode node = mapper.readTree(values);
            String username = node.get("username").asText();
            String password = node.get("password").asText();
            User user = userService.login(username,password);
            session.setAttribute("user",user);
            resultMessage = ResultMessage.success("登录成功");
            resultMessage.setOther("nickname",user.getNickname());
            return resultMessage;
        }catch (Exception e){
            resultMessage = ResultMessage.failure("登录失败");
            return resultMessage;
        }
    }
    @GetMapping("/session")
    public ResultMessage session(HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) throw new Exception();
            resultMessage = ResultMessage.success("已登录");
            resultMessage.setOther("user",user);
            return resultMessage;
        }catch (Exception e){
            resultMessage = ResultMessage.failure("未登录或登录超时");
            return resultMessage;
        }
    }
    @DeleteMapping("/logout")
    public ResultMessage logout(HttpSession session) {
        session.removeAttribute("user");
        resultMessage = ResultMessage.success("退出成功");
        return resultMessage;
    }
}

