package com.hotel_booking_system.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
        super(message);
    }

	public ResourceNotFoundException() {
		super();
	}
		
}
