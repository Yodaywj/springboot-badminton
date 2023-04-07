package com.example.demo.service.impl;

import com.example.demo.mapper.StadiumMapper;
import com.example.demo.model.Stadium;
import com.example.demo.service.StadiumService;
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
}
