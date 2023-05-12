package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.MyMailMapper;
import com.ywj.badminton.service.MyMailService;
import com.ywj.badminton.utils.Code;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class MyMailServiceImpl implements MyMailService {
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    JavaMailSender javaMailSender;
    @Resource
    MyMailMapper myMailMapper;
    @Override
    public void sendMail(String from,String to,String cc,String subject,String text){
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(from);
        smm.setTo(to);
        smm.setCc(cc);
        smm.setSubject(subject);
        smm.setText(text);
        javaMailSender.send(smm);
    }
    @Override
    public void generateCode(String mail,String type){
        String code = Code.generateCode(6);
        redisTemplate.opsForValue().set(mail+type,code,5, TimeUnit.MINUTES);
        String CHType = "";
        switch (type) {
            case "register":
                CHType = "注册";
                break;
            case "reset":
                CHType = "重置密码";
                break;
        }
        String fromMail = "yangwenjun.zj@qq.com";
        sendMail(fromMail,mail,"yoda20001231@gmail.com","尚羽智店验证码",mail+"您的"+CHType+"验证码为"+code);
    }

    @Override
    public Boolean validateCode(String mail, String type, String code) {
        return Objects.equals(redisTemplate.opsForValue().get(mail+type), code);
    }

    @Override
    public String getStadiumOwner(String stadiumId) {
        return myMailMapper.getStadiumOwner(stadiumId);
    }

    @Override
    public String getMail(String username) {
        return myMailMapper.getMail(username);
    }
}