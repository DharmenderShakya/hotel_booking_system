package com.hotel_booking_system.serviceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hotel_booking_system.response.ApiResponse;
import com.hotel_booking_system.service.HotelImageService;

@Service
public class HotelImageServiceImpl implements HotelImageService {

	@Override
	public ResponseEntity<ApiResponse<Object>> uploadHotelImage(MultipartFile myFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ApiResponse<Object>> uploadHotelRoomImage(MultipartFile myFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ApiResponse<Object>> deleteImageById(MultipartFile myFile) {
		// TODO Auto-generated method stub
		return null;
	}

}
