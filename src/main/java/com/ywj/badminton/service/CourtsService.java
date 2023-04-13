package com.ywj.badminton.service;

import com.ywj.badminton.model.Court;

import java.util.List;

public interface CourtsService {
    void pushNewCourts(String stadiumId, int number);
    List<Court> getAll(String stadiumId);
    void deleteAll(String stadiumId);
    void switchLight(String stadiumId, int id, String light);
    void setNewCourt(Court court);
}
