package com.example.demo.controller.userController;

import com.example.demo.Service.BookingService;
import com.example.demo.dto.BookingDto;
import com.example.demo.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserBookingController {
    @Autowired
    private BookingService bookingService;



    @PostMapping("/roomBooking")
    public ResponseEntity<String> handleRoomBooking(@RequestBody BookingDto bookingDto){
        try {
            bookingService.bookRoom(bookingDto);
            return new ResponseEntity<>("booking successfully", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Booking failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/onlineBooking")
    public ResponseEntity<String> hanRoomOnlineBooking(@RequestBody BookingDto bookingDto){
        try{
            bookingService.onlineBookRoom(bookingDto);
            return new ResponseEntity<>("Booking successfully",HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>("Booking failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/userBookingList/{userId}")
    public ResponseEntity<List<BookingDto>> getUserBookings(@PathVariable Long userId){
        List<BookingDto> bookings=bookingService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }
}
