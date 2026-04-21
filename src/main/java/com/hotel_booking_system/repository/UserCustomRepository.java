package com.hotel_booking_system.repository;

import java.util.List;
import java.util.Map;

import com.hotel_booking_system.entity.Users;


public interface UserCustomRepository{

	Map<String, Object> getUserRoleByUserId(Users users);
	
}
