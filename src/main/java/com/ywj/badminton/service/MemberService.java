package com.ywj.badminton.service;

import com.ywj.badminton.model.Member;

import java.util.List;

public interface MemberService {
    List<Member> show(String stadiumId);
    void edit (Member member);
    void delete (String stadiumId, String memberName);
    void add (Member member);
    void deleteAll(String stadiumId);
}
