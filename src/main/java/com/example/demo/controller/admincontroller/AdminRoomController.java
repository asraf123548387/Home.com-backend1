package com.example.demo.controller.admincontroller;

import com.example.demo.Service.HotelService;
import com.example.demo.Service.RoomService;
import com.example.demo.dto.RoomDto;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRoomController {
    @Autowired
    HotelService hotelService;
    @Autowired
    RoomService roomService;


    @GetMapping("/adminHotelListInAddRoom/{adminUserId}")
    public ResponseEntity<List<Hotel>> getHotelsForAdminForAddRoom(@PathVariable Long adminUserId){
        List<Hotel> hotels=hotelService.getHotelsForAdmin(adminUserId);
        return  ResponseEntity.ok(hotels);
    }

    @PostMapping("/adminAddRoom")
    public ResponseEntity<String> addRoom(@RequestBody RoomDto roomDto){
        try{


            roomService.addRoom(roomDto);
            return new ResponseEntity<>("Room added successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to addRoom",HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }




}
