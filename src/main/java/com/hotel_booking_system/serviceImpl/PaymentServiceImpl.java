package com.hotel_booking_system.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.hotel_booking_system.entity.Bookings;
import com.hotel_booking_system.entity.Payments;
import com.hotel_booking_system.entity.Refund;
import com.hotel_booking_system.entity.Users;
import com.hotel_booking_system.exception.BadRequestException;
import com.hotel_booking_system.exception.UnauthoriseException;
import com.hotel_booking_system.repository.BookingRepository;
import com.hotel_booking_system.repository.PaymentRepository;
import com.hotel_booking_system.repository.RefundRepository;
import com.hotel_booking_system.repository.UserRoleRepository;
import com.hotel_booking_system.repository.UsersRepository;
import com.hotel_booking_system.request.PaymentRequest;
import com.hotel_booking_system.request.RefundRequest;
import com.hotel_booking_system.response.PaymentResponse;
import com.hotel_booking_system.response.RefundResponse;
import com.hotel_booking_system.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository payRepository;
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private BookingRepository bookiRepository;
	
	@Autowired
	private RefundRepository refundRepository;
	
	@Autowired
	private KafkaTemplate<String, PaymentRequest> kafkaTemplate;


	@Override
	public void makePaymentByUser(Long bookingId,PaymentRequest request,String userName) {

			Users user = userRepository.findByUserName(userName)
			.orElseThrow(() -> new BadRequestException("User Not Found"));
			
			Bookings booking = bookiRepository.findById(bookingId)
			.orElseThrow(() -> new BadRequestException("Booking Not Found"));
			
			// Authorization
			if (!booking.getUser().getId().equals(user.getId())) {
			throw new UnauthoriseException("You are not allowed to pay for this booking");
			}
			
			request.setBookingId(bookingId);
			
			request.setUserName(userName);
			
			kafkaTemplate.send("booking-topic", request);
			
			}


	@Override
	public PaymentResponse getUserPaymentById(Long id, String userName) {
		
		Users user=userRepository.findByUserName(userName).orElseThrow(()->new BadRequestException("User Not Found"));
		
		Payments payments=payRepository.findById(id).orElseThrow(()->new BadRequestException("Payment Details Not Found"));
		
	    if (!payments.getUser().getId().equals(user.getId())) {
	        throw new UnauthoriseException("You are not allowed view Details");
	    }
		
		return new PaymentResponse(payments);
	}


	@Override
	public RefundResponse paymentRefundRequest(Long id,RefundRequest refundRequest, String userName) {
		
		Users user=userRepository.findByUserName(userName).orElseThrow(()->new BadRequestException("User Not Found"));
		
		Payments payments=payRepository.findById(id).orElseThrow(()->new BadRequestException("Payment Details Not Found"));
		
	    if (!payments.getUser().getId().equals(user.getId())) {
	        throw new UnauthoriseException("You are not allowed to pay for this booking");
	    }
	    
	    Refund refund=new Refund();
	    
	    refund.setAmount(refundRequest.getAmount());
	    
	    refund.setRefundDate(refundRequest.getRefundDate());
	    
	    refund.setStatus("PAID");
	    
	    refund.setPayments(payments);
	    
		return new RefundResponse(refund);
	}


	@Override
	public RefundResponse getRefundById(Long id,RefundRequest refundRequest, String userName) {
		
		Users user=userRepository.findByUserName(userName).orElseThrow(()->new BadRequestException("User Not Found"));
		
		Refund refund=refundRepository.findById(id).orElseThrow(()->new BadRequestException("User Not Found"));
		
		return new RefundResponse(refund);
	}


	@Override
	public List<PaymentResponse> getPaymentByOwnerId(Long ownerId, PaymentRequest request, String userName) {

	    Users user = userRepository.findByUserName(userName)
	            .orElseThrow(() -> new BadRequestException("User not found"));

	    if (!user.getId().equals(ownerId)) {
	        throw new UnauthoriseException("You are not authorized to access these payments");
	    }

	    List<Payments> payments = payRepository.getPaymentByUser_Id(ownerId);

	    return payments.stream()
	            .map(PaymentResponse::new)
	            .toList();
	}


	@Override
	public PaymentResponse getEarning(PaymentRequest bRequest, String userName) {
		// TODO Auto-generated method stub
		return null;
	}
		

}
