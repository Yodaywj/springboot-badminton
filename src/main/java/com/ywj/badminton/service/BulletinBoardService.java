package com.ywj.badminton.service;

import com.ywj.badminton.model.Bulletin;

import java.util.List;

public interface BulletinBoardService {
    void delete (int id);
    void save (Bulletin bulletin);
    void add (String time);
    List<Bulletin> show ();
}
