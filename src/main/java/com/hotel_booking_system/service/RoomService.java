package com.hotel_booking_system.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hotel_booking_system.entity.RoomImages;
import com.hotel_booking_system.request.RoomsRequest;
import com.hotel_booking_system.response.RoomsResponse;

public interface RoomService {

	public RoomsResponse createRoom(RoomsRequest request,String userName);
	
	public RoomsResponse updateRoom(RoomsRequest request,Long id,String userName);
	
	public RoomsResponse getRoomById(Long Id);
	
	public RoomsResponse deleteRoomBy(Long Id,String userName);
	
	public List<RoomsResponse> getRoomByHotelId(Long Id,String userName);
	
	public RoomsResponse saveRoomImages(MultipartFile muFile,RoomsRequest request);
	
}
