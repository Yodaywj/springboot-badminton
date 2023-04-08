package com.example.demo.model;

import lombok.Data;

@Data
public class Member {
    private String memberName;

    private String stadiumId;

    private int balance;

    private  String expiredTime;

    private String startTime;

    private String level;

    private String remarks;
}
