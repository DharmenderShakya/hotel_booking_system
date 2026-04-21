package com.hotel_booking_system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel_booking_system.configuration.JwtFilter;
import com.hotel_booking_system.configuration.SocialAppService;
import com.hotel_booking_system.configuration.UserInfoService;
import com.hotel_booking_system.controller.AuthController;
import com.hotel_booking_system.controller.RoomController;
import com.hotel_booking_system.request.RoomsRequest;
import com.hotel_booking_system.response.RoomsResponse;
import com.hotel_booking_system.service.RoomService;
import com.hotel_booking_system.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RoomController.class,
excludeAutoConfiguration = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class

})
@AutoConfigureMockMvc(addFilters = false)
public class RoomTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private UserService userService;
    @MockBean private JwtFilter jwtFilter;
    @MockBean private UserInfoService userInfoService;
    @MockBean private SocialAppService socialAppService;
    @MockBean
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ Mock user
    private Principal mockPrincipal() {
        return () -> "test@gmail.com";
    }

    // ✅ Create Room
    @Test
    void testCreateRoom() throws Exception {

        RoomsRequest request = new RoomsRequest();
        request.setHotelId(1L);
        request.setRoomNumber(101L);
        request.setRoomType("DELUXE");
        request.setPrice(BigDecimal.valueOf(500.0)); // ✅ FIX
        request.setCapacity(2);

        Mockito.when(roomService.createRoom(Mockito.any(), Mockito.anyString()))
                .thenReturn(new RoomsResponse());

        mockMvc.perform(post("/api/rooms")
                .principal(mockPrincipal())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Get Room By ID
    @Test
    void testGetRoomById() throws Exception {

        Mockito.when(roomService.getRoomById(1L))
                .thenReturn(new RoomsResponse());

        mockMvc.perform(get("/api/rooms/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Update Room
    @Test
    void testUpdateRoom() throws Exception {

        RoomsRequest request = new RoomsRequest();
        request.setRoomNumber(102L);
        request.setRoomType("STANDARD");
        request.setPrice(BigDecimal.valueOf(1500.0)); // ✅ FIX
        request.setCapacity(2);

        Mockito.when(roomService.updateRoom(Mockito.any(), Mockito.eq(1L), Mockito.anyString()))
                .thenReturn(new RoomsResponse());

        mockMvc.perform(put("/api/rooms/{id}", 1L)
                .principal(mockPrincipal())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Delete Room
    @Test
    void testDeleteRoom() throws Exception {

        Mockito.when(roomService.deleteRoomBy(Mockito.eq(1L), Mockito.anyString()))
                .thenReturn(new RoomsResponse());

        mockMvc.perform(delete("/api/rooms/{id}", 1L)
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Get Rooms By Hotel
    @Test
    void testGetRoomsByHotel() throws Exception {

        List<RoomsResponse> list = List.of(new RoomsResponse());

        Mockito.when(roomService.getRoomByHotelId(Mockito.eq(1L), Mockito.anyString()))
                .thenReturn(list);

        mockMvc.perform(get("/api/rooms/hotel/{hotelId}", 1L)
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ✅ Exception Case
    @Test
    void testRoom_NotFound() throws Exception {

        Mockito.when(roomService.getRoomById(1L))
                .thenThrow(new RuntimeException("Room not found"));

        mockMvc.perform(get("/api/rooms/{id}", 1L))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}