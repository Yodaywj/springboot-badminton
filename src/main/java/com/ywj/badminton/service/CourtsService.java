package com.ywj.badminton.service;

import com.ywj.badminton.model.Court;

import java.util.List;

public interface CourtsService {
    void pushNewCourts(String stadiumId, int number);
    Court getCourt(String stadiumId ,int id);
    void deleteAll(String stadiumId);
    void switchLight(String stadiumId, int id, String light);
    Court setNewCourt(Court court);
}
