package com.ywj.badminton.service;

public interface MyMailService {
    void sendMail(String from,String to,String cc,String subject,String text);
}
