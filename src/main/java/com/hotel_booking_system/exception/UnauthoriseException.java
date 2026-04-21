package com.hotel_booking_system.exception;

public class UnauthoriseException extends RuntimeException {

	private static final long serialVersionUID = 2855828188061570081L;

	public UnauthoriseException() {
		super();
	}

	public UnauthoriseException(String message) {
		super(message);
	}

}
