package com.ywj.badminton.model;

import lombok.Data;

@Data
public class Booking {
    private String stadiumId;

    private int courtId;

    private String time;

    private String username;

    private int duration;

    private String remarks;

    private String state;

    private String stadiumName;
}
