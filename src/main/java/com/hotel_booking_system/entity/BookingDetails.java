package com.hotel_booking_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "booking_details")
@Getter
@Setter
public class BookingDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Bookings bookings;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms romRooms;
    
    @Column(name = "price_per_night")
    private BigDecimal pricePerNight;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

}
