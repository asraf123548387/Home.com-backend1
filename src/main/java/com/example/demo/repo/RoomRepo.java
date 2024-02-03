package com.example.demo.repo;

import com.example.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room,Long> {
    List<Room> findByHotel_HotelId(Long hotelId);

    List<Room> findByRoomNumberContainingIgnoreCase(String search);
}
