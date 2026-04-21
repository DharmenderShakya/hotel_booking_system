package com.hotel_booking_system.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hotel_booking_system.entity.Refund;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefundResponse {

    private Long id;

    private BigDecimal amount;

    private String status;

    private LocalDateTime refundDate;
    
    public RefundResponse(Refund refund) {

        this.id = refund.getId();
        this.amount = refund.getAmount();
        this.status = refund.getStatus();
        this.refundDate = refund.getRefundDate(); 
    }
}
