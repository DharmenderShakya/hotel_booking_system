package com.hotel_booking_system.exception;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -3873300355572265042L;
	
	String message;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
		this.message=message;
	}

	public BadRequestException(Exception ex) {
		super(ex);
		this.message=ex.getMessage();
	}

	@Override
	public String toString() {
		return this.message;
	}
	
	
	
}
