package com.ywj.badminton.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywj.badminton.model.User;
import com.ywj.badminton.service.MyMailService;
import com.ywj.badminton.service.UserService;
import com.ywj.badminton.utils.JwtUtils;
import com.ywj.badminton.utils.ResultMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private MyMailService myMailService;
    private final ObjectMapper mapper = new ObjectMapper();
    @PostMapping("/register")
    public ResultMessage register(@RequestBody User newUser) {
        try {
            userService.register(newUser);
            ResultMessage resultMessage = ResultMessage.success("注册成功");
            resultMessage.setOther("用户名",newUser.getUsername());
            return resultMessage;
        }catch (Exception e){
            return ResultMessage.failure("用户名已存在");
        }
    }
    @PostMapping("/login")
    public ResultMessage login(@RequestBody String values, HttpSession session) {
        try {
            JsonNode node = mapper.readTree(values);
            String username = node.get("username").asText();
            String password = node.get("password").asText();
            User user = userService.login(username,password);
            if (user != null){
                ResultMessage resultMessage = ResultMessage.success("登录成功");
                session.setAttribute("user",user);
                String jwt = JwtUtils.generateJwt(ResultMessage.data("user",user));
                resultMessage.setOther("jwt",jwt);
                resultMessage.setOther("nickname",user.getNickname());
                return resultMessage;
            }else
                return ResultMessage.failure("登录失败");

        }catch (Exception e){
            return ResultMessage.failure("登录失败");
        }
    }
    @GetMapping("/session")
    public ResultMessage session(HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) throw new Exception();
            ResultMessage resultMessage = ResultMessage.success("已登录");
            resultMessage.setOther("user",user);
            return resultMessage;
        }catch (Exception e){
            return ResultMessage.failure("未登录或登录超时");
        }
    }
    @DeleteMapping("/logout")
    public ResultMessage logout(HttpSession session) {
        userService.logout(session);
        return ResultMessage.success("退出成功");
    }
    @GetMapping("/showUserInfo")
    public ResultMessage showUserInfo(@RequestParam("memberName") String username){
        User user = userService.showUserInfo(username);
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setOther("user", user);
        return resultMessage;

    }
    @PatchMapping("/editUser")
    public ResultMessage editUser(@RequestBody User user,HttpSession session){
        try {
            System.out.println(user);
            userService.editUser(user);
            User current = (User) session.getAttribute("user");
            User newUser = userService.login(current.getUsername(), current.getPassword());
            session.setAttribute("user",newUser);
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setOther("user",newUser);
            resultMessage.setResult(true);
            resultMessage.setMessage("编辑成功");
            return resultMessage;
        }catch (Exception e){
            return ResultMessage.failure("编辑失败");
        }

    }
    @GetMapping("/getCaptcha")
    public void getCaptcha(String mail,String type){
        myMailService.generateCode(mail,type);
    }
    @GetMapping("/validateCaptcha")
    public ResultMessage validateCaptcha(String mail,String type,String code){
        return ResultMessage.data("state",myMailService.validateCode(mail,type,code));
    }
    @PatchMapping("/resetPassword")
    public void resetPassword(String mail,String password){
        userService.resetPassword(mail,password);
    }
    @PostMapping("/quickLogin")
    public ResultMessage quickLogin (String mail,HttpSession session){
        User user = userService.findUserByMail(mail);
        try {
            if (user == null){
                User newUser = new User();
                newUser.setMail(mail);
                newUser.setUsername(mail);
                newUser.setNickname(mail);
                newUser.setPassword(UUID.randomUUID().toString());
                newUser.setGender("male");
                newUser.setPrivilege("general");
                userService.register(newUser);
                session.setAttribute("user",newUser);
            }else {
                session.setAttribute("user",user);
            }
            return ResultMessage.success("登录成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultMessage.failure("登录失败");
        }
    }
}

