package com.hotel_booking_system.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel_booking_system.entity.Users;
import com.hotel_booking_system.repository.UsersRepository;

@Service
public class UserInfoService implements UserDetailsService{

	private final UsersRepository repository;
	
    private final PasswordEncoder encoder;

    public UserInfoService(UsersRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Users user = repository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserInfoDetails(user);
    }

    // Add any additional methods for registering or managing users
    public String addUser(Users userInfo) {
        // Encrypt password before saving
        userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
        repository.save(userInfo);
        return "User added successfully!";
    }

}
