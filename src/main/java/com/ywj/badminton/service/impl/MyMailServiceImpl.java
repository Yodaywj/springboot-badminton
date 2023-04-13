package com.ywj.badminton.service.impl;

import com.ywj.badminton.service.MyMailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyMailServiceImpl implements MyMailService {
    @Resource
    JavaMailSender javaMailSender;
    public void sendMail(String from,String to,String cc,String subject,String text){
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(from);
        smm.setTo(to);
        smm.setCc(cc);
        smm.setSubject(subject);
        smm.setText(text);
        javaMailSender.send(smm);
    }
}