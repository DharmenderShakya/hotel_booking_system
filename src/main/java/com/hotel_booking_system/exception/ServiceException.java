package com.hotel_booking_system.exception;

public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ServiceException(String message) {
        super(message);
    }

	public ServiceException() {
		super();
	}
	
	
}
