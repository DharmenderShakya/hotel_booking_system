package com.hotel_booking_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel_booking_system.entity.Bookings;

@Repository
public interface BookingRepository extends JpaRepository<Bookings, Long> {

	public List<Bookings> findByUser_Id(Long userId);
}
