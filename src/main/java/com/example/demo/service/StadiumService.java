package com.example.demo.service;

import com.example.demo.model.Stadium;

import java.util.List;

public interface StadiumService {
    void modify(Stadium stadium);
    List<Stadium> show(String owner);
    void add(Stadium stadium);
    void delete(String id);
}
