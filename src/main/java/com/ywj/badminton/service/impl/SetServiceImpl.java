package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.PeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.ywj.badminton.service.SetService;

import javax.annotation.Resource;

@Service
public class SetServiceImpl implements SetService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private PeopleMapper peopleMapper;

    @Override
    public void setKey(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}