package com.ywj.badminton.service;

public interface MyMailService {
    void sendMail(String from,String to,String cc,String subject,String text);
    void generateCode(String mail,String type);
    Boolean validateCode(String mail,String type,String code);
    String getStadiumOwner(String stadiumId);
    String getMail (String username);
}
