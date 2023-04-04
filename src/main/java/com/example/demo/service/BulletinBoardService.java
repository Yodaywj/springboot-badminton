package com.example.demo.service;

import com.example.demo.model.Bulletin;

import java.util.List;

public interface BulletinBoardService {
    void delete (int id);
    void save (int id, String content, String title);
    void add ();
    List<Bulletin> show ();
}
