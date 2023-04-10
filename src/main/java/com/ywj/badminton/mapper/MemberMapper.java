package com.ywj.badminton.mapper;

import com.ywj.badminton.model.Member;

import java.util.List;

public interface MemberMapper {
    List<Member> show(String stadiumId) ;
    void edit(Member member);
    void delete (String stadiumId, String memberName);
    void add (Member member);
}
