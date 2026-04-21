package com.hotel_booking_system.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hotel_booking_system.entity.Users;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository{

	@PersistenceContext
	EntityManager enManager;
	
	@Autowired
	JdbcTemplate jsTemplate;
	
	@Override
	public Map<String, Object> getUserRoleByUserId(Users users) {
		
		Map<String, Object> newRoleMap=new HashMap<>();
		
		if(users!=null) {
			Map<String, Object> roles=jsTemplate.queryForMap("SELECT r.`role_name` from roles r JOIN user_roles us ON r.`id`=us.`role_id` where us.`user_id`=?",
					users.getId());
			newRoleMap=roles;
		}
		
		return newRoleMap;
	}

}
