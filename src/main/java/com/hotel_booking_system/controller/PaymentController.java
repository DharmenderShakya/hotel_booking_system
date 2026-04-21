package com.hotel_booking_system.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hotel_booking_system.request.PaymentRequest;
import com.hotel_booking_system.request.RefundRequest;
import com.hotel_booking_system.response.PaymentResponse;
import com.hotel_booking_system.response.RefundResponse;
import com.hotel_booking_system.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Make payment for a booking
    @PostMapping("/{bookingId}")
    public String makePayment(@PathVariable Long bookingId,
                                       @RequestBody PaymentRequest request,
                                       Principal principal) {
        String userName = principal.getName();
        
         paymentService.makePaymentByUser(bookingId, request, userName);
         
         return "Payment Done Successfully";
        		 
    }

    // Get payment by ID
    @GetMapping("/{paymentId}")
    public PaymentResponse getPaymentById(@PathVariable Long paymentId,
                                          Principal principal) {
        String userName = principal.getName();
        return paymentService.getUserPaymentById(paymentId, userName);
    }

//     Request refund
    @PostMapping("/refund/{paymentId}")
    public RefundResponse refundPayment(@PathVariable Long paymentId,
                                        @RequestBody RefundRequest request,
                                        Principal principal) {
        String userName = principal.getName();
        return paymentService.paymentRefundRequest(paymentId, request, userName);
    }

    //  Get refund by ID
    @GetMapping("/refund/{refundId}")
    public RefundResponse getRefundById(@PathVariable Long refundId,
                                        Principal principal) {
        String userName = principal.getName();
        return paymentService.getRefundById(refundId, null, userName);
    }

    // Get all payments for owner
    @GetMapping("/owner/{ownerId}")
    public List<PaymentResponse> getPaymentsByOwner(@PathVariable Long ownerId,
                                                    Principal principal) {
        String userName = principal.getName();
        return paymentService.getPaymentByOwnerId(ownerId, null, userName);
    }
}