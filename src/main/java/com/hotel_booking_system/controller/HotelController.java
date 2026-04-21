package com.hotel_booking_system.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hotel_booking_system.request.HotelRequest;
import com.hotel_booking_system.response.HotelResponse;
import com.hotel_booking_system.response.ReviewResponse;
import com.hotel_booking_system.response.RoomsResponse;
import com.hotel_booking_system.service.HotelService;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    // Search hotels (by city)
    @PostMapping("/search")
    public List<HotelResponse> searchHotels(@RequestBody HotelRequest request) {
        return hotelService.searchHotel(request);
    }

    // Get hotel by ID
    @GetMapping("/{id}")
    public HotelResponse getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    // Get rooms by hotel ID
    @GetMapping("/{id}/rooms")
    public List<RoomsResponse> getRoomsByHotelId(@PathVariable Long id) {
        return hotelService.getRoomsByHotelId(id);
    }

    // 4. Get reviews by hotel ID
    @GetMapping("/{id}/reviews")
    public List<ReviewResponse> getReviewsByHotelId(@PathVariable Long id) {
        return hotelService.getReviewsByHotelId(id);
    }

    //  5. Create hotel (only owner/admin)
    @PostMapping
    public HotelResponse createHotel(@RequestBody HotelRequest request,
                                     Principal principal) {
        String userName = principal.getName();
        return hotelService.createHotel(request, userName);
    }

    //  6. Update hotel
    @PutMapping("/{id}")
    public HotelResponse updateHotel(@PathVariable Long id,
                                     @RequestBody HotelRequest request,
                                     Principal principal) {
        String userName = principal.getName();
        return hotelService.updateHotel(id, request, userName);
    }

    //  7. Delete hotel (soft delete)
    @DeleteMapping("/{id}")
    public HotelResponse deleteHotel(@PathVariable Long id,
                                     Principal principal) {
        String userName = principal.getName();
        return hotelService.deleteHotelById(id, userName);
    }
}