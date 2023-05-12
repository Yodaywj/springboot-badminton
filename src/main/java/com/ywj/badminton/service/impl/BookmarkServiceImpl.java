package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.BookmarkMapper;
import com.ywj.badminton.model.Booking;
import com.ywj.badminton.model.Stadium;
import com.ywj.badminton.service.BookmarkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    @Resource
    private BookmarkMapper bookmarkMapper;
    @Override
    public List<String> getBookmarks(String username) {
        return bookmarkMapper.getBookmarks(username);
    }
    @Override
    public void addBookmark(String username, String stadiumId) {
        bookmarkMapper.addBookmark(username,stadiumId);
    }

    @Override
    public void deleteBookmark(String username, String stadiumId) {
        bookmarkMapper.deleteBookmark(username,stadiumId);
    }
    @Override
    public List<Stadium> getMarkedStadiums(String username) {
        return bookmarkMapper.getMarkedStadiums(username);
    }
}
