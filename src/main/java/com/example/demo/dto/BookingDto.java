package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private String guestName;
    private String email;
    private String mobileNumber;
    private Date checkInDate;
    private Date checkOutDate;
    private Double totalPrice;
    private Long roomId;
    private Long userId;
}
