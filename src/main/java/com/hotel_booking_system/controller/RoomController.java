package com.hotel_booking_system.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hotel_booking_system.request.RoomsRequest;
import com.hotel_booking_system.response.RoomsResponse;
import com.hotel_booking_system.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Create Room
    @PostMapping
    public RoomsResponse createRoom(@RequestBody RoomsRequest request,
                                    Principal principal) {
        String userName = principal.getName();
        return roomService.createRoom(request, userName);
    }

    // Update Room
    @PutMapping("/{id}")
    public RoomsResponse updateRoom(@PathVariable Long id,
                                    @RequestBody RoomsRequest request,
                                    Principal principal) {
        String userName = principal.getName();
        return roomService.updateRoom(request, id, userName);
    }

    //  Delete Room
    @DeleteMapping("/{id}")
    public RoomsResponse deleteRoom(@PathVariable Long id,
                                    Principal principal) {
        String userName = principal.getName();
        return roomService.deleteRoomBy(id, userName);
    }

    //  Get Room by ID
    @GetMapping("/{id}")
    public RoomsResponse getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    // Get Rooms by Hotel (Owner only)
    @GetMapping("/hotel/{hotelId}")
    public List<RoomsResponse> getRoomsByHotel(@PathVariable Long hotelId,
                                               Principal principal) {
        String userName = principal.getName();
        return roomService.getRoomByHotelId(hotelId, userName);
    }
}