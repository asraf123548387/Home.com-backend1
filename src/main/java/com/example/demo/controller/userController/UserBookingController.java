package com.example.demo.controller.userController;

import com.example.demo.Service.BookingService;
import com.example.demo.dto.BookingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
