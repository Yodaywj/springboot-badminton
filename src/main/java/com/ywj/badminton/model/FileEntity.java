package com.ywj.badminton.model;

import lombok.Data;

import java.sql.Blob;

@Data
public class FileEntity {
    private String id; // equals username

    private String fileName;

    private byte[] fileData;
}
