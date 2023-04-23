package com.ywj.badminton.mapper;

import com.ywj.badminton.model.Comment;

import java.util.List;

public interface CommentMapper {
    List<Comment> showComment();
    void send(Comment comment);
    void delete(Comment comment);
}
