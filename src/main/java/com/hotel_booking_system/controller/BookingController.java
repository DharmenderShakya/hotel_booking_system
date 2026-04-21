package com.hotel_booking_system.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hotel_booking_system.request.BookingRequest;
import com.hotel_booking_system.response.BookingResponse;
import com.hotel_booking_system.service.BookingService;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Get all bookings for logged-in user
    @GetMapping("/all")
    public List<BookingResponse> getAllBookings(Principal principal) {
        String userName = principal.getName();
        return bookingService.getAllBooking(null, userName);
    }

    // 2. Get booking by ID
    @GetMapping("/{id}")
    public BookingResponse getBookingById(@PathVariable Long id,
                                          Principal principal) {
        String userName = principal.getName();
        return bookingService.getBookingById(id, userName);
    }

    // 3. Create booking
    @PostMapping("/create")
    public BookingResponse createBooking(@RequestBody BookingRequest request,
                                         Principal principal) {
        String userName = principal.getName();
        return bookingService.createBooking(request, userName);
    }

    // 4. Cancel booking
    @PutMapping("/cancel/{id}")
    public BookingResponse cancelBooking(@PathVariable Long id,
                                         Principal principal) {
        String userName = principal.getName();
        return bookingService.getCancelBookingByIdByUsers(id, userName);
    }
}