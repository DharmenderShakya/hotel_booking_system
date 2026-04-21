package com.hotel_booking_system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel_booking_system.configuration.JwtFilter;
import com.hotel_booking_system.configuration.SocialAppService;
import com.hotel_booking_system.configuration.UserInfoService;
import com.hotel_booking_system.controller.HotelController;
import com.hotel_booking_system.request.HotelRequest;
import com.hotel_booking_system.response.HotelResponse;
import com.hotel_booking_system.response.ReviewResponse;
import com.hotel_booking_system.response.RoomsResponse;
import com.hotel_booking_system.service.HotelService;
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

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(
    controllers = HotelController.class,
    excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class
    }
)
public class HotelTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private UserService userService;
    @MockBean private JwtFilter jwtFilter;
    @MockBean private UserInfoService userInfoService;
    @MockBean private SocialAppService socialAppService;
    @MockBean private HotelService hotelService;

    @Autowired
    private ObjectMapper objectMapper;

    // Mock logged-in user
    private Principal mockPrincipal() {
        return () -> "owner@gmail.com";
    }

    //  Search Hotels
    @Test
    void testSearchHotels() throws Exception {

        List<HotelResponse> hotels = List.of(new HotelResponse());

        Mockito.when(hotelService.searchHotel(Mockito.any()))
                .thenReturn(hotels);

        mockMvc.perform(post("/api/hotels/search")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new HotelRequest())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    //  Get Hotel By ID
    @Test
    void testGetHotelById() throws Exception {

        Mockito.when(hotelService.getHotelById(1L))
                .thenReturn(new HotelResponse());

        mockMvc.perform(get("/api/hotels/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //  Get Rooms By Hotel ID
    @Test
    void testGetRoomsByHotelId() throws Exception {

        List<RoomsResponse> rooms = List.of(new RoomsResponse());

        Mockito.when(hotelService.getRoomsByHotelId(1L))
                .thenReturn(rooms);

        mockMvc.perform(get("/api/hotels/1/rooms"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    //  Get Reviews By Hotel ID
    @Test
    void testGetReviewsByHotelId() throws Exception {

        List<ReviewResponse> reviews = List.of(new ReviewResponse());

        Mockito.when(hotelService.getReviewsByHotelId(1L))
                .thenReturn(reviews);

        mockMvc.perform(get("/api/hotels/1/reviews"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    //  Create Hotel
    @Test
    void testCreateHotel() throws Exception {

        HotelRequest request = new HotelRequest();
        request.setName("Test Hotel");

        Mockito.when(hotelService.createHotel(Mockito.any(), Mockito.anyString()))
                .thenReturn(new HotelResponse());

        mockMvc.perform(post("/api/hotels")
                .principal(mockPrincipal())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //  Update Hotel
    @Test
    void testUpdateHotel() throws Exception {

        HotelRequest request = new HotelRequest();
        request.setName("Updated Hotel");

        Mockito.when(hotelService.updateHotel(Mockito.eq(1L), Mockito.any(), Mockito.anyString()))
                .thenReturn(new HotelResponse());

        mockMvc.perform(put("/api/hotels/1")
                .principal(mockPrincipal())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //  Delete Hotel
    @Test
    void testDeleteHotel() throws Exception {

        Long hotelId = 1L;

        Mockito.when(hotelService.deleteHotelById(Mockito.eq(hotelId), Mockito.anyString()))
                .thenReturn(new HotelResponse());

        mockMvc.perform(delete("/api/hotels/{id}", hotelId)
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //  Exception Case
    @Test
    void testGetHotel_NotFound() throws Exception {

        Mockito.when(hotelService.getHotelById(1L))
                .thenThrow(new RuntimeException("Hotel not found"));

        mockMvc.perform(get("/api/hotels/1"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}