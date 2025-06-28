package com.example.demo.repo;

import com.example.demo.entity.Room;
import com.example.demo.entity.PGProperty;
import com.example.demo.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
    // Find rooms by PG property
    List<Room> findByPgProperty(PGProperty pgProperty);
    
    // Find available rooms
    List<Room> findByIsAvailableTrue();
    
    // Find available rooms by PG property
    List<Room> findByPgPropertyAndIsAvailableTrue(PGProperty pgProperty);
    
    // Find rooms by room type
    List<Room> findByRoomType(RoomType roomType);
    
    // Find rooms by capacity
    List<Room> findByCapacity(Integer capacity);
    
    // Find rooms by room number and PG property
    Optional<Room> findByRoomNumberAndPgProperty(String roomNumber, PGProperty pgProperty);
    
    // Find rooms by monthly rent range
    List<Room> findByMonthlyRentBetween(java.math.BigDecimal minRent, java.math.BigDecimal maxRent);
} 