package com.hotel_booking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel_booking_system.entity.Amenities;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Long> {

}
