package com.hotel_booking_system.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hotel_booking_system.entity.Hotels;
import com.hotel_booking_system.entity.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {

    private Long bookingId;
    
    private Long hotelId;
    
    private Long roomId;

    private BigDecimal totalAmount;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    private boolean cancelStatus;

}
