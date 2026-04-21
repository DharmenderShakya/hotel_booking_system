package com.hotel_booking_system.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.hotel_booking_system.configuration.JwtService;
import com.hotel_booking_system.entity.Roles;
import com.hotel_booking_system.entity.UserProfile;
import com.hotel_booking_system.entity.UserRoles;
import com.hotel_booking_system.entity.Users;
import com.hotel_booking_system.exception.BadRequestException;
import com.hotel_booking_system.exception.ResourceNotFoundException;
import com.hotel_booking_system.exception.UnauthoriseException;
import com.hotel_booking_system.repository.RoleRepository;
import com.hotel_booking_system.repository.UserCustomRepository;
import com.hotel_booking_system.repository.UserProfileRepository;
import com.hotel_booking_system.repository.UserRoleRepository;
import com.hotel_booking_system.repository.UsersRepository;
import com.hotel_booking_system.request.RegistrationRequest;
import com.hotel_booking_system.response.ApiResponse;
import com.hotel_booking_system.response.LoginReponse;
import com.hotel_booking_system.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserCustomRepository userRepository2;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	String timestamp = java.time.OffsetDateTime.now().toString();
	
	@Override
	public void addUser(RegistrationRequest request) {

	    if (request.getUserName() == null || request.getUserName().isEmpty()) {
	        throw new BadRequestException("Username is required");
	    }

	    if (request.getPassword() == null || request.getPassword().isEmpty()) {
	        throw new BadRequestException("Password is required");
	    }

	    if (request.getRoleId() == null) {
	        throw new BadRequestException("Role is required");
	    }

	    // 2. Check Duplicate User
	    if (userRepository.findByUserName(request.getUserName()).isPresent()) {
	        throw new BadRequestException("Username already exists");
	    }

	    // 3. Create User
	    Users user = new Users();
	    user.setUserName(request.getUserName());
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    user.setStatus("0");
	    user.setEmail(request.getEmail());
	    
	    user.setCreatedDate(LocalDateTime.now());

	    Users savedUser = userRepository.save(user);

	    // 4. Fetch Role Safely
	    Roles role = roleRepository.findById(request.getRoleId())
	            .orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));

	    // 5. Map User & Role
	    UserRoles userRoles = new UserRoles();
	    userRoles.setUser(savedUser);
	    userRoles.setRole(role);
	    
	    	userRoleRepository.save(userRoles);

	    // 6. Call Lab Service (ONLY if needed)
	        UserProfile userProfile = new UserProfile();
	        
	        userProfile.setEmail(request.getEmail());
	        userProfile.setFirstName(request.getUserName());
	        userProfile.setUsers(savedUser);
	        userProfile.setPhone(request.getMobile());

	        try {
	        	userProfileRepository.save(userProfile);
	        } catch (Exception e) {
	            throw new BadRequestException(e.getMessage());
	        }
	    
	}


	@Override
	public ResponseEntity<ApiResponse<LoginReponse>> doLogin(RegistrationRequest request){

	    //  1. Input Validation
	    if (request.getUserName() == null || request.getUserName().isEmpty()) {
	        throw new BadRequestException("Username is required");
	    }

	    if (request.getPassword() == null || request.getPassword().isEmpty()) {
	        throw new BadRequestException("Password is required");
	    }

	    // 2. Authenticate User
	    try {
	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        request.getUserName(),
	                        request.getPassword()
	                )
	        );
	    } catch (Exception e) {
	        throw new UnauthoriseException("Invalid username or password");
	    }

	    // 3. Fetch User from DB
	    Optional<Users> user=Optional.ofNullable(userRepository.findByUserName(request.getUserName())
	    		.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"))); 
	    
	    Users user1=user.get();

	    // 4. Check User Status
	    if (!"ACTIVE".equalsIgnoreCase(user1.getStatus())) {
	        throw new UnauthoriseException("User is inactive");
	    }

	    // 5. Generate Token
	    String token = jwtService.generateToken(user1.getUserName());

	    // 6. Prepare Response
	    LoginReponse response = new LoginReponse();

	    response.setId(user1.getId());
	    response.setUserName(user1.getUserName()); // FIXED
	    response.setUserToken(token);
	    response.setUserRole(userRepository2.getUserRoleByUserId(user1));
	    response.setCreatedDate(user1.getCreatedDate());
	    response.setDeviceId(user1.getDeviceId());
	    response.setStatus(user1.getStatus());

	    return ResponseEntity.status(HttpStatus.CREATED)
		        .body(new ApiResponse<>(
		                true,
		                "Lab created successfully",
		                response,
		                201,
		                timestamp
		        ));
	    
	}


	@Override
	public ResponseEntity<ApiResponse<Object>> addOwner(RegistrationRequest raRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
