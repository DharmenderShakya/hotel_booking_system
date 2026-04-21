package com.hotel_booking_system.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hotel_booking_system.request.ReviewRequest;
import com.hotel_booking_system.response.ReviewResponse;
import com.hotel_booking_system.service.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Create review
    @PostMapping
    public ReviewResponse createReview(@RequestBody ReviewRequest request,
                                       Principal principal) {
        String userName = principal.getName();
        return reviewService.createReview(request, userName);
    }

    // Update review
    @PutMapping("/{id}")
    public ReviewResponse updateReview(@PathVariable Long id,
                                       @RequestBody ReviewRequest request,
                                       Principal principal) {
        String userName = principal.getName();
        return reviewService.updateReviewById(id, request, userName);
    }

    // Delete review (soft delete)
    @DeleteMapping("/{id}")
    public ReviewResponse deleteReview(@PathVariable Long id,
                                       Principal principal) {
        String userName = principal.getName();
        return reviewService.deleteReviewById(id, userName);
    }

    // Get reviews for hotel (owner only)
    @GetMapping("/hotel/{hotelId}")
    public List<ReviewResponse> getReviewsByHotelOwner(@PathVariable Long hotelId,
                                                       Principal principal) {
        String userName = principal.getName();
        return reviewService.getReviewByOwnerId(hotelId, userName);
    }
}