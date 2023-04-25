package com.ywj.badminton.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Court implements Serializable {
    private String stadiumId;

    private int id;

    private String state;

    private String light;

    private String countdown;
}
