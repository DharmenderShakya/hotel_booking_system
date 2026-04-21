package com.hotel_booking_system.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hotel_booking_system.request.PaymentRequest;
import com.hotel_booking_system.request.RefundRequest;
import com.hotel_booking_system.response.ApiResponse;
import com.hotel_booking_system.response.PaymentResponse;
import com.hotel_booking_system.response.RefundResponse;

public interface PaymentService {

	public void makePaymentByUser(Long id,PaymentRequest bRequest,String userName);
	
	public PaymentResponse getUserPaymentById(Long id,String userName);
	
	public RefundResponse paymentRefundRequest(Long id,RefundRequest refundRequest,String userName);
	
	public RefundResponse getRefundById(Long id,RefundRequest refundRequest,String userName);
	
	public List<PaymentResponse> getPaymentByOwnerId(Long id,PaymentRequest bRequest,String userName);
	
	public PaymentResponse getEarning(PaymentRequest bRequest,String userName);
	
	
//	GET    /api/admin/payments             → All payments
//	GET    /api/admin/refunds              → All refunds
//	PUT    /api/admin/refunds/{id}/approve → Approve refund
//	PUT    /api/admin/refunds/{id}/reject  → Reject refund
	
}
