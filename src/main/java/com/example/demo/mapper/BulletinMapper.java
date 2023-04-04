package com.example.demo.mapper;

import com.example.demo.model.Bulletin;

import java.util.List;

public interface BulletinMapper {
    void delete (int id);
    void save (int id, String content, String title);
    void add ();
    List<Bulletin> show ();
}
