package com.ywj.badminton.service;

import com.ywj.badminton.model.Stadium;

import java.util.List;

public interface StadiumService {
    void modify(Stadium stadium);
    List<Stadium> show(String owner);
    void add(Stadium stadium);
    void delete(String id);
    String getName(String id);
    Stadium getStadium(String id);
}
