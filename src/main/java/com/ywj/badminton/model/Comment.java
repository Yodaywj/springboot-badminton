package com.ywj.badminton.model;

import lombok.Data;

@Data
public class Comment {
    String id;

    String username;

    String nickname;

    String time;

    String content;
}
