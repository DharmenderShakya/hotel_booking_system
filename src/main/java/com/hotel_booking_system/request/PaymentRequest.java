package com.hotel_booking_system.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hotel_booking_system.entity.Bookings;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequest {

    private Long paymentId;

    private BigDecimal amount;
    
    private String paymentMethod;

    private String status;

    private String transactionId;

    private LocalDateTime paymentDate;
    
    private Long bookingId;
    
    private String userName;
    
    private Long userId;
}
