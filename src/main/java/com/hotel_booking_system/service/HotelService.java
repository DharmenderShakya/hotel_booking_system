package com.hotel_booking_system.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel_booking_system.request.HotelRequest;
import com.hotel_booking_system.response.ApiResponse;
import com.hotel_booking_system.response.HotelResponse;
import com.hotel_booking_system.response.ReviewResponse;
import com.hotel_booking_system.response.RoomsResponse;

public interface HotelService {

	// search hotel by location ,date and filter
	public List<HotelResponse> searchHotel(HotelRequest raRequest);
	
	public HotelResponse getHotelById(Long id);
	
	public List<RoomsResponse> getRoomsByHotelId(Long id);
	
	public List<ReviewResponse> getReviewsByHotelId(Long id);
	
	// owner flow
	
	public HotelResponse createHotel(HotelRequest hotelRequest,String userName);
	
	public HotelResponse updateHotel(Long id,HotelRequest hotelRequest,String userName); 
	
	public HotelResponse deleteHotelById(Long id,String userName); 
	
//	GET    /api/admin/hotels               → View all hotels
//	PUT    /api/admin/hotels/{id}/approve  → Approve hotel
//	PUT    /api/admin/hotels/{id}/reject   → Reject hotel
//	DELETE /api/admin/hotels/{id}          → Remove hotel
	
}
