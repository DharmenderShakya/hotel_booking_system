package com.hotel_booking_system.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.hotel_booking_system.entity.Bookings;
import com.hotel_booking_system.entity.Hotels;
import com.hotel_booking_system.entity.Rooms;
import com.hotel_booking_system.entity.Users;
import com.hotel_booking_system.enums.BookingStatus;
import com.hotel_booking_system.exception.BadRequestException;
import com.hotel_booking_system.repository.BookingRepository;
import com.hotel_booking_system.repository.HotelRepository;
import com.hotel_booking_system.repository.RoomRepository;
import com.hotel_booking_system.repository.UsersRepository;
import com.hotel_booking_system.request.BookingRequest;
import com.hotel_booking_system.response.BookingResponse;
import com.hotel_booking_system.service.BookingService;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {
	
	@Autowired
	private BookingRepository boRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private HotelRepository hoRepository;
	
	@Autowired
	private RoomRepository romRepository;

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	public List<BookingResponse> getAllBooking(BookingRequest bRequest,String userName) {
		
	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));
		
		List<Bookings> booList= boRepository.findByUser_Id(user.getId());
		
	    return booList.stream()
	            .map(BookingResponse::new)
	            .toList();
	}

	@Override
	public BookingResponse getBookingById(Long Id,String userName) {
		
	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));
		
		Bookings bookings= boRepository.findById(Id).orElseThrow(() -> new BadRequestException("User not found"));
		
	    return new BookingResponse(bookings);
	    
	}

	@Override
	@Transactional
	public BookingResponse createBooking(BookingRequest bRequest,String userName) {
		
	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));
	    
	    Hotels hotels=hoRepository.findById(bRequest.getHotelId()).orElseThrow(()->new BadRequestException("hotel Not Found"));
	    
	    Rooms romRooms=romRepository.findById(bRequest.getRoomId()).orElseThrow(()-> new BadRequestException("Room is not available"));
	    
	    if (bRequest.getCheckIn().isAfter(bRequest.getCheckOut())) {
	        throw new BadRequestException("Check-in cannot be after check-out"); 
	    }
	    
	    Bookings bookings=new Bookings();
	    
	    bookings.setUser(user);
	    
	    bookings.setHotel(hotels);
	    
	    bookings.setRoom(romRooms);
	    
	    bookings.setCheckIn(bRequest.getCheckIn());
	    
	    bookings.setCheckOut(bRequest.getCheckOut());
	    
	    bookings.setTotalAmount(bRequest.getTotalAmount());
	    
	    bookings.setCreatedAt(LocalDateTime.now());
	    
	    bookings.setStatus(BookingStatus.CONFIRMED);
	    
	    boRepository.save(bookings);
	    
		return new BookingResponse(bookings);
	}

	@Override
	public BookingResponse getCancelBookingByIdByUsers(Long Id,String userName) {
		
	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));
		
		Bookings bookings=boRepository.findById(Id).orElseThrow(()->new BadRequestException("booking Details not Found"));
		
	    if (BookingStatus.CANCELLED.equals(bookings.getStatus())) {
	        throw new BadRequestException("Booking already cancelled");
	    }
	    
		bookings.setCancelStatus(true);
		
		bookings.setUpdatedAt(LocalDateTime.now());
		
		bookings.setStatus(BookingStatus.CANCELLED);
		
		boRepository.save(bookings);
		
		return new BookingResponse(bookings);
	}

}
