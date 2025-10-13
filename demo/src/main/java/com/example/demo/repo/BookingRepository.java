package com.example.demo.repo;

import com.example.demo.entity.Booking;
import com.example.demo.entity.BookingStatus;
import com.example.demo.entity.User;
import com.example.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    // Find bookings by user
    List<Booking> findByUser(User user);
    
    // Find bookings by room
    List<Booking> findByRoom(Room room);
    
    // Find bookings by status
    List<Booking> findByStatus(BookingStatus status);
    
    // Find bookings by user and status
    List<Booking> findByUserAndStatus(User user, BookingStatus status);
    
    // Find bookings by room and status
    List<Booking> findByRoomAndStatus(Room room, BookingStatus status);
    
    // Find bookings by date range
    List<Booking> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Find bookings by end date
    List<Booking> findByEndDate(LocalDate endDate);
    
    // Find bookings by start date
    List<Booking> findByStartDate(LocalDate startDate);
    
    // Find bookings by user and date range
    List<Booking> findByUserAndStartDateBetween(User user, LocalDate startDate, LocalDate endDate);
    
    // Find bookings by room and date range
    List<Booking> findByRoomAndStartDateBetween(Room room, LocalDate startDate, LocalDate endDate);
    
    // Find active bookings (not cancelled or completed)
    List<Booking> findByStatusNotIn(List<BookingStatus> statuses);
} 