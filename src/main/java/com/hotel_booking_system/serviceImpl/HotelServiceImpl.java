package com.hotel_booking_system.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hotel_booking_system.entity.Hotels;
import com.hotel_booking_system.entity.Review;
import com.hotel_booking_system.entity.Rooms;
import com.hotel_booking_system.entity.Users;
import com.hotel_booking_system.exception.BadRequestException;
import com.hotel_booking_system.exception.UnauthoriseException;
import com.hotel_booking_system.repository.HotelRepository;
import com.hotel_booking_system.repository.ReviewRepository;
import com.hotel_booking_system.repository.RoomRepository;
import com.hotel_booking_system.repository.UsersRepository;
import com.hotel_booking_system.request.HotelRequest;
import com.hotel_booking_system.response.HotelResponse;
import com.hotel_booking_system.response.ReviewResponse;
import com.hotel_booking_system.response.RoomsResponse;
import com.hotel_booking_system.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private HotelRepository hotRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Override
	@Cacheable(value = "hotels", key = "#city")
	public List<HotelResponse> searchHotel(HotelRequest raRequest) {
		
		List<Hotels> hotels;
		
		if(raRequest.getCity()!=null && !raRequest.getCity().isEmpty()) {
			hotels=hotRepository.findByCityIgnoreCase(raRequest.getCity());
		}else {
			hotels=hotRepository.findAll();
		}
		
	    return hotels.stream()
	            .map(HotelResponse::new)
	            .toList();
	}

	@Override
	public HotelResponse getHotelById(Long id) {
		Hotels hotels=hotRepository.findById(id).orElseThrow(()->new BadRequestException("Hotel Details is not found"));
		return new HotelResponse(hotels);
	}

	@Override
	public List<RoomsResponse> getRoomsByHotelId(Long id) {
		
		List<Rooms> rooms=roomRepository.findByHotels_Id(id);
		
	    return rooms.stream()
	            .map(RoomsResponse::new)
	            .toList();
	}

	@Override
	public List<ReviewResponse> getReviewsByHotelId(Long id) {
		List<Review> reviews=reviewRepository.findReviewByHotels_Id(id);
		
	    return reviews.stream()
	            .map(ReviewResponse::new)
	            .toList();
	}

	@Override
	@CacheEvict(value = "hotels", allEntries = true)
	public HotelResponse createHotel(HotelRequest hotelRequest, String userName) {

	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));

	    Hotels hotel = new Hotels();

	    hotel.setOwnerId(user.getId());


	    hotel.setName(hotelRequest.getName());
	    hotel.setDescription(hotelRequest.getDescription());

	    hotel.setLocation(hotelRequest.getLocation());
	    hotel.setCity(hotelRequest.getCity());
	    hotel.setState(hotelRequest.getState());
	    hotel.setCountry(hotelRequest.getCountry());
	    hotel.setZipCode(hotelRequest.getZipCode());

	    hotel.setStarRating(hotelRequest.getStarRating());

	    hotel.setContactEmail(hotelRequest.getContactEmail());
	    hotel.setContactPhone(hotelRequest.getContactPhone());

	    hotel.setCreatedAt(LocalDateTime.now());
	    hotel.setUpdatedAt(LocalDateTime.now());

	    Hotels savedHotel = hotRepository.save(hotel);

	    return new HotelResponse(savedHotel);
	}

	@Override
	public HotelResponse updateHotel(Long id, HotelRequest request, String userName) {

	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));

	    Hotels hotel = hotRepository.findById(id)
	            .orElseThrow(() -> new BadRequestException("Hotel not found"));

	    if (!hotel.getOwnerId().equals(user.getId())) {
	        throw new UnauthoriseException("You are not allowed to update this hotel");
	    }

	    if (request.getName() != null) {
	        hotel.setName(request.getName());
	    }

	    if (request.getDescription() != null) {
	        hotel.setDescription(request.getDescription());
	    }

	    if (request.getLocation() != null) {
	        hotel.setLocation(request.getLocation());
	    }

	    if (request.getCity() != null) {
	        hotel.setCity(request.getCity());
	    }

	    if (request.getState() != null) {
	        hotel.setState(request.getState());
	    }

	    if (request.getCountry() != null) {
	        hotel.setCountry(request.getCountry());
	    }

	    if (request.getZipCode() != null) {
	        hotel.setZipCode(request.getZipCode());
	    }

	    if (request.getStarRating() != null) {
	        hotel.setStarRating(request.getStarRating());
	    }

	    if (request.getContactEmail() != null) {
	        hotel.setContactEmail(request.getContactEmail());
	    }

	    if (request.getContactPhone() != null) {
	        hotel.setContactPhone(request.getContactPhone());
	    }

	    hotel.setUpdatedAt(LocalDateTime.now());

	    Hotels updatedHotel = hotRepository.save(hotel);
	    
	    return new HotelResponse(updatedHotel);
	}

	@Override
	public HotelResponse deleteHotelById(Long id,String userName) {
		
	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));

	    Hotels hotel = hotRepository.findById(id)
	            .orElseThrow(() -> new BadRequestException("Hotel not found"));

	    if (!hotel.getOwnerId().equals(user.getId())) {
	        throw new UnauthoriseException("You are not allowed to update this hotel");
	    }
	    
	    hotel.setDeletedStatus(true);
	    
	    hotRepository.save(hotel);
	    
		return new HotelResponse(hotel);
	}

}
