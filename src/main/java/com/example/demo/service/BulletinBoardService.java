package com.example.demo.service;

import com.example.demo.model.Bulletin;

import java.util.List;

public interface BulletinBoardService {
    void delete (int id);
    void save (Bulletin bulletin);
    void add (String time);
    List<Bulletin> show ();
}
