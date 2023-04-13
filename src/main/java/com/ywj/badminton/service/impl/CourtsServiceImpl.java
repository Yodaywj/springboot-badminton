package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.CourtMapper;
import com.ywj.badminton.model.Court;
import com.ywj.badminton.service.CourtsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class CourtsServiceImpl implements CourtsService {
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private CourtMapper courtMapper;
    @Override
    public void pushNewCourts(String stadiumId, int number) {
//        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
//        Map<String, Object> hashData = new HashMap<>();
//        hashData.put("state","空闲");
//        hashData.put("countdown","00:00:00");
//        hashData.put("light","关");
        for (int i=1; i<= number; i++){
//            hashOps.putAll(i+"of"+stadiumId, hashData);
            Court court = new Court();
            court.setStadiumId(stadiumId);
            court.setLight("已关闭");
            court.setId(i);
            court.setState("空闲");
            court.setCountdown("2000-12-31 00:00:00");
            courtMapper.pushNewCourts(court);
        }
    }

    @Override
    public List<Court> getAll(String stadiumId) {
//        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
//        List<Court> courts = new ArrayList<>();
//        Map<String, Object> hashData;
//        for (int i=1; i<= number; i++){
//            Court court = new Court();
//            hashData = hashOps.entries(i+"of"+stadiumId);
//            court.setCountdown((String) hashData.get("countdown"));
//            court.setState((String) hashData.get("state"));
//            court.setLight((String) hashData.get("light"));
//            court.setId(i);
//            court.setStadiumId(stadiumId);
//            court.setNextBooking("");
//            courts.add(court);
//        }
//        return courts;
        return courtMapper.getAll(stadiumId);
    }

    @Override
    public void deleteAll(String stadiumId) {
//        for (int i=1; i<= number; i++){
//            redisTemplate.delete(i+"of"+stadiumId);
//        }
        courtMapper.deleteAll(stadiumId);
    }

    @Override
    public void switchLight(String stadiumId, int id,String light) {
        courtMapper.switchLight(stadiumId,id,light);
    }

    @Override
    public void setNewCourt(Court court) {
        LocalDateTime now = LocalDateTime.now();
        String countdown = court.getCountdown();
        LocalTime time = LocalTime.parse(countdown);
        LocalDateTime result = now.toLocalDate().atTime(time).plusHours(now.getHour()).plusMinutes(now.getMinute()).plusSeconds(now.getSecond());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        court.setCountdown(result.format(formatter));
        courtMapper.setNewCourt(court);
    }
}
