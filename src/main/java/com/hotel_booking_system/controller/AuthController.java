package com.hotel_booking_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel_booking_system.request.RegistrationRequest;
import com.hotel_booking_system.response.ApiResponse;
import com.hotel_booking_system.response.LoginReponse;
import com.hotel_booking_system.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/userRegistration")
	public ResponseEntity<?> addUser(@Valid @RequestBody RegistrationRequest resRequest){
				userService.addUser(resRequest);
				return ResponseEntity.ok("{\"ok\":\"ok\"}");
	}  
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginReponse>> doLogin(@RequestBody RegistrationRequest resRequest){
		
		return userService.doLogin(resRequest);
	}
}
