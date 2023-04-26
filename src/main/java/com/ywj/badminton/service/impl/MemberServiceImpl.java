package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.MemberMapper;
import com.ywj.badminton.model.Member;
import com.ywj.badminton.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Override
    public List<Member> show(String stadiumId) {
        return memberMapper.show(stadiumId);
    }

    @Override
    public void edit(Member member) {
        memberMapper.edit(member);
    }

    @Override
    public void delete(String stadiumId, String memberName) {
        memberMapper.delete(stadiumId,memberName);
    }

    @Override
    public void add(Member member) {
        memberMapper.add(member);
    }

    @Override
    public void deleteAll(String stadiumId) {

    }
}
