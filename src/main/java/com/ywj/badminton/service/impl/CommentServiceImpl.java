package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.CommentMapper;
import com.ywj.badminton.model.Comment;
import com.ywj.badminton.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    CommentMapper commentMapper;
    @Override
    public List<Comment> showComment() {
        return commentMapper.showComment();
    }
    @Override
    public void send(Comment comment) {
        commentMapper.send(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentMapper.delete(comment);
    }
}
