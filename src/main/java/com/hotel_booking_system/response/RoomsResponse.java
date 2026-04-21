package com.hotel_booking_system.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hotel_booking_system.entity.Rooms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomsResponse {

	private Long id;

    private Long hotelId;

    private String roomType;

    private BigDecimal price;

    private Integer capacity;

    private Integer totalRooms;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    public RoomsResponse(Rooms romRooms) {
    	
    }
	
}
