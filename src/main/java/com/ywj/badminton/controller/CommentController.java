package com.ywj.badminton.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywj.badminton.model.Comment;
import com.ywj.badminton.service.CommentService;
import com.ywj.badminton.utils.ResultMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    CommentService commentService;
    ObjectMapper mapper = new ObjectMapper();
    @GetMapping("/showComment")
    public ResultMessage showComment(){
        List<Comment> comments = commentService.showComment();
        return ResultMessage.data("comments",comments);
    }
    @PostMapping("/send")
    public ResultMessage send(@RequestBody Comment comment){
        String id = UUID.randomUUID().toString();
        comment.setId(id);
        commentService.send(comment);
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setOther("id",id);
        resultMessage.setResult(true);
        resultMessage.setMessage("留言成功");
        return resultMessage;
    }
    @DeleteMapping("/delete")
    public ResultMessage delete(@RequestBody String data) throws JsonProcessingException {
        JsonNode rootNode = mapper.readTree(data);
        JsonNode commentNode = rootNode.get("comment");
        Comment comment = mapper.treeToValue(commentNode, Comment.class);
        commentService.delete(comment);
        return ResultMessage.success("删除成功");
    }
}
