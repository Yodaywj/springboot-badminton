package com.example.demo.service.impl;

import com.example.demo.mapper.BulletinMapper;
import com.example.demo.model.Bulletin;
import com.example.demo.service.BulletinBoardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BulletinBoardServiceImpl implements BulletinBoardService {
    @Resource
    private BulletinMapper bulletinMapper;
    @Override
    public void delete(int id) {
        bulletinMapper.delete(id);
    }

    @Override
    public void save(int id, String content, String title) {
        bulletinMapper.save(id, content, title);
    }

    @Override
    public void add() {
        bulletinMapper.add();
    }

    @Override
    public List<Bulletin> show() {
        return bulletinMapper.show();
    }
}
