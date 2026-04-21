package com.hotel_booking_system.entity;

import java.util.List;

import jakarta.persistence.*;

@Table(name = "amenities")
@Entity
public class Amenities {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "icon", length = 255)
    private String icon;

    @OneToMany(mappedBy = "amenities")
    private List<HotelAmenities> amenities;
}
