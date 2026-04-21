package com.hotel_booking_system.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.hotel_booking_system.entity.UserRoles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReponse {
	
	  	private Long id;

	    private String userName;

	    private Long employeeId;

	    private Long labId;

	    private Long centerId;

	    private String status;
	    
	    private String userToken;
	    
	    private String deviceId;
	 
	    private LocalDateTime createdDate;
		
	    private List<UserRoles> userRoles;
	    
	    Map<String,Object> userRole;
}
