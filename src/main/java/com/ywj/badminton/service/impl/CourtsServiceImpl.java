package com.ywj.badminton.service.impl;

import com.ywj.badminton.service.CourtsService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class CourtsServiceImpl implements CourtsService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public void pushNewCourts(String stadiumId, int number) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        Map<String, Object> hashData = new HashMap<>();
        hashData.put("state","空闲");
        hashData.put("countdown","00:00:00");
        hashData.put("light","关");
        hashData.put("nextBooking","无");
        for (int i=1; i<= number; i++){
            hashOps.putAll(i+"of"+stadiumId, hashData);
        }
    }
}
