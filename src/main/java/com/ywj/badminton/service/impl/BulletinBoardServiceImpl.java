package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.BulletinMapper;
import com.ywj.badminton.model.Bulletin;
import com.ywj.badminton.service.BulletinBoardService;
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
    public void save(Bulletin bulletin) {
        bulletinMapper.save(bulletin);
    }

    @Override
    public void add(String time) {
        bulletinMapper.add(time);
    }

    @Override
    public List<Bulletin> show() {
        return bulletinMapper.show();
    }
}
