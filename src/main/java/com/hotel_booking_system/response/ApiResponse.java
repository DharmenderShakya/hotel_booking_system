package com.hotel_booking_system.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

	private boolean status;
    private String message;
    private T data;
    private int statusCode;
    private String timestamp;
    
	public ApiResponse(boolean success, String message, T data) {
		this.status = success;
		this.message = message;
		this.data = data;
	}
	
	 public static <T> ApiResponse<T> getResponse(boolean status, String message, T data) {
	        return new ApiResponse<>(
	                status,
	                message,
	                data,
	                200,
	                LocalDateTime.now().toString()
	        );
	    }

	    public static <T> ApiResponse<T> getResponse(boolean status, String message, T data, int statusCode) {
	        return new ApiResponse<>(
	                status,
	                message,
	                data,
	                statusCode,
	                LocalDateTime.now().toString()
	        );
	    }
    	
}
