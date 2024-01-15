package com.ywj.badminton.controller;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywj.badminton.model.Avatar;
import com.ywj.badminton.model.FileEntity;
import com.ywj.badminton.model.User;
import com.ywj.badminton.service.MyMailService;
import com.ywj.badminton.service.UserService;
import com.ywj.badminton.test.Tut1Sender;
import com.ywj.badminton.utils.Code;
import com.ywj.badminton.utils.JwtUtils;
import com.ywj.badminton.utils.ResultMessage;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private Tut1Sender tut1Sender;
    @Resource
    private RedisTemplate<String,String> redisTemplate;
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
            }else {
                return ResultMessage.failure("登录失败");
            }

        }catch (Exception e){
            return ResultMessage.failure("登录失败");
        }
    }
    @GetMapping("/session")
    public ResultMessage session(String jwt, HttpSession session, HttpServletRequest request, @RequestHeader("Host") String clientURL){
        try {
            String clientIP = request.getRemoteAddr();
            if (!Objects.equals(redisTemplate.opsForValue().get(clientURL + clientIP), clientURL)){
                redisTemplate.opsForValue().set(clientURL + clientIP,clientURL,60, TimeUnit.MINUTES);
                myMailService.sendMail("yangwenjun.zj@qq.com","437238751@qq.com","437238751@qq.com","访问记录",clientIP+"访问了: "+clientURL+" 并使用了会话");
            }
            User user = (User) session.getAttribute("user");
//            if (user != null && user.getUsername().equals("Neo_Young")){
//                tut1Sender.send();
//            }
            if (user == null && !jwt.equals("null")){
                Claims claims = JwtUtils.parseJwt(jwt);
                String JSONUser = JSONUtil.toJsonStr(claims.get("user"));
                user = mapper.readValue(JSONUser,User.class);
                session.setAttribute("user",user);
            }
            if (user == null){
                throw new Exception();
            }
            Avatar avatar = userService.getAvatar(user.getUsername());
            ResultMessage resultMessage = ResultMessage.success("已登录");
            resultMessage.setOther("user",user);
            resultMessage.setOther("avatar",avatar);
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
        String jwt;
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
                jwt = JwtUtils.generateJwt(ResultMessage.data("user",newUser));
            }else {
                session.setAttribute("user",user);
                jwt = JwtUtils.generateJwt(ResultMessage.data("user",user));
            }
            return ResultMessage.success(jwt);
        }catch (Exception e){
            e.printStackTrace();
            return ResultMessage.failure("登录失败");
        }
    }
    @PostMapping("/uploadAvatar")
    public ResultMessage uploadAvatar (@RequestParam("avatar") MultipartFile avatar, String username)  {
        try {
            Avatar newAvatar = new Avatar();
            newAvatar.setFileName(username+"_avatar");
            newAvatar.setFileData(avatar.getBytes());
            newAvatar.setAvatarId(Code.generateCode(10,true));
            newAvatar.setId(username);
            userService.uploadAvatar(newAvatar);
            return ResultMessage.success("上传成功");
        } catch (IOException e) {
            return ResultMessage.failure("上传失败");
        }
    }
}