package com.ywj.badminton.mapper;

import com.ywj.badminton.model.Booking;
import com.ywj.badminton.model.Stadium;

import java.util.List;

public interface BookingMapper {
    List<Stadium> enquiry(int size, int offset);
    long countAll();
    List<Stadium> filter(String stadiumName,int courtNumber,String province, String city);
    void bookCourt(Booking booking);
    List<Booking> myBooking(String username);
    void deleteBooking(String id);
    List<Booking> bookingManage(String stadiumId);
    void setBooking(String id,int courtId,String state);
    void hideBooking(String id);
    List<Booking> bookingsForCourt(String stadiumId,int courtId);
}
