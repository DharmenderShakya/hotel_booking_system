package com.hotel_booking_system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel_booking_system.configuration.JwtFilter;
import com.hotel_booking_system.configuration.SocialAppService;
import com.hotel_booking_system.configuration.UserInfoService;
import com.hotel_booking_system.controller.BookingController;
import com.hotel_booking_system.controller.HotelController;
import com.hotel_booking_system.request.BookingRequest;
import com.hotel_booking_system.response.BookingResponse;
import com.hotel_booking_system.service.BookingService;
import com.hotel_booking_system.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
	    controllers = BookingController.class,
	    excludeAutoConfiguration = {
	        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
	        org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class,
	        org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class
	    }
	)
@AutoConfigureMockMvc(addFilters = false)
public class BookingTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private UserService userService;
    @MockBean private JwtFilter jwtFilter;
    @MockBean private UserInfoService userInfoService;
    @MockBean private SocialAppService socialAppService;
    @MockBean private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ Mock logged-in user
    private Principal mockPrincipal() {
        return () -> "test@gmail.com";
    }

    // ✅ Get All Bookings
    @Test
    void testGetAllBookings() throws Exception {

        List<BookingResponse> bookings = List.of(new BookingResponse());

        Mockito.when(bookingService.getAllBooking(Mockito.isNull(), Mockito.anyString()))
                .thenReturn(bookings);

        mockMvc.perform(get("/api/booking/all")
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ✅ Get Booking By ID
    @Test
    void testGetBookingById() throws Exception {

        Mockito.when(bookingService.getBookingById(Mockito.eq(1L), Mockito.anyString()))
                .thenReturn(new BookingResponse());

        mockMvc.perform(get("/api/booking/{id}", 1L)
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Create Booking
    @Test
    void testCreateBooking() throws Exception {

        BookingRequest request = new BookingRequest();
        request.setHotelId(1L);
        request.setRoomId(1L);

        Mockito.when(bookingService.createBooking(Mockito.any(), Mockito.anyString()))
                .thenReturn(new BookingResponse());

        mockMvc.perform(post("/api/booking/create")
                .principal(mockPrincipal())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Cancel Booking
    @Test
    void testCancelBooking() throws Exception {

        Mockito.when(
                bookingService.getCancelBookingByIdByUsers(Mockito.eq(1L), Mockito.anyString())
        ).thenReturn(new BookingResponse());

        mockMvc.perform(put("/api/booking/cancel/{id}", 1L)
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Exception Case
    @Test
    void testGetBooking_NotFound() throws Exception {

        Mockito.when(bookingService.getBookingById(Mockito.eq(1L), Mockito.anyString()))
                .thenThrow(new RuntimeException("Booking not found"));

        mockMvc.perform(get("/api/booking/{id}", 1L)
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}