package com.hotel_booking_system.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hotel_booking_system.entity.Hotels;

@Repository
public interface HotelRepository extends JpaRepository<Hotels, Long>{

	public List<Hotels> findByCityIgnoreCase(String city);
	
	@Query("""
		    SELECT DISTINCT h FROM Hotels h
		    WHERE (:city IS NULL OR LOWER(h.city) = LOWER(:city))
		    AND NOT EXISTS (
		        SELECT b FROM Bookings b
		        WHERE b.hotel = h
		        AND b.checkIn < :checkOut
		        AND b.checkOut > :checkIn
		    )
		""")
		List<Hotels> searchAvailableHotels(
		        String city,
		        LocalDate checkIn,
		        LocalDate checkOut
		);
	
}
