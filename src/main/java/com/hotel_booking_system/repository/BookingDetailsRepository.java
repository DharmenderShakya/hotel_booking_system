package com.hotel_booking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel_booking_system.entity.BookingDetails;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Long>{

}
