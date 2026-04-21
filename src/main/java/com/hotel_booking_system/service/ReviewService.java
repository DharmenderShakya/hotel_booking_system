package com.hotel_booking_system.service;

import java.util.List;

import com.hotel_booking_system.request.ReviewRequest;
import com.hotel_booking_system.response.ReviewResponse;

public interface ReviewService {

	public ReviewResponse createReview(ReviewRequest reviewRequest,String userName);
	
	public ReviewResponse updateReviewById(Long id,ReviewRequest reviewRequest,String userName);
	
	public ReviewResponse deleteReviewById(Long id,String userName); 
	
	public List<ReviewResponse> getReviewByOwnerId(Long id,String userName); 
	
//	GET    /api/admin/reviews              → View all reviews
//	DELETE /api/admin/reviews/{id}         → Remove inappropriate review
//	GET    /api/admin/dashboard            → System stats
	
}
