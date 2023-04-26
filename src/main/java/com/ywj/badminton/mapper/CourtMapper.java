package com.ywj.badminton.mapper;

import com.ywj.badminton.model.Court;

public interface CourtMapper {
    void pushNewCourts(Court court);
    Court getCourt(String stadiumId,int id);
    void deleteAll(String stadiumId);
    void switchLight(String stadiumId,int id,String light);
    void setNewCourt(Court court);
}
