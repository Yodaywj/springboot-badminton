package com.ywj.badminton.controller;

import com.ywj.badminton.mapper.BookingMapper;
import com.ywj.badminton.model.Booking;
import com.ywj.badminton.model.Stadium;
import com.ywj.badminton.model.User;
import com.ywj.badminton.service.BookingService;
import com.ywj.badminton.utils.ResultMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Resource
    private BookingService bookingService;
    @GetMapping("/enquiry")
    public ResultMessage enquiry(int page,int size){
        int offset = size * (page - 1);
        long sum = bookingService.countAll();
        List<Stadium> stadiums = bookingService.enquiry(size,offset);
        ResultMessage resultMessage = ResultMessage.success("");
        resultMessage.setOther("stadiums",stadiums);
        resultMessage.setOther("sum",sum);
        return resultMessage;
    }
    @GetMapping("/filter")
    public ResultMessage filter(String stadiumName,int courtNumber,String province, String city){
        System.out.println(province);
        System.out.println(stadiumName);
        List<Stadium> stadiums = bookingService.filter(stadiumName,courtNumber,province,city);
        ResultMessage resultMessage = ResultMessage.success("筛选成功");
        resultMessage.setOther("stadiums",stadiums);
        return resultMessage;
    }
    @PostMapping("/bookCourt")
    public ResultMessage bookCourt(@RequestBody Booking booking){
        try {
            bookingService.bookCourt(booking);
            return ResultMessage.success("预订成功");
        }catch (Exception e){
            return ResultMessage.failure("预订失败");
        }
    }
    @GetMapping("/myBooking")
    public ResultMessage myBooking(HttpSession session){
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        List<Booking> myBooking = bookingService.myBooking(username);
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setOther("myBooking",myBooking);
        return resultMessage;
    }
}
