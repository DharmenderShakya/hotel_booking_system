package com.hotel_booking_system.request;

import java.time.LocalDateTime;

import com.hotel_booking_system.entity.Hotels;
import com.hotel_booking_system.entity.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
	
	    private Long id;

	    private Hotels hotels;
	    
	    private Long hotel_id;

	    private Users users;

	    private Integer rating;

	    private String comment;

	    private LocalDateTime createdAt;
}
