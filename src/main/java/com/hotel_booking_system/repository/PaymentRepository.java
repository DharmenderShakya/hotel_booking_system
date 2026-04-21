package com.hotel_booking_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel_booking_system.entity.Payments;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, Long> {
	
	 List<Payments> getPaymentByUser_Id(Long id);

}
