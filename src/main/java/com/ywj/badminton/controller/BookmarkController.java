package com.ywj.badminton.controller;

import com.ywj.badminton.model.Stadium;
import com.ywj.badminton.service.BookmarkService;
import com.ywj.badminton.utils.ResultMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {
    @Resource
    private BookmarkService bookmarkService;
    @GetMapping("/getBookmarks/{username}")
    public ResultMessage getBookmarks(@PathVariable String username){
        List<String> bookmarks = bookmarkService.getBookmarks(username);
        return ResultMessage.data("bookmarks",bookmarks);
    }
    @PostMapping("/addBookmark")
    public ResultMessage addBookmark(String username,String stadiumId){
        try {
            bookmarkService.addBookmark(username,stadiumId);
            return ResultMessage.success("收藏成功");
        }catch (Exception e){
            return ResultMessage.failure("操作过快");
        }
    }
    @DeleteMapping("/deleteBookmark")
    public ResultMessage deleteBookmark(String username,String stadiumId){
        try {
            bookmarkService.deleteBookmark(username,stadiumId);
            return ResultMessage.success("取消成功");
        }catch (Exception e){
            return ResultMessage.failure("操作过快");
        }
    }
    @GetMapping("/getMarkedStadiums/{username}")
    public ResultMessage getMarkedStadiums(@PathVariable String username){
        List<Stadium> stadiums = bookmarkService.getMarkedStadiums(username);
        return ResultMessage.data("stadiums",stadiums);
    }
}
