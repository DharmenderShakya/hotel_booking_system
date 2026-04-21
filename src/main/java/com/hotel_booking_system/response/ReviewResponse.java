package com.hotel_booking_system.response;

import java.time.LocalDateTime;

import com.hotel_booking_system.entity.Hotels;
import com.hotel_booking_system.entity.Review;
import com.hotel_booking_system.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
	private Long id;

    private Hotels hotels;

    private Users users;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;
    
    private Long hotelId;
    
    private String hotelName;
    
    private Long userId;
    
    private String userName;
    
    public ReviewResponse(Review review) {

        this.id = review.getId();

        this.hotelId = review.getHotels().getId();
        
        this.hotelName = review.getHotels().getName();

        this.userId = review.getUsers().getId();
        
        this.userName = review.getUsers().getUserName();

        this.rating = review.getRating();
        
        this.comment = review.getComment();
        
        this.createdAt = review.getCreatedAt();
    }
}
