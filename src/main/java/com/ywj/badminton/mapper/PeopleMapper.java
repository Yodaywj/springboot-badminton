package com.ywj.badminton.mapper;

import org.apache.ibatis.annotations.Param;
public interface PeopleMapper {
    void setPeople(@Param("key") String id, @Param("value")String name);

    String getPeopleID (String name);
}
