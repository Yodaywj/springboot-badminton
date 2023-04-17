package com.ywj.badminton.service.impl;

import com.ywj.badminton.mapper.BookingMapper;
import com.ywj.badminton.model.Booking;
import com.ywj.badminton.model.Stadium;
import com.ywj.badminton.service.BookingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
        bookingMapper.bookCourt(booking);
    }
}
