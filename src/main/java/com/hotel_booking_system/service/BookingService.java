package com.hotel_booking_system.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel_booking_system.request.BookingRequest;
import com.hotel_booking_system.response.ApiResponse;
import com.hotel_booking_system.response.BookingResponse;

public interface BookingService {
	
	// for user
	
	public List<BookingResponse> getAllBooking(BookingRequest bRequest,String userName);
	
	public BookingResponse getBookingById(Long Id,String userName);
	
	public BookingResponse createBooking(BookingRequest bRequest,String userName);
	
	public BookingResponse getCancelBookingByIdByUsers(Long Id,String userName);
	
	
//	GET    /api/admin/bookings             → View all bookings
//	GET    /api/admin/bookings/{id}        → Booking details
	
	

	
	
}
