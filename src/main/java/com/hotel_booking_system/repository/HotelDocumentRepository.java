package com.hotel_booking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel_booking_system.entity.HotelDocument;

public interface HotelDocumentRepository extends JpaRepository<HotelDocument, Long> {

	HotelDocument findHotelDocumentByDocId(String docId);
	
}
