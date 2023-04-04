package com.example.demo.controller;

import com.example.demo.service.BulletinBoardService;
import com.example.demo.utils.ResultMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bulletin-board")
public class BulletinBoardController {
    @Resource
    private BulletinBoardService bulletinBoardService;
    private final ObjectMapper mapper = new ObjectMapper();
    private ResultMessage resultMessage = new ResultMessage();
    @DeleteMapping("/delete/{id}")
    public ResultMessage delete(@PathVariable int id){
        try {
            bulletinBoardService.delete(id);
            return ResultMessage.success("删除成功");
        }catch (Exception e){
            return ResultMessage.failure("删除失败");
        }
    }
    @PutMapping("/add")
    public ResultMessage add(){
        try {
            bulletinBoardService.add();
            return ResultMessage.success("新增成功");
        }catch (Exception e){
            return ResultMessage.failure("新增失败");
        }
    }
    @PostMapping("/save")
    public ResultMessage save(@RequestBody String data){
        try {
            JsonNode node = mapper.readTree(data);
            int id = node.get("id").asInt();
            String content = node.get("content").asText();
            String title = node.get("title").asText();
            bulletinBoardService.save(id,content,title);
            return ResultMessage.success("编辑成功");
        }catch (Exception e){
            return ResultMessage.failure("编辑失败");
        }
    }
    @GetMapping ("/show")
    public ResultMessage show(){
        try {
            resultMessage.setOther("bulletin",bulletinBoardService.show());
            resultMessage.setResult(true);
            return resultMessage;
        }catch (Exception e){
            return ResultMessage.failure("网络异常");
        }
    }
}
