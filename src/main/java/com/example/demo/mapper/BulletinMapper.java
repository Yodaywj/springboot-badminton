package com.example.demo.mapper;

import com.example.demo.model.Bulletin;

import java.util.List;

public interface BulletinMapper {
    void delete (int id);
    void save (Bulletin bulletin);
    void add (String time);
    List<Bulletin> show ();
}
