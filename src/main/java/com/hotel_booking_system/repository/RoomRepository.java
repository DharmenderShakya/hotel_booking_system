package com.hotel_booking_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel_booking_system.entity.Rooms;

public interface RoomRepository extends JpaRepository<Rooms, Long> {
	
	public List<Rooms> findByHotels_Id(Long hotelId);

}
