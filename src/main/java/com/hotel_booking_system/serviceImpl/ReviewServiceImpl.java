package com.hotel_booking_system.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hotel_booking_system.entity.Hotels;
import com.hotel_booking_system.entity.Review;
import com.hotel_booking_system.entity.Users;
import com.hotel_booking_system.exception.BadRequestException;
import com.hotel_booking_system.exception.UnauthoriseException;
import com.hotel_booking_system.repository.HotelRepository;
import com.hotel_booking_system.repository.ReviewRepository;
import com.hotel_booking_system.repository.UsersRepository;
import com.hotel_booking_system.request.ReviewRequest;
import com.hotel_booking_system.response.ReviewResponse;
import com.hotel_booking_system.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private ReviewRepository reviRepository;
	
	@Override
	public ReviewResponse createReview(ReviewRequest reviewRequest, String userName) {
		
		Users user=usersRepository.findByUserName(userName).orElseThrow(()->new BadRequestException("User Not Found"));
		
		Hotels hotels=hotelRepository.findById(reviewRequest.getHotel_id()).orElseThrow(()->new BadRequestException("Hotel Not Found"));
		
		Review review=new Review();
		
		review.setRating(reviewRequest.getRating());
		
		review.setComment(reviewRequest.getComment());
		
		review.setCreatedAt(LocalDateTime.now());
		
		review.setUsers(user);
		
		review.setHotels(hotels);
		
		reviRepository.save(review);
		
		return new ReviewResponse(review);
	}

	@Override
	public ReviewResponse updateReviewById(Long id,ReviewRequest reviewRequest, String userName) {
		
		Users user=usersRepository.findByUserName(userName).orElseThrow(()->new BadRequestException("User Not Found"));
		
		Review review=reviRepository.findById(id).orElseThrow(()->new BadRequestException("Hotel Not Found"));
		
		review.setComment(reviewRequest.getComment());
		
		review.setRating(reviewRequest.getRating());
		
		review.setUpdateAt(LocalDateTime.now());
		
		reviRepository.save(review);
		
		return new ReviewResponse(review);
	}

	@Override
	public ReviewResponse deleteReviewById(Long id, String userName) {
		
		Users user=usersRepository.findByUserName(userName).orElseThrow(()->new BadRequestException("User Not Found"));
		
		Review review=reviRepository.findById(id).orElseThrow(()->new BadRequestException("Hotel Not Found"));
		
		review.setDeleteStatus(true);
		
		review.setUpdateAt(LocalDateTime.now());
		
		reviRepository.save(review);
		
		return new ReviewResponse(review);
	}

	@Override
	public List<ReviewResponse> getReviewByOwnerId(Long hotelId, String userName) {

	    Users user = usersRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));

	    Hotels hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new BadRequestException("Hotel not found"));
	    
	    if (!hotel.getOwnerId().equals(user.getId())) {
	        throw new UnauthoriseException("You are not authorized to view reviews of this hotel");
	    }

	    List<Review> reviews = reviRepository.findReviewByHotels_Id(hotelId);

	    return reviews.stream()
	            .map(ReviewResponse::new)
	            .toList();
	}

}
