package com.example.demo.repo;

import com.example.demo.entity.Payment;
import com.example.demo.entity.PaymentStatus;
import com.example.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    // Find payment by booking
    Optional<Payment> findByBooking(Booking booking);
    
    // Find payments by status
    List<Payment> findByStatus(PaymentStatus status);
    
    // Find payments by transaction ID
    Optional<Payment> findByTransactionId(String transactionId);
    
    // Find payments by payment method
    List<Payment> findByPaymentMethod(String paymentMethod);
    
    // Find payments by amount range
    List<Payment> findByAmountBetween(java.math.BigDecimal minAmount, java.math.BigDecimal maxAmount);
    
    // Find payments by date range
    List<Payment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find payments by paid date
    List<Payment> findByPaidAt(LocalDateTime paidAt);
    
    // Find payments by paid date range
    List<Payment> findByPaidAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find pending payments
    List<Payment> findByStatusAndPaidAtIsNull(PaymentStatus status);
    
    // Find successful payments
    List<Payment> findByStatusAndPaidAtIsNotNull(PaymentStatus status);
} 