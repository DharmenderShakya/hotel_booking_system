package com.hotel_booking_system.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomsRequest {
	
	    private Long id;

	    private Long hotelId;

	    private String roomType;

	    private BigDecimal price;

	    private Integer capacity;

	    private Integer totalRooms;

	    private String description;

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;
	    
	    private Long roomNumber;
}
