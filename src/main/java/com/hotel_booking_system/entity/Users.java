package com.hotel_booking_system.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Users {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status")
    private String status;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "user_token")
    private String userToken;
    
    private String deviceId;
 
    @Column(name = "created_date")
    private LocalDateTime createdDate;
	
    @OneToMany(mappedBy = "user")
    private List<UserRoles> userRoles;
    
    @Transient
    Map<String,Object> userRole;

}
