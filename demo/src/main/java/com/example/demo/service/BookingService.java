package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.BookingStatus;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.repo.BookingRepository;
import com.example.demo.repo.RoomRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    public List<Booking> getAllBookings() {
        return  bookingRepository.findAll();

    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id:"+id));

    }
@Autowired
private UserRepository userRepository;
    public List<Booking> getBookingsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return bookingRepository.findByUser(user);
    }
    @Autowired
    private RoomRepository roomRepository;

    // Get bookings by room
    public List<Booking> getBookingsByRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
        return bookingRepository.findByRoom(room);
    }

    // Create new booking
    public Booking createBooking(Booking booking) {
        // Validate user and room exist
        if (booking.getUser() == null || booking.getUser().getId() == null) {
            throw new RuntimeException("User is required");
        }
        if (booking.getRoom() == null || booking.getRoom().getId() == null) {
            throw new RuntimeException("Room is required");
        }

        User user = userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Room room = roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Check room availability
        if (!room.getIsAvailable()) {
            throw new RuntimeException("Room is not available");
        }

        // Set user and room
        booking.setUser(user);
        booking.setRoom(room);

        // Set status and total amount if needed
        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.PENDING);
        }
        if (booking.getTotalAmount() == null) {
            booking.setTotalAmount(room.getMonthlyRent());
        }

        // Save booking
        Booking savedBooking = bookingRepository.save(booking);
        // Optionally, mark room as unavailable
        room.setIsAvailable(false);
        roomRepository.save(room);

        return savedBooking;
    }
    // Update booking status
    public Booking updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = getBookingById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    // Delete booking
    public void deleteBooking(Long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);

        // Optionally, mark room as available again
        Room room = booking.getRoom();
        room.setIsAvailable(true);
        roomRepository.save(room);
    }


    }
