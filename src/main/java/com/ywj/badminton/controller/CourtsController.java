package com.ywj.badminton.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywj.badminton.service.CourtsService;
import com.ywj.badminton.utils.ResultMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/courts")
public class CourtsController {
    @Resource
    private CourtsService courtsService;
    private final ObjectMapper mapper = new ObjectMapper();
    @PostMapping("/pushNewCourts")
    public ResultMessage pushNewCourts(@RequestBody String data) throws JsonProcessingException {
        JsonNode node = mapper.readTree(data);
        String stadiumId = node.get("stadiumId").asText();
        int number = node.get("number").asInt();
        System.out.println(data);
        System.out.println(stadiumId);
        System.out.println(number);
        courtsService.pushNewCourts(stadiumId,number);
        return ResultMessage.success("场馆共有"+number+"块场地");
    }
//    @GetMapping("/getAll")
//    public ResultMessage getAll()
}
