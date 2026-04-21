package com.hotel_booking_system.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HotelRequest {

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
    
}
