package com.ywj.badminton.service;

import com.ywj.badminton.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> showComment();
    void send(Comment comment);
    void delete(Comment comment);
}
