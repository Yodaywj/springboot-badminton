package com.ywj.badminton.mapper;

import com.ywj.badminton.model.Court;

import java.util.List;

public interface CourtMapper {
    void pushNewCourts(Court court);
    List<Court> getAll(String stadiumId);
    void deleteAll(String stadiumId);
    void switchLight(String stadiumId,int id,String light);
    void setNewCourt(Court court);
}
