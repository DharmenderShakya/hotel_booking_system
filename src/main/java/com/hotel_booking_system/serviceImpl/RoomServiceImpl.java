package com.hotel_booking_system.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hotel_booking_system.entity.Hotels;
import com.hotel_booking_system.entity.Rooms;
import com.hotel_booking_system.entity.Users;
import com.hotel_booking_system.exception.BadRequestException;
import com.hotel_booking_system.exception.UnauthoriseException;
import com.hotel_booking_system.repository.HotelRepository;
import com.hotel_booking_system.repository.RoomRepository;
import com.hotel_booking_system.repository.UsersRepository;
import com.hotel_booking_system.request.RoomsRequest;
import com.hotel_booking_system.response.RoomsResponse;
import com.hotel_booking_system.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private RoomRepository roomRepository;

	@Override
	public RoomsResponse getRoomById(Long id) {

	    Rooms room = roomRepository.findById(id)
	            .orElseThrow(() -> new BadRequestException("Room not found"));

	    return new RoomsResponse(room);
	}

	@Override
	public RoomsResponse deleteRoomBy(Long id, String userName) {

	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));

	    Rooms room = roomRepository.findById(id)
	            .orElseThrow(() -> new BadRequestException("Room not found"));

	    if (!room.getHotels().getOwnerId().equals(user.getId())) {
	        throw new UnauthoriseException("You are not allowed to delete this room");
	    }

	    roomRepository.delete(room);

	    return new RoomsResponse(room);
	}

	@Override
	public List<RoomsResponse> getRoomByHotelId(Long hotelId, String userName) {

	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));

	    Hotels hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new BadRequestException("Hotel not found"));

	    if (!hotel.getOwnerId().equals(user.getId())) {
	        throw new UnauthoriseException("You are not allowed to view rooms of this hotel");
	    }

	    List<Rooms> rooms = roomRepository.findByHotels_Id(hotelId);

	    return rooms.stream()
	            .map(RoomsResponse::new)
	            .toList();
	}

	@Override
	public RoomsResponse createRoom(RoomsRequest request, String userName) {

	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));

	    Hotels hotel = hotelRepository.findById(request.getHotelId())
	            .orElseThrow(() -> new BadRequestException("Hotel not found"));

	    if (!hotel.getOwnerId().equals(user.getId())) {
	        throw new UnauthoriseException("You are not allowed to add rooms to this hotel");
	    }

	    Rooms room = new Rooms();
	    room.setRoomNumber(request.getRoomNumber());
	    room.setRoomType(request.getRoomType());
	    room.setPrice(request.getPrice());
	    room.setCapacity(request.getCapacity());
	    room.setAvailability(true);
	    roomRepository.save(room);

	    return new RoomsResponse(room);
	}

	@Override
	public RoomsResponse updateRoom(RoomsRequest request, Long id, String userName) {

	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));

	    Rooms room = roomRepository.findById(id)
	            .orElseThrow(() -> new BadRequestException("Room not found"));

	    if (!room.getHotels().getOwnerId().equals(user.getId())) {
	        throw new UnauthoriseException("You are not allowed to update this room");
	    }

	    room.setRoomNumber(request.getRoomNumber());
	    room.setRoomType(request.getRoomType());
	    room.setPrice(request.getPrice());
	    room.setCapacity(request.getCapacity());

	    roomRepository.save(room);

	    return new RoomsResponse(room);
	}

	@Override
	public RoomsResponse saveRoomImages(MultipartFile muFile, RoomsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
