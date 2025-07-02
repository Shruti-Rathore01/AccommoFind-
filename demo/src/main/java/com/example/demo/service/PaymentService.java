package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Payment;
import com.example.demo.entity.PaymentStatus;
import com.example.demo.repo.BookingRepository;
import com.example.demo.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;


    public List<Payment> getAllPayments() {
        return  paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Payment not found with id:"+id)
        );
    }

    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }
    @Autowired
    private BookingRepository bookingRepository;

    public Payment getPaymentByBooking(Long bookingId) {
        Booking booking=bookingRepository.findById(bookingId)
                .orElseThrow(
                        ()->new RuntimeException("Booking not found with id: "+bookingId));
        return paymentRepository
                .findByBooking(booking)
                .orElseThrow(()->new RuntimeException(
                        "Payment not found with booking id:"+bookingId
                ));

    }


    public Payment createPayment(Payment payment) {
        if(payment.getBooking()==null || payment.getBooking().getId()==null)
        {
            throw new RuntimeException("Booking is required ");

        }
        Booking booking=bookingRepository.findById(payment.getBooking().getId())
                .orElseThrow(()->new RuntimeException("Booking not found "));

        payment.setBooking(booking);
        
        if(payment.getStatus()==null)
        {
            payment.setStatus(PaymentStatus.PENDING);
        }
        
        if(payment.getPaidAt()==null && payment.getStatus()==PaymentStatus.SUCCESS)
        {
            payment.setPaidAt(LocalDateTime.now());
        }
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment=getPaymentById(id);
        payment.setStatus(status);
        if(status==PaymentStatus.SUCCESS && payment.getPaidAt()==null)
        {
            payment.setPaidAt(LocalDateTime.now());
        }
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        Payment payment=getPaymentById(id);
        paymentRepository.delete(payment);
    }
}
