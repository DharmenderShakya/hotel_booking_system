package com.hotel_booking_system.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotel_booking_system.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	public List<Review> findReviewByHotels_Id(Long id);

}
