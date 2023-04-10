package com.ywj.badminton.mapper;

import com.ywj.badminton.model.Bulletin;

import java.util.List;

public interface BulletinMapper {
    void delete (int id);
    void save (Bulletin bulletin);
    void add (String time);
    List<Bulletin> show ();
}
