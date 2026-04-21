package com.hotel_booking_system.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hotel_booking_system.entity.Bookings;
import com.hotel_booking_system.enums.BookingStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookingResponse {
	
	private Long id;

    private BigDecimal totalAmount;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private BookingStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    private boolean cancelStatus;
    
    public BookingResponse(Bookings booking) {
        this.id = booking.getId();
        this.totalAmount = booking.getTotalAmount();
        this.checkIn = booking.getCheckIn();
        this.checkOut = booking.getCheckOut();
        this.status = booking.getStatus();
        this.createdAt = booking.getCreatedAt();
        this.updatedAt = booking.getUpdatedAt();
        this.cancelStatus=booking.isCancelStatus();
    }
    
    
}
