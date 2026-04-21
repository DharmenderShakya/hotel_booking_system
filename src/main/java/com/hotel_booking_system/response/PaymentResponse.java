package com.hotel_booking_system.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hotel_booking_system.entity.Payments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponse {

    private Long paymentId;

    private BigDecimal amount;
    
    private String paymentMethod;

    private String status;

    private String transactionId;

    private LocalDateTime paymentDate;
    
    public PaymentResponse(Payments payments) {

        this.paymentId = payments.getId();
        
        this.amount = payments.getAmount();

        this.paymentMethod = payments.getPaymentMethod();

        this.status = payments.getStatus() ;

        this.transactionId = payments.getTransactionId();
        
        this.paymentDate = payments.getPaymentDate();
    }
}
