package com.hotel_booking_system.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefundRequest {
	
    private Long id;

    private BigDecimal amount;

    private String status;

    private LocalDateTime refundDate;
}
