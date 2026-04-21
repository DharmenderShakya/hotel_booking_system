package com.hotel_booking_system.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.hotel_booking_system.response.ApiResponse;

public interface HotelImageService {

	public ResponseEntity<ApiResponse<Object>> uploadHotelImage(MultipartFile myFile);
	
	public ResponseEntity<ApiResponse<Object>> uploadHotelRoomImage(MultipartFile myFile);
	
	public ResponseEntity<ApiResponse<Object>> deleteImageById(MultipartFile myFile);
	
}
