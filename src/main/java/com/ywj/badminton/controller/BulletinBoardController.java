package com.ywj.badminton.controller;

import com.ywj.badminton.model.Bulletin;
import com.ywj.badminton.service.BulletinBoardService;
import com.ywj.badminton.utils.ResultMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/bulletin-board")
public class BulletinBoardController {
    @Resource
    private BulletinBoardService bulletinBoardService;

    @DeleteMapping("/delete/{id}")
    public ResultMessage delete(@PathVariable int id){
        try {
            bulletinBoardService.delete(id);
            return ResultMessage.success("删除成功");
        }catch (Exception e){
            return ResultMessage.failure("删除失败");
        }
    }
    @PostMapping("/add")
    public ResultMessage add(){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time = format.format(new Date());
            bulletinBoardService.add(time);
            return ResultMessage.success("新增成功");
        }catch (Exception e){
            return ResultMessage.failure("新增失败");
        }
    }
    @PostMapping("/save")
    public ResultMessage save(@RequestBody Bulletin data){
        try {
            bulletinBoardService.save(data);
            return ResultMessage.success("编辑成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultMessage.failure("编辑失败");
        }
    }
    @GetMapping ("/show")
    public ResultMessage show(){
        try {
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setOther("bulletin",bulletinBoardService.show());
            resultMessage.setResult(true);
            return resultMessage;
        }catch (Exception e){
            return ResultMessage.failure("网络异常");
        }
    }
}
