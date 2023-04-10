package com.ywj.badminton.controller;

import com.ywj.badminton.mapper.PeopleMapper;
import com.ywj.badminton.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
//@CrossOrigin(originPatterns = "*",allowCredentials = "true")
public class SetController {
    @Autowired
    public SetService setService;
    @Resource
    private PeopleMapper peopleMapper;
    @RequestMapping("/set/{key}/{value}")
    public void setString(@PathVariable String key, @PathVariable String value){
        setService.setKey(key,value);
        System.out.println(key+" 8 "+value);
        peopleMapper.setPeople(key,value);
    }
    @CrossOrigin
    @GetMapping("/get/{key}")
    @ResponseBody
    public String getString(@PathVariable String key){
        System.out.println(setService.getValue(key)+"88");
        return setService.getValue(key);
    }
}
