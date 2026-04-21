package com.hotel_booking_system.kafka;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.hotel_booking_system.entity.Bookings;
import com.hotel_booking_system.entity.Payments;
import com.hotel_booking_system.entity.Users;
import com.hotel_booking_system.exception.BadRequestException;
import com.hotel_booking_system.repository.BookingRepository;
import com.hotel_booking_system.repository.PaymentRepository;
import com.hotel_booking_system.repository.RefundRepository;
import com.hotel_booking_system.repository.UsersRepository;
import com.hotel_booking_system.request.PaymentRequest;
import com.hotel_booking_system.response.PaymentResponse;

import jakarta.transaction.Transactional;

@Service
public class PaymentConsumer {
	
	@Autowired
	private PaymentRepository payRepository;
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private BookingRepository bookiRepository;
	
	@Autowired
	private RefundRepository refundRepository;
	
	@Transactional
	public PaymentResponse processPayment(Long bookingId,
	                                      BigDecimal amount,
	                                      String paymentMethod,
	                                      Long userId) {

	    Bookings booking = bookiRepository.findById(bookingId)
	            .orElseThrow(() -> new BadRequestException("Booking Not Found"));


	    Users user = userRepository.findById(userId)
	            .orElseThrow(() -> new BadRequestException("User Not Found"));

	        Payments payment = new Payments();
	        payment.setAmount(amount);
	        payment.setPaymentMethod(paymentMethod);
	        payment.setStatus("SUCCESS");
	        payment.setTransactionId("TXN-" + System.currentTimeMillis());
	        payment.setPaymentDate(LocalDateTime.now());
	        payment.setBookings(booking);
	        payment.setUser(user);

	        Payments saved = payRepository.save(payment);

	        // Update booking
	        
	        return new PaymentResponse(saved);

	}
	
	
	@KafkaListener(topics = "booking-topic", groupId = "booking-group")
	public void consume(PaymentRequest event) {

	  processPayment(
	            event.getBookingId(),
	            event.getAmount(),
	            event.getPaymentMethod(),
	            event.getUserId()
	    );
	}
}
