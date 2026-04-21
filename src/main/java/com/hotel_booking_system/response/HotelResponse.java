package com.hotel_booking_system.response;

import java.time.LocalDateTime;

import com.hotel_booking_system.entity.Hotels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {

	private Long id;

    private Long ownerId;

    private String name;

    private String location;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    private String description;

    private Integer starRating;

    private String contactEmail;

    private String contactPhone;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    private boolean deleteStatus;
    
    public HotelResponse(Hotels hRequest) {
        this.id = hRequest.getId();
        this.ownerId = hRequest.getOwnerId();
        this.name = hRequest.getName();
        this.location = hRequest.getLocation();
        this.city = hRequest.getCity();
        this.state = hRequest.getState();
        this.country = hRequest.getCountry();
        this.zipCode = hRequest.getZipCode();
        this.description = hRequest.getDescription();
        this.starRating = hRequest.getStarRating();
        this.contactEmail = hRequest.getContactEmail();
        this.contactPhone = hRequest.getContactPhone();
        
//        set timestamps (depends on your logic)
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.deleteStatus=hRequest.isDeletedStatus();
    }
	
}
