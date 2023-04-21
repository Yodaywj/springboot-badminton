package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.StadiumMapper;
import com.ywj.badminton.model.Stadium;
import com.ywj.badminton.service.StadiumService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class StadiumServiceImpl implements StadiumService {
    @Resource
    StadiumMapper stadiumMapper;
    @Override
    public void modify(Stadium stadium) {
        stadiumMapper.modify(stadium);
    }

    @Override
    public List<Stadium> show(String owner) {
        return stadiumMapper.show(owner);
    }

    @Override
    public void add(Stadium stadium) {
        stadiumMapper.add(stadium);
    }

    @Override
    public void delete(String id) {
        stadiumMapper.delete(id);
    }

    @Override
    public String getName(String id) {
        return stadiumMapper.getName(id);
    }

    @Override
    public Stadium getStadium(String id) {
        return stadiumMapper.getStadium(id);
    }
}
