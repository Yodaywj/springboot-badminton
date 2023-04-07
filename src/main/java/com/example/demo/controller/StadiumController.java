package com.example.demo.controller;

import com.example.demo.model.Stadium;
import com.example.demo.model.User;
import com.example.demo.service.StadiumService;
import com.example.demo.utils.ResultMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("/stadium")
public class StadiumController {
    @Resource
    private StadiumService stadiumService;
    private final ObjectMapper mapper = new ObjectMapper();
    @PostMapping("/modify")
    public ResultMessage modify(@RequestBody Stadium stadium){
        try {
            stadiumService.modify(stadium);
            return ResultMessage.success("编辑成功");
        }catch (Exception e){
            return ResultMessage.failure("编辑失败");
        }
    }
    @GetMapping("/show")
    public ResultMessage show(HttpSession session){
        ResultMessage resultMessage = new ResultMessage();
        User user = (User) session.getAttribute("user");
        String owner = user.getUsername();
        resultMessage.setOther("stadiums",stadiumService.show(owner));
        return resultMessage;
    }
    @DeleteMapping("/delete/{id}")
    public ResultMessage getOriginalData(@PathVariable String id){
        try {
            stadiumService.delete(id);
            return ResultMessage.success("删除成功");
        }catch (Exception e){
            return ResultMessage.failure("删除失败");
        }
    }
    @PostMapping("/add")
    public ResultMessage add(@RequestBody Stadium stadium,HttpSession session){
        try {
            ResultMessage resultMessage = new ResultMessage();
            String id = UUID.randomUUID().toString();
            stadium.setId(id);
            User user = (User) session.getAttribute("user");
            String username = user.getUsername();
            stadium.setOwner(username);
            stadiumService.add(stadium);
            resultMessage.setOther("id",id);
            resultMessage.setOther("username",username);
            resultMessage.setResult(true);
            resultMessage.setMessage("新增成功");
            return resultMessage;
        }catch (Exception e){
            return ResultMessage.failure("新增失败");
        }
    }
}
