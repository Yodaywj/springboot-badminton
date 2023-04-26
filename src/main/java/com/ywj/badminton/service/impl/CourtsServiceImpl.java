package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.CourtMapper;
import com.ywj.badminton.model.Court;
import com.ywj.badminton.service.CourtsService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class CourtsServiceImpl implements CourtsService {
    @Resource
    private CourtMapper courtMapper;
    @Override
    public void pushNewCourts(String stadiumId, int number) {
        for (int i=1; i<= number; i++){
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
    @Cacheable(value = "courts",key = "#stadiumId+#id")
    public Court getCourt(String stadiumId,int id) {
        return courtMapper.getCourt(stadiumId,id);
    }

    @Override
    public void deleteAll(String stadiumId) {
        courtMapper.deleteAll(stadiumId);
    }

    @Override
    @CacheEvict(value = "courts",key = "#stadiumId+#id")
    public void switchLight(String stadiumId, int id, String light) {
        courtMapper.switchLight(stadiumId,id,light);
    }

    @Override
    @CachePut(value = "courts",key = "#court.stadiumId+#court.id")
    public Court setNewCourt(Court court) {
        LocalDateTime now = LocalDateTime.now();
        String countdown = court.getCountdown();
        LocalTime time = LocalTime.parse(countdown);
        LocalDateTime result = now.toLocalDate().atTime(time).plusHours(now.getHour()).plusMinutes(now.getMinute()).plusSeconds(now.getSecond());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        court.setCountdown(result.format(formatter));
        courtMapper.setNewCourt(court);
        return court;
    }
}
