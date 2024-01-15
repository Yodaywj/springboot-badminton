package com.ywj.badminton.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Avatar extends FileEntity{
    private String avatarId;
}
