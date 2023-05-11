package com.ywj.badminton.service;

import com.ywj.badminton.model.Booking;
import com.ywj.badminton.model.Stadium;

import java.util.List;

public interface BookmarkService {
    List<String> getBookmarks(String username);
    void addBookmark(String username,String stadiumId);
    void deleteBookmark(String username,String stadiumId);
    List<Stadium> getMarkedStadiums(String username);
}
