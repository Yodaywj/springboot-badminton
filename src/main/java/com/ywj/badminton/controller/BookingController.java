package com.ywj.badminton.controller;

import com.ywj.badminton.mapper.BookingMapper;
import com.ywj.badminton.model.Booking;
import com.ywj.badminton.model.Stadium;
import com.ywj.badminton.model.User;
import com.ywj.badminton.service.BookingService;
import com.ywj.badminton.service.MyMailService;
import com.ywj.badminton.utils.ResultMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    @Resource
    private BookingService bookingService;
    @Resource
    MyMailService myMailService;
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
    public ResultMessage filter(String stadiumName,int courtNumber,String province, String city,String stadiumId){
        List<Stadium> stadiums = bookingService.filter(stadiumName,courtNumber,province,city,stadiumId);
        ResultMessage resultMessage = ResultMessage.success("筛选成功");
        resultMessage.setOther("stadiums",stadiums);
        return resultMessage;
    }
    @PostMapping("/bookCourt")
    public ResultMessage bookCourt(@RequestBody Booking booking){
        try {
            bookingService.bookCourt(booking);
            String stadiumId = booking.getStadiumId();
            String username = booking.getUsername();
            String owner = myMailService.getStadiumOwner(stadiumId);
            String mail = myMailService.getMail(owner);
            executor.execute(()->{
                myMailService.sendMail(
                        "yangwenjun.zj@qq.com",
                        mail,
                        "yoda20001231@gmail.com",
                        "新预订通知",
                        username+"预订了您的场地，请及时处理");
            });
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
    @DeleteMapping("/deleteBooking/{id}")
    public ResultMessage deleteBooking(@PathVariable String id){
        bookingService.deleteBooking(id);
        return ResultMessage.success("取消预订成功");
    }
    @GetMapping("/bookingManage/{stadiumId}")
    public ResultMessage bookingManage(@PathVariable String stadiumId){
        List<Booking> bookings = bookingService.bookingManage(stadiumId);
        return ResultMessage.data("bookings",bookings);
    }
    @PatchMapping("/setBooking")
    public ResultMessage setBooking(@RequestParam String id,@RequestParam int courtId,@RequestParam String state){
        bookingService.setBooking(id,courtId,state);
        String username = id.substring(0, id.length() - 37);
        String mail = myMailService.getMail(username);
        executor.execute(()->{
            myMailService.sendMail(
                    "yangwenjun.zj@qq.com",
                    mail,
                    "yoda20001231@gmail.com",
                    "预订状态变更",
                    username+"，场馆管理员处理了您的预订订单，请及时查看");
        });
        return ResultMessage.success("设置成功");
    }
    @PatchMapping("/hideBooking/{id}")
    public ResultMessage hideBooking(@PathVariable String id){
        bookingService.hideBooking(id);
        return ResultMessage.success("删除成功");
    }
    @GetMapping("/bookingsForCourt")
    public ResultMessage bookingsForCourt(@RequestParam String stadiumId,@RequestParam int courtId){
        List<Booking> bookings = bookingService.bookingsForCourt(stadiumId,courtId);
        return ResultMessage.data("bookings",bookings);
    }
}
