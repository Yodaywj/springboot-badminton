package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.BookingMapper;
import com.ywj.badminton.model.Booking;
import com.ywj.badminton.model.Stadium;
import com.ywj.badminton.service.BookingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {
    @Resource
    private BookingMapper bookingMapper;
    @Override
    public List<Stadium> enquiry(int size, int offset) {
        return bookingMapper.enquiry(size,offset);
    }

    @Override
    public long countAll() {
        return bookingMapper.countAll();
    }

    @Override
    public List<Stadium> filter(String stadiumName,int courtNumber,String province, String city) {
        return bookingMapper.filter(stadiumName,courtNumber,province,city);
    }

    @Override
    public void bookCourt(Booking booking) {
        String id = UUID.randomUUID().toString();
        String username = booking.getUsername();
        booking.setId(username+"_"+id);
        bookingMapper.bookCourt(booking);
    }

    @Override
    public List<Booking> myBooking(String username) {
        return bookingMapper.myBooking(username);
    }

    @Override
    public void deleteBooking(String id) {
        bookingMapper.deleteBooking(id);
    }

    @Override
    public List<Booking> bookingManage(String stadiumId) {
        return bookingMapper.bookingManage(stadiumId);
    }

    @Override
    public void setBooking(String id, int courtId, String state) {
        bookingMapper.setBooking(id,courtId,state);
    }

    @Override
    public void hideBooking(String id) {
        bookingMapper.hideBooking(id);
    }

    @Override
    public List<Booking> bookingsForCourt(String stadiumId, int courtId) {
        return bookingMapper.bookingsForCourt(stadiumId,courtId);
    }
}
