package com.ywj.badminton.model;

import lombok.Data;

@Data
public class Booking {
    private String id;

    private String stadiumId;

    private int courtId;

    private String time;

    private String username;

    private float duration;

    private String remarks;

    private String state;

    private String stadiumName;
}
