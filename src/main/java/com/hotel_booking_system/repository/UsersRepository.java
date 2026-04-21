package com.hotel_booking_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel_booking_system.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{
	
	Optional<Users> findByUserName(String userName);
	
}
