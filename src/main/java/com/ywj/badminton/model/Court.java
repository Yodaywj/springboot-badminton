package com.ywj.badminton.model;

import lombok.Data;

@Data
public class Court {
    private String stadiumId;

    private int id;

    private String state;

    private String light;

    private String countdown;
}
