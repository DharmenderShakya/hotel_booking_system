package com.hotel_booking_system.service;

import org.springframework.http.ResponseEntity;

import com.hotel_booking_system.request.RegistrationRequest;
import com.hotel_booking_system.response.ApiResponse;
import com.hotel_booking_system.response.LoginReponse;

public interface UserService {
	
	public void addUser(RegistrationRequest raRequest);
	
	public ResponseEntity<ApiResponse<LoginReponse>> doLogin(RegistrationRequest raRequest);
	
	public ResponseEntity<ApiResponse<Object>> addOwner(RegistrationRequest raRequest);
	
	
	
//	GET    /api/admin/users                → List all users
//	GET    /api/admin/users/{id}           → User details
//	PUT    /api/admin/users/{id}/status    → Activate/Block user
//	DELETE /api/admin/users/{id}           → Delete user
	
	
//	GET    /api/admin/owners               → List all owners
//	PUT    /api/admin/owners/{id}/approve  → Approve owner
//	PUT    /api/admin/owners/{id}/block    → Block owner
	
}
