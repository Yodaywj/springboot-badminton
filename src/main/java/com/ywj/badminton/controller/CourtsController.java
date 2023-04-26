package com.ywj.badminton.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywj.badminton.model.Court;
import com.ywj.badminton.service.CourtsService;
import com.ywj.badminton.service.MyMailService;
import com.ywj.badminton.utils.ResultMessage;
import com.ywj.badminton.utils.TimeDifferenceCalculator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courts")
public class CourtsController {
    @Resource
    private CourtsService courtsService;
    @Resource private MyMailService myMailService;
    private final ObjectMapper mapper = new ObjectMapper();
    @PostMapping("/pushNewCourts")
    public ResultMessage pushNewCourts(@RequestBody String data) throws JsonProcessingException {
        JsonNode node = mapper.readTree(data);
        String stadiumId = node.get("stadiumId").asText();
        int number = node.get("number").asInt();
        courtsService.pushNewCourts(stadiumId,number);
        return ResultMessage.success("目前有"+number+"片场地");
    }
    @GetMapping("/getAll/{id}/{courtNumber}")
    public ResultMessage getAll(@PathVariable String id,@PathVariable int courtNumber){
        LocalDateTime now = LocalDateTime.now();
        List<Court> courts = new ArrayList<>();
        for (int i = 1;i <= courtNumber; i++){
            courts.add(courtsService.getCourt(id,i));
        }
        for (Court court : courts){
            court.setCountdown(TimeDifferenceCalculator.calculate(court.getCountdown(),now));
        }
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setOther("courts", courts);
        return resultMessage;
    }
    @DeleteMapping("/deleteAll/{id}")
    public ResultMessage deleteAll(@PathVariable String id){
        courtsService.deleteAll(id);
        return ResultMessage.success("场地删除成功");
    }
    @PatchMapping("/switchLight")
    public ResultMessage switchLight(@RequestBody String data){
        try {
            JsonNode node = mapper.readTree(data);
            String stadiumId = node.get("stadiumId").asText();
            int id = node.get("id").asInt();
            int courtNumber = node.get("courtNumber").asInt();
            String light = node.get("light").asText();
            String option = light.equals("开启中")? "开启":"关闭";
            if (id == 0){
                for (int i=1; i<=courtNumber;i++){
                    courtsService.switchLight(stadiumId,i,light);
                }
                return ResultMessage.success(option+"所有场地灯光成功");
            }else {
                courtsService.switchLight(stadiumId,id,light);
                return ResultMessage.success(option+id+"号场灯光成功");
            }
        }catch (Exception e){
            return ResultMessage.failure("操作失败");
        }
    }
    @PatchMapping("/setNewCourt")
    public ResultMessage setNewCourt(@RequestBody Court court) {
        court = courtsService.setNewCourt(court);
        int id = court.getId();
        LocalDateTime now = LocalDateTime.now();
        court.setCountdown(TimeDifferenceCalculator.calculate(court.getCountdown(),now));
        ResultMessage resultMessage = ResultMessage.success("设置"+id+"号场成功");
        resultMessage.setOther("countdown",court.getCountdown());
        return resultMessage;
    }
}
